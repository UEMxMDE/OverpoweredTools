package pc101.overpoweredtools.objects.items;

import pc101.overpoweredtools.OverpoweredTools;
import pc101.overpoweredtools.init.ItemInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pc101.overpoweredtools.entity.overpoweredArrow.EntityOverpoweredArrow;

public class OverpoweredArrow extends ItemArrow
{
    public OverpoweredArrow(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(OverpoweredTools.OVERPOWEREDTOOLSTAB);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter)
    {
        EntityOverpoweredArrow entityOverpoweredArrow = new EntityOverpoweredArrow(worldIn, shooter);
        return entityOverpoweredArrow;
    }
}