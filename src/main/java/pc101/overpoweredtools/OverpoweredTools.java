package pc101.overpoweredtools;

import net.minecraftforge.common.MinecraftForge;
import pc101.overpoweredtools.objects.items.OverpoweredShield;
import pc101.overpoweredtools.proxy.CommonProxy;
import pc101.overpoweredtools.recipes.CraftingRecipes;
import pc101.overpoweredtools.util.Reference;
import pc101.overpoweredtools.tabs.OverpoweredToolsTab;
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

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        RegistryHandler.preInitRegistries(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        CraftingRecipes.init();
        new CraftingRecipes.Decoration();
        //CraftingRecipes.Decoration.matches();
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