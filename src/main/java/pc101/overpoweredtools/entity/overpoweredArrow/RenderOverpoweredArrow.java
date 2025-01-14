package pc101.overpoweredtools.entity.overpoweredArrow;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.util.Reference;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderOverpoweredArrow extends RenderArrow<EntityOverpoweredArrow>
{
    public RenderOverpoweredArrow(RenderManager manager)
    {
        super(manager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityOverpoweredArrow entity)
    {
        return new ResourceLocation(Reference.MOD_ID + ":textures/entity/arrows/overpowered_arrow.png");
    }
}