package pc101.overpoweredtools.objects.items;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.OverpoweredTools;
import pc101.overpoweredtools.init.ItemInit;

import javax.annotation.Nullable;

public class OverpoweredElytra extends ItemElytra
{
    public OverpoweredElytra(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(OverpoweredTools.OVERPOWEREDTOOLSTAB);
        setMaxDamage(1000);

        /*addPropertyOverride(new ResourceLocation("broken"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return ItemElytra.isUsable(stack) ? 0.0F : 1.0F;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);*/

        ItemInit.ITEMS.add(this);
    }
}