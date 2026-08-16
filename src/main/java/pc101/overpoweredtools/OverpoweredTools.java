package pc101.overpoweredtools;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import pc101.overpoweredtools.network.NetworkHandler;
import pc101.overpoweredtools.network.PacketOPElytraFlying;
import pc101.overpoweredtools.objects.items.OverpoweredShield;
import pc101.overpoweredtools.proxy.CommonProxy;
import pc101.overpoweredtools.recipes.CraftingRecipes;
import pc101.overpoweredtools.util.Reference;
import pc101.overpoweredtools.tabs.OverpoweredToolsTab;
import pc101.overpoweredtools.util.handlers.OPElytraHandlerClient;
import pc101.overpoweredtools.util.handlers.OPElytraHandlerServer;
import pc101.overpoweredtools.util.handlers.RegistryHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, name = Reference.NAME/*, acceptedMinecraftVersions = Reference.MC_VERSION*/)
public class OverpoweredTools
{
    @Instance
    public static OverpoweredTools instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final CreativeTabs OVERPOWEREDTOOLSTAB = new OverpoweredToolsTab("overpoweredtoolstab");

    public static final OPElytraHandlerServer OP_ELYTRA_SERVER_HANDLER = new OPElytraHandlerServer();   // Separated into OP_ELYTRA_SERVER_HANDLER so the instance can be accessed outside of this class.

    public OverpoweredTools()
    {
        // Classes that are not automatically registered via @Mod.EventBusSubscriber() can be registered in the constructor of the main mod class: https://docs.minecraftforge.net/en/1.12.x/events/intro/
        // Although I have seen people register events via ClientProxy instead: https://mcjty.eu/docs/1.12/networking, I prefer following the forge docs for now.
        // I just wanted to note the above for future reference.
        MinecraftForge.EVENT_BUS.register(new OPElytraHandlerClient());
        //MinecraftForge.EVENT_BUS.register(new OPElytraHandlerServer());
        MinecraftForge.EVENT_BUS.register(OP_ELYTRA_SERVER_HANDLER);    // Separated into OP_ELYTRA_SERVER_HANDLER so the instance can be accessed outside of this class.
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        RegistryHandler.preInitRegistries(event);
        NetworkHandler.INSTANCE.registerMessage(PacketOPElytraFlying.PacketOPElytraFlyingHandler.class, PacketOPElytraFlying.class, NetworkHandler.getNextPacketId(), Side.SERVER);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        CraftingRecipes.init();
        //new CraftingRecipes.Decoration();
        //CraftingRecipes.Decoration.matches();

        proxy.render();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @EventHandler
    public void serverInit(FMLServerStartingEvent event)
    {

    }
}