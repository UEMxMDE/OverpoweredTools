package pc101.overpoweredtools.util.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pc101.overpoweredtools.init.ItemInit;
import pc101.overpoweredtools.objects.items.OverpoweredElytra;

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

    // If this was an ItemStack instead of a Map, that ItemStack would be shared across all players.
    public Map<UUID, ItemStack> temporaryOPElytraInstanceStorage = new HashMap<>();
    //public boolean isCurrentlyVanillaElytra;    // Without this variable being used in the way it is below, the overpowered elytra would duplicate every time the player attempts to take it off.
    public Map<UUID, Boolean> isCurrentlyVanillaElytra = new HashMap<>();
    // Remembers if the player wore/took off a vanilla elytra or if they wore/took off an overpowered elytra (this is necessary because every tick the overpowered elytra is being replaced with the vanilla elytra and then the vanilla elytra is replaced with the overpowered elytra again).
    public Map<UUID, Boolean> wasWearingOPElytra = new HashMap<>();

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
            if(player.fallDistance != 0.0F) player.sendMessage(new TextComponentString("fallDistance = " + player.fallDistance));
            //if(player.motionY != 0.0D) player.sendMessage(new TextComponentString(event.phase + " motionY = " + player.motionY));
            //if(player.fallDistance != 0.0F) player.sendMessage(new TextComponentString(event.phase + " fallDistance = " + player.fallDistance));
            //if(player.motionX != 0.0D || player.motionY != 0.0D || player.motionZ != 0.0D) player.sendMessage(new TextComponentString(/*event.phase +*/ "\nmotionX = " + player.motionX + "\nmotionY = " + player.motionY + "\nmotionZ = " + player.motionZ));
            //if(player.fallDistance != 0.0F && event.phase == TickEvent.Phase.START) player.sendMessage(new TextComponentString("fallDistance = " + player.fallDistance));

            ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

            // This whole if block fixes multiple issues with the overpowered elytra and vanilla elytra displaying each other instead of themselves.
            if (event.phase == TickEvent.Phase.START)
            {
                if (chest.getItem() instanceof OverpoweredElytra && OverpoweredElytra.isUsable(chest))  // instanceof may cause issues with elytras that extend OverpoweredElytra, but those elytras should be using their own handlers instead of this one, so I am keeping instanceof here for now.
                {
                    wasWearingOPElytra.put(player.getUniqueID(), true);
                }
                else if (wasWearingOPElytra.getOrDefault(player.getUniqueID(), false))
                {
                    // Fixes a bug where the player sees themselves wearing the overpowered elytra (correct behavior), but when they see other players wearing the overpowered elytra it displays the vanilla elytra instead.
                    ((WorldServer) player.world).getEntityTracker().sendToTracking(player, new SPacketEntityEquipment(player.getEntityId(), EntityEquipmentSlot.CHEST, chest));

                    wasWearingOPElytra.put(player.getUniqueID(), false);
                }
            }

            if (chest.getItem() instanceof OverpoweredElytra && OverpoweredElytra.isUsable(chest))  // instanceof may cause issues with elytras that extend OverpoweredElytra, but those elytras should be using their own handlers instead of this one, so I am keeping instanceof here for now.
            {
                // The below code swaps the overpowered elytra with the vanilla elytra temporarily to trick vanilla Minecraft into doing correct flight calculations.
                wasWearingOPElytra.put(player.getUniqueID(), true);
                temporaryOPElytraInstanceStorage.put(player.getUniqueID(), chest);

                //player.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.ELYTRA));    // This is the original way the elytra was swapped. I have switched to the method below because this method caused some issues during earlier testing, but later testing has shown that using this line or the line below makes no difference. I am keeping the line below for now.
                player.inventory.armorInventory.set(EntityEquipmentSlot.CHEST.getIndex(), new ItemStack(Items.ELYTRA));

                if (player.inventoryContainer instanceof ContainerPlayer)   // This if statement fixes a bug where the overpowered elytra would show up in the middle right slot of a crafting table if the player is wearing the overpowered elytra. The overpowered elytra was not able to be removed from the crafting table until the player took off their overpowered elytra.
                {
                    // Fixes a bug where if the player is wearing the overpowered elytra and then picks up an item or experience orb, the overpowered elytra briefly flickers as the vanilla elytra.
                    player.inventoryContainer.inventoryItemStacks.set(6, new ItemStack(Items.ELYTRA));
                }

                isCurrentlyVanillaElytra.put(player.getUniqueID(), true);

                if(/*shouldFly*/ shouldFly.getOrDefault(player.getUniqueID(), false))
                {
                    player.setElytraFlying();

                    // Doesn't do much of anything as a "fix" unless it is allowed to be called by itself (i.e. being called without being restricted to a tick phase)
                    //player.travel(player.moveStrafing, player.moveVertical, player.moveForward);

                    // For some reason this fixed the glitch caused by player.travel(. . .) where the player stops flying 1 tick to early, but it also messes with fallDistance a lot, so I am leaving this commented out.
                    //player.setInWeb();

                    // If I did not include the if statement below, ticksOPElytraFlying would be called twice by being called once per tick phase. As a result the overpowered elytra would lose its durability twice as fast as the vanilla elytra. This is why in the previous commit the math for decrementing durability from the overpowered elytra had to be % 40 to match the vanilla elytra even though the vanilla elytra uses % 20 instead.
                    if(event.phase == TickEvent.Phase.END && false /* I did "&& false" above to disable this durability system because it only works with the durability system in the previous version of this handler. */) // TickEvent.Phase.START would work too.
                    {
                        // The below if statements and else statement are for making the overpowered elytra take durability damage in the same way the vanilla elytra takes durability damage.
                        // The below if statements and else statement are copied and modified from EntityLivingBase#onUpdate and EntityLivingBase#updateElytra.
                        if (player.isElytraFlying())
                        {
                            //++ticksOPElytraFlying;
                            ticksOPElytraFlying.put(player.getUniqueID(), 1 + ticksOPElytraFlying.getOrDefault(player.getUniqueID(), 0));
                        }
                        /*
                        else    // After some testing, I have found that this else statement never gets executed.
                        {
                            //ticksOPElytraFlying = 0;
                            ticksOPElytraFlying.put(player.getUniqueID(), 0);
                        }
                         */
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
            else    // This "else" block of code fixes a bug where if the player is elytra flying with the overpowered elytra equipped, and then they take it off, and then they put it back on, the player automatically begins flying again (unlike the vanilla elytra which needs its flight to be manually reactivated).
            {
                if(event.phase == TickEvent.Phase.END && player.isElytraFlying())    // Decrement overpowered elytra durability if using the elytra swap system.
                {
                    ticksOPElytraFlying.put(player.getUniqueID(), 1 + ticksOPElytraFlying.getOrDefault(player.getUniqueID(), 0));

                    if ((ticksOPElytraFlying.getOrDefault(player.getUniqueID(), 0) + 1) % 20 == 0)
                    {
                        temporaryOPElytraInstanceStorage.getOrDefault(player.getUniqueID(), ItemStack.EMPTY).damageItem(1, player);
                    }
                }

                shouldFly.put(player.getUniqueID(), false);
            }

            // Swap the temporary vanilla elytra with the previously saved overpowered elytra instance.
            // TickEvent.Phase.START does not work properly here, If used, it turns any overpowered elytra worn in the chest slot into 2 vanilla elytras. It also turns any other item that is subsequently equipped in the chest slot to turn into 2 vanilla elytras. The overpowered elytra is still in the slot despite the slot seeming empty, but you can only see it again if the code is changed back to TickEvent.Phase.END via a hotswap via debug mode.
            // If an event.phase is not specified, the fallDistance values become incorrect.
            if(event.phase == TickEvent.Phase.END && isCurrentlyVanillaElytra.getOrDefault(player.getUniqueID(), false))
            {
                //player.setItemStackToSlot(EntityEquipmentSlot.CHEST, temporaryOPElytraInstanceStorage.getOrDefault(player.getUniqueID(), ItemStack.EMPTY));   // This is the original way the elytra was swapped. I have switched to the method below because this method caused some issues during earlier testing, but later testing has shown that using this line or the line below makes no difference. I am keeping the line below for now.
                player.inventory.armorInventory.set(EntityEquipmentSlot.CHEST.getIndex(), temporaryOPElytraInstanceStorage.getOrDefault(player.getUniqueID(), ItemStack.EMPTY));

                if (player.inventoryContainer instanceof ContainerPlayer)   // This if statement fixes a bug where the overpowered elytra would show up in the middle right slot of a crafting table if the player is wearing the overpowered elytra. The overpowered elytra was not able to be removed from the crafting table until the player took off their overpowered elytra.
                {
                    // Fixes a bug where if the player is wearing the overpowered elytra and then picks up an item or experience orb, the overpowered elytra briefly flickers as the vanilla elytra.
                    player.inventoryContainer.inventoryItemStacks.set(6, temporaryOPElytraInstanceStorage.getOrDefault(player.getUniqueID(), ItemStack.EMPTY));
                }

                // Fixes a bug where the overpowered elytra does not update its durability bar unless rockets are fired during flight, the player dies and respawns, or the player disconnects and then reconnects later.
                player.connection.sendPacket(new SPacketSetSlot(player.inventoryContainer.windowId, 6, temporaryOPElytraInstanceStorage.getOrDefault(player.getUniqueID(), ItemStack.EMPTY)));

                // Fixes a bug where the player sees themselves wearing the overpowered elytra (correct behavior), but when they see other players wearing the overpowered elytra it displays the vanilla elytra instead.
                ((WorldServer) player.world).getEntityTracker().sendToTracking(player, new SPacketEntityEquipment(player.getEntityId(), EntityEquipmentSlot.CHEST, temporaryOPElytraInstanceStorage.getOrDefault(player.getUniqueID(), ItemStack.EMPTY)));

                isCurrentlyVanillaElytra.put(player.getUniqueID(), false);
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
