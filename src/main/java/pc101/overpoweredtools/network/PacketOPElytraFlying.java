package pc101.overpoweredtools.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pc101.overpoweredtools.init.ItemInit;
import pc101.overpoweredtools.objects.items.OverpoweredElytra;
import pc101.overpoweredtools.util.handlers.OPElytraHandlerServer;
import pc101.overpoweredtools.OverpoweredTools;

public class PacketOPElytraFlying implements IMessage
{
    public PacketOPElytraFlying()
    {
        // "A default constructor is always required"
        // https://docs.minecraftforge.net/en/1.12.x/networking/simpleimpl/
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }

    // "It is recommended (but not required) that for organization’s sake, this class is an inner class to your MyMessage class. If this is done, note that the class must also be declared static."
    // https://docs.minecraftforge.net/en/1.12.x/networking/simpleimpl/
    public static class PacketOPElytraFlyingHandler implements IMessageHandler<PacketOPElytraFlying, IMessage>
    {
        @Override
        public IMessage onMessage(PacketOPElytraFlying message, MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().player;

            // This gets the same instance of the OPElytraHandlerServer in the main mod class (OverpoweredTools.java) instead of making a new instance.
            // If I didn't get the same instance in the main mod class and instead made a new instance, then the new instance would not be registered in the Forge Event Bus.
            // And if this class is not registered in the Forge Event Bus then it will never be used by forge at all, which is bad.
            OPElytraHandlerServer handlerServer = OverpoweredTools.OP_ELYTRA_SERVER_HANDLER;

            //player.sendMessage(new TextComponentString("Received PacketOPElytraFlying()"));

            player.getServerWorld().addScheduledTask(() ->
            {
                ItemStack itemstack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                if (itemstack.getItem() == ItemInit.OVERPOWERED_ELYTRA && OverpoweredElytra.isUsable(itemstack))
                {
                    // This logic was copied from lines 1033-1045 of NetHandlerPlayServer.java
                    if (!player.onGround && player.motionY < 0.0D && !player.isElytraFlying() && !player.isInWater() && !player.capabilities.isFlying && !player.isRiding())
                    {
                        //player.sendMessage(new TextComponentString("shouldFly = true"));
                        //player.setElytraFlying();
                        //OPElytraHandlerServer.shouldFly = true;
                        handlerServer.shouldFly.put(player.getUniqueID(), true);
                    }
                    else
                    {
                        //player.sendMessage(new TextComponentString("shouldFly = false"));
                        //player.clearElytraFlying();
                        //OPElytraHandlerServer.shouldFly = false;
                        handlerServer.shouldFly.put(player.getUniqueID(), false);
                    }
                }
            });

            return null;
        }
    }
}
