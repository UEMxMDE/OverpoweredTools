package pc101.overpoweredtools.entity.overpoweredShield;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.util.Reference;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderOverpoweredShield extends Render<EntityOverpoweredShield>
{
    public RenderOverpoweredShield(RenderManager manager)
    {
        super(manager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityOverpoweredShield entity)
    {
        return new ResourceLocation(Reference.MOD_ID + ":textures/entity/overpowered_shield.png");
    }
}