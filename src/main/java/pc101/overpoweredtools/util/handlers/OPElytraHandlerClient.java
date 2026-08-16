package pc101.overpoweredtools.util.handlers;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import pc101.overpoweredtools.network.NetworkHandler;
import pc101.overpoweredtools.network.PacketOPElytraFlying;
import pc101.overpoweredtools.objects.items.OverpoweredElytra;
import pc101.overpoweredtools.util.Reference;

//@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class OPElytraHandlerClient
{
    public boolean lastJump;

    @SubscribeEvent
    public void flightClient(TickEvent.PlayerTickEvent event)
    {
        // Check if this if statement is necessary.
        /*
        if (event.side != Side.CLIENT)
        {
            return;
        }
        */

        boolean isJumpKeyPressed;

        if(event.player instanceof EntityPlayerSP)  // Prevents a crash caused by trying to cast EntityPlayerMP to EntityPlayerSP on the line of code below.
        {
            EntityPlayerSP player = (EntityPlayerSP) event.player;

            isJumpKeyPressed = player.movementInput.jump;

            if (isJumpKeyPressed && !lastJump && !player.onGround && player.motionY < 0.0D && !player.isElytraFlying() && !player.isInWater() && !player.capabilities.isFlying && !player.isRiding())
            {
                ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                if (chest.getItem() instanceof OverpoweredElytra && OverpoweredElytra.isUsable(chest))
                {
                    //player.sendMessage(new TextComponentString("Sent PacketOPElytraFlying()"));
                    //player.connection.sendPacket(new CPacketEntityAction(player, CPacketEntityAction.Action.START_FALL_FLYING));
                    //OPElytraHandlerServer.flightServer(event);
                    NetworkHandler.INSTANCE.sendToServer(new PacketOPElytraFlying());
                }
            }

            lastJump = isJumpKeyPressed;
        }
    }
}
