package pc101.overpoweredtools.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import pc101.overpoweredtools.util.Reference;

public class NetworkHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    public static int packetID;

    public static int getNextPacketId()
    {
        return packetID++;
    }
}
