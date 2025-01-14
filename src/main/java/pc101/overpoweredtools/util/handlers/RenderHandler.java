package pc101.overpoweredtools.util.handlers;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.entity.overpoweredArrow.EntityOverpoweredArrow;
import pc101.overpoweredtools.entity.overpoweredArrow.RenderOverpoweredArrow;
import pc101.overpoweredtools.entity.overpoweredShield.EntityOverpoweredShield;
import pc101.overpoweredtools.entity.overpoweredShield.RenderOverpoweredShield;
import pc101.overpoweredtools.entity.test.EntityTest;
import pc101.overpoweredtools.entity.test.RenderTest;

@SideOnly(Side.CLIENT)
public class RenderHandler
{
    public static void registerEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new IRenderFactory<EntityTest>()
        {
            @Override
            public Render<? super EntityTest> createRenderFor(RenderManager manager)
            {
                return new RenderTest(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityOverpoweredArrow.class, new IRenderFactory<EntityOverpoweredArrow>()
        {
            @Override
            public Render<? super EntityOverpoweredArrow> createRenderFor(RenderManager manager)
            {
                return new RenderOverpoweredArrow(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityOverpoweredShield.class, new IRenderFactory<EntityOverpoweredShield>()
        {
            @Override
            public Render<? super EntityOverpoweredShield> createRenderFor(RenderManager manager)
            {
                return new RenderOverpoweredShield(manager);
            }
        });
    }
}