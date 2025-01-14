package pc101.overpoweredtools.entity.overpoweredArrow;

import pc101.overpoweredtools.init.ItemInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityOverpoweredArrow extends EntityArrow
{
    public EntityOverpoweredArrow(World worldIn)
    {
        super(worldIn);
    }

    public EntityOverpoweredArrow(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityOverpoweredArrow(World worldIn, EntityLivingBase shooter)
    {
        super(worldIn, shooter);
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return new ItemStack(ItemInit.OVERPOWERED_ARROW);
    }

    @Override
    protected void arrowHit(EntityLivingBase living)
    {
        super.arrowHit(living);
        living.onKillCommand();
    }
}
