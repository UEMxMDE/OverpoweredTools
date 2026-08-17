package pc101.overpoweredtools.util.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.init.ItemInit;
import pc101.overpoweredtools.objects.items.OverpoweredElytra;
import pc101.overpoweredtools.util.Reference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class OPElytraHandlerServer
{
    //public static boolean shouldFly = false;
    //public int ticksOPElytraFlying;

    // Both variables below tracks every player on a server. They are responsible for making sure that multiple players can fly and lose durability independently of each other.
    // If this was a boolean instead of a Map, that boolean would be shared across all players. This means that the elytra would activate for all players at once if they are all falling, which is not supposed to happen.
    public Map<UUID, Boolean> shouldFly = new HashMap<>();
    // If this was an int instead of a Map, that int would be shared across all players. When I tested that, it meant that only one player at a time would have their overpowered elytra durability go down if multiple people are flying with it at the same time, which is not supposed to happen.
    public Map<UUID, Integer> ticksOPElytraFlying = new HashMap<>();

    @SubscribeEvent
    public void flightServer(TickEvent.PlayerTickEvent event)
    {
        // Check if this if statement is necessary.
        /*
        if (event.side != Side.SERVER)
        {
            return;
        }
        */

        if(event.player instanceof EntityPlayerMP)  // Prevents a crash caused by trying to cast EntityPlayerSP to EntityPlayerMP on the line of code below.
        {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            //player.sendMessage(new TextComponentString("shouldFly = " + shouldFly));
            //if(player.fallDistance != 0.0F) player.sendMessage(new TextComponentString("fallDistance = " + player.fallDistance));
            //if(player.motionY != 0.0D) player.sendMessage(new TextComponentString(event.phase + " motionY = " + player.motionY));
            //if(player.fallDistance != 0.0F) player.sendMessage(new TextComponentString(event.phase + " fallDistance = " + player.fallDistance));
            //if(player.motionX != 0.0D || player.motionY != 0.0D || player.motionZ != 0.0D) player.sendMessage(new TextComponentString(/*event.phase +*/ "\nmotionX = " + player.motionX + "\nmotionY = " + player.motionY + "\nmotionZ = " + player.motionZ));

            ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            if (chest.getItem() instanceof OverpoweredElytra && OverpoweredElytra.isUsable(chest))
            {
                if(/*shouldFly*/ shouldFly.getOrDefault(player.getUniqueID(), false))
                {
                    player.setElytraFlying();

                    // If I did not include the if statement below, ticksOPElytraFlying would be called twice by being called once per tick phase. As a result the overpowered elytra would lose its durability twice as fast as the vanilla elytra. This is why in the previous commit the math for decrementing durability from the overpowered elytra had to be % 40 to match the vanilla elytra even though the vanilla elytra uses % 20 instead.
                    if(event.phase == TickEvent.Phase.START) // TickEvent.Phase.END would work too.
                    {
                        // The below if statements and else statement are for making the overpowered elytra take durability damage in the same way the vanilla elytra takes durability damage.
                        // The below if statements and else statement are copied and modified from EntityLivingBase#onUpdate and EntityLivingBase#updateElytra.
                        if (player.isElytraFlying())
                        {
                            //++ticksOPElytraFlying;
                            ticksOPElytraFlying.put(player.getUniqueID(), 1 + ticksOPElytraFlying.getOrDefault(player.getUniqueID(), 0));
                        }
                        else
                        {
                            //ticksOPElytraFlying = 0;
                            ticksOPElytraFlying.put(player.getUniqueID(), 0);
                        }
                        if ((ticksOPElytraFlying.getOrDefault(player.getUniqueID(), 0) + 1) % 20 == 0)
                        {
                            chest.damageItem(1, player);
                        }
                    }

                    // This if statement determines when the overpowered elytra is supposed to stop flying.
                    if ((player.onGround || player.capabilities.isFlying || player.isRiding()) && event.phase == TickEvent.Phase.START) // If event.phase == TickEvent.Phase.END is used instead then the OP elytra will cancel its flight too early (for example, the flight will cancel when touching tall grass as opposed to the vanilla elytra which (without rockets) does not get stopped by tall grass.)
                    {
                        //shouldFly = false;
                        shouldFly.put(player.getUniqueID(), false);
                        player.clearElytraFlying();
                        //ticksOPElytraFlying = 0;    // The vanilla elytra resets its ticksElytraFlying to 0 when the elytra stops flying so the same will be done here as well.
                        ticksOPElytraFlying.put(player.getUniqueID(), 0);   // The vanilla elytra resets its ticksElytraFlying to 0 when the elytra stops flying so the same will be done here as well.
                    }
                }
            }

            // Attempt 2 (did not work)
            /*
            ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            if (chest.getItem() instanceof OverpoweredElytra && OverpoweredElytra.isUsable(chest))
            {
                if (shouldFly && !player.onGround && player.motionY < 0.0D && !player.isElytraFlying() && !player.isInWater() && !player.capabilities.isFlying && !player.isRiding())
                {
                    player.sendMessage(new TextComponentString("setElytraFly()"));
                    player.setElytraFlying();
                }
                else
                {
                    //shouldFly = false;
                    player.sendMessage(new TextComponentString("clearElytraFly()"));
                    player.clearElytraFlying();
                }
            }

             */
        }

        // Attempt 1 (did not work)
        /*
        if (event.phase == TickEvent.Phase.END) {
            if (event.player instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) event.player;

                // This logic was copied from lines 1033-1045 of NetHandlerPlayServer.java
                if (shouldFly && !player.onGround && player.motionY < 0.0D && !player.isElytraFlying() && !player.isInWater()) {
                    ItemStack itemstack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

                    if (itemstack.getItem() == ItemInit.OVERPOWERED_ELYTRA && OverpoweredElytra.isUsable(itemstack)) {
                        player.sendMessage(new TextComponentString("setElytraFlying()"));
                        player.setElytraFlying();
                    }
                } else {
                    player.sendMessage(new TextComponentString("clearElytraFlying()"));

                    ItemStack itemstack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);   // If I don't include this line and the below if statement then the vanilla elytra will not work at all.
                    if (itemstack.getItem() == ItemInit.OVERPOWERED_ELYTRA && OverpoweredElytra.isUsable(itemstack)) {
                        player.clearElytraFlying();
                        shouldFly = false;
                    }
                }

                player.sendMessage(new TextComponentString("What is shouldFly? " + shouldFly));
            }
        }

         */
    }
}
