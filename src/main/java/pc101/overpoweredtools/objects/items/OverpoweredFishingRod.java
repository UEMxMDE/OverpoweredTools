package pc101.overpoweredtools.objects.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.OverpoweredTools;
import pc101.overpoweredtools.init.ItemInit;

import javax.annotation.Nullable;

public class OverpoweredFishingRod extends ItemFishingRod
{
    public OverpoweredFishingRod(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(OverpoweredTools.OVERPOWEREDTOOLSTAB);
        setMaxDamage(800); //Durability
        setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation("cast"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if(entityIn == null)
                {
                    return 0.0f;
                }
                else
                {
                    boolean flag = entityIn.getHeldItemMainhand() == stack;
                    boolean flag1 = entityIn.getHeldItemOffhand() == stack;
                    if(entityIn.getHeldItemMainhand().getItem() instanceof OverpoweredFishingRod)
                    {
                        flag1 = false;
                    }
                    return (flag || flag1) && entityIn instanceof EntityPlayer && ((EntityPlayer)entityIn).fishEntity != null ? 1.0F : 0.0F;
                }
            }
        });
        ItemInit.ITEMS.add(this);
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3d()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(playerIn.fishEntity != null)
        {
            int i = playerIn.fishEntity.handleHookRetraction();
            itemstack.damageItem(1, playerIn);      //Probably amount of durability a swing takes from rod, turty's github replaces "1" with "i"
            playerIn.swingArm(handIn);
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        }
        else
        {
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!worldIn.isRemote)
            {
                EntityFishHook entityfishhook = new EntityFishHook(worldIn, playerIn);
                int j = EnchantmentHelper.getFishingSpeedBonus(itemstack);

                if (j > 0)
                {
                    entityfishhook.setLureSpeed(j);
                }

                int k = EnchantmentHelper.getFishingLuckBonus(itemstack);

                if (k > 0)
                {
                    entityfishhook.setLuck(k);
                }

                worldIn.spawnEntity(entityfishhook);
            }

            playerIn.swingArm(handIn);
            playerIn.addStat(StatList.getObjectUseStats(this));
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public int getItemEnchantability()
    {
        return 15;
    }
}