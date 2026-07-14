package pc101.overpoweredtools.util.handlers;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.OverpoweredTools;
import pc101.overpoweredtools.init.EntityInit;
import pc101.overpoweredtools.init.ItemInit;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pc101.overpoweredtools.recipes.CraftingRecipes;
import pc101.overpoweredtools.util.Reference;

@EventBusSubscriber
public class RegistryHandler
{
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event)
    {
        RenderHandler.registerEntityRenders();
        for(Item item : ItemInit.ITEMS)
        {
            OverpoweredTools.proxy.registerItemRenderer(item, 0, "inventory");
        }
    }

    public static void preInitRegistries(FMLPreInitializationEvent event)
    {
        EntityInit.registerEntities();
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
        event.getRegistry().register(new CraftingRecipes.Decoration().setRegistryName(Reference.MOD_ID, "overpoweredshielddecoration"));
    }

    public static void initRegistries()
    {
        CraftingRecipes.init();
        //new CraftingRecipes.Decoration();
    }

    public static void postInitRegistries()
    {

    }

    public static void serverRegistries(FMLServerStartingEvent event)
    {

    }
}
