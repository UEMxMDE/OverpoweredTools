package pc101.overpoweredtools.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.objects.items.OpElytraLayerElytra;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }


    @Override
    public void render()
    {
        // -----------------------------------------------------------------------------------------------------
        // The below code is only for rendering the Overpowered Elytra on the back of the player and on other entities that the vanilla elytra can also render on.
        // This code is for rendering the Overpowered Elytra on the back of the player.
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        for(RenderPlayer renderPlayer : skinMap.values())
        {
            renderPlayer.addLayer(new OpElytraLayerElytra(renderPlayer));
        }

        // This code is for rendering the Overpowered Elytra on the back of the same non-player entities that the vanilla elytra can also render on.
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        for (Render<? extends Entity> render : renderManager.entityRenderMap.values())
        {
            if (render instanceof RenderArmorStand)
            {
                ((RenderArmorStand)render).addLayer(new OpElytraLayerElytra((RenderArmorStand)render)); // Renders this elytra on an armor stand if the armor stand has it equipped in its chest slot.
            }
            else if(render instanceof RenderBiped)
            {
                ((RenderBiped<?>)render).addLayer(new OpElytraLayerElytra((RenderBiped<?>)render)); // Renders this elytra on biped mobs if they have it equipped in their chest slot.
            }
        }

        // This code works as an alternative to the above code.
        /*
        // This code is for rendering the Overpowered Elytra on the back of the player.
        for (RenderPlayer renderPlayer : Minecraft.getMinecraft().getRenderManager().getSkinMap().values())
        {
            renderPlayer.addLayer(new OpElytraLayerElytra(renderPlayer));
        }

        // This code is for rendering the Overpowered Elytra on the back of the same non-player entities that the vanilla elytra can also render on.
        for (Render<? extends Entity> render : Minecraft.getMinecraft().getRenderManager().entityRenderMap.values())
        {
            if (render instanceof RenderArmorStand)
            {
                ((RenderArmorStand)render).addLayer(new OpElytraLayerElytra((RenderArmorStand)render));
            }
            else if(render instanceof RenderBiped)
            {
                ((RenderBiped<?>)render).addLayer(new OpElytraLayerElytra((RenderBiped<?>)render));
            }
        }

        // Alternative to the above for loop that is NOT recommended because it allows for the ability for rendering the Overpowered Elytra on more entities than the vanilla elytra can render on.
        for (Render<? extends Entity> render : Minecraft.getMinecraft().getRenderManager().entityRenderMap.values())
        {
            if (render instanceof RenderLivingBase)
            {
                ((RenderLivingBase<?>)render).addLayer(new OpElytraLayerElytra((RenderLivingBase<?>)render));
            }
        }
        */
        // -----------------------------------------------------------------------------------------------------
    }
}