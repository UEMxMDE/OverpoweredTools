package pc101.overpoweredtools.init;

import pc101.overpoweredtools.entity.overpoweredArrow.EntityOverpoweredArrow;
import pc101.overpoweredtools.entity.overpoweredShield.EntityOverpoweredShield;
import pc101.overpoweredtools.entity.test.EntityTest;
import pc101.overpoweredtools.util.Reference;
import pc101.overpoweredtools.OverpoweredTools;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit
{
    public static void registerEntities()
    {
        registerEntity("test", EntityTest.class, Reference.ENTITY_TEST, 50, 13310623, 65354);
        registerArrow("overpowered_arrow", EntityOverpoweredArrow.class, Reference.ENTITY_OVERPOWERED_ARROW);
        registerShield("overpowered_shield", EntityOverpoweredShield.class, Reference.ENTITY_OVERPOWERED_SHIELD);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, OverpoweredTools.instance, range, 1, true, color1, color2);
    }

    private static void registerArrow(String name, Class<? extends Entity> entity, int id)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, OverpoweredTools.instance, 64, 20, true);
    }

    private static void registerShield(String name, Class<? extends Entity> entity, int id)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, OverpoweredTools.instance, 0, 1, true);
    }

    private static void registerNonMobEntity()
    {

    }
}
