package pc101.overpoweredtools.objects.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.OverpoweredTools;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import pc101.overpoweredtools.init.ItemInit;

import javax.annotation.Nullable;

public class OverpoweredBow extends ItemBow
{
    public OverpoweredBow(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(OverpoweredTools.OVERPOWEREDTOOLSTAB);
        setMaxDamage(800);

        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    return entityIn.getActiveItemStack().getItem() != ItemInit.OVERPOWERED_BOW ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });

        ItemInit.ITEMS.add(this);
    }

    @Override
    public int getItemEnchantability()
    {
        return 10;
    }

    @Override
    protected boolean isArrow(ItemStack stack)
    {
        if(stack.getItem() == ItemInit.OVERPOWERED_ARROW)
        {
            return true;
        }
        return false;
    }
}