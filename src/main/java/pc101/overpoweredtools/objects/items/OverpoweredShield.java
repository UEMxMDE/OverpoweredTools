package pc101.overpoweredtools.objects.items;

import net.minecraft.block.BlockDispenser;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.OverpoweredTools;
import pc101.overpoweredtools.init.ItemInit;

import javax.annotation.Nullable;
import java.util.List;

//@EventBusSubscriber     // Is required for any @SubscribeEvent methods to work in this class.
public class OverpoweredShield extends ItemShield
{
    public OverpoweredShield(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(OverpoweredTools.OVERPOWEREDTOOLSTAB);
        setMaxDamage(10);

        addPropertyOverride(new ResourceLocation("overpowered_shield_blocking"), new IItemPropertyGetter()  // Figure out what this does. Once you figure it out, then figure out whether it is necessary for this file and also figure out whether it is necessary for all other files in my mod that have this block of code.
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);    // Figure out what this line does

        // This line make this shield use the custom TileEntityItemStackRenderer that I made called OPShieldTEISR.
        // The reason for this is to make banners able to be rendered on the shield.
        this.setTileEntityItemStackRenderer(new OPShieldTEISR());

        ItemInit.ITEMS.add(this);

        //TileEntityItemStackRenderer a = new TileEntityItemStackRenderer();            // these 2 lines came from here: https://forums.minecraftforge.net/topic/65787-solved1122how-to-get-a-model-for-a-shield/
        //a.renderByItem(new ItemStack(this), 0);                                       // not sure how to use this
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)      // This method is required for the overpowered shield name to show up properly
    {
        if (stack.getSubCompound("BlockEntityTag") != null)
        {
            EnumDyeColor enumdyecolor = TileEntityBanner.getColor(stack);
            return I18n.translateToLocal("item.overpowered_shield." + enumdyecolor.getUnlocalizedName() + ".name");
        }
        else
        {
            return I18n.translateToLocal("item.overpowered_shield.name");
        }
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity)     // This method is required for the overpowered shield to take damage because setMaxDamage() doesn't do the job for some reason
    {
        // Option 1 (see Cadiboo's comment about damage: https://forums.minecraftforge.net/topic/66462-solvedswordshield-item-problems/) [works]
        //return true;

        // Option 2 (see Cadiboo's comment about damage: https://forums.minecraftforge.net/topic/66462-solvedswordshield-item-problems/) [can't figure out how to get this to work]
        // I'm not sure how to do what Cadiboo is saying because I don't know how to check if the shield is blocking.
        /*
        if(entity != null && <something here that checks if the shield is blocking>) {
            return true;
        }
        return false;
        */

        // Option 3 (see Item.java) [works]
        return stack.getItem() == ItemInit.OVERPOWERED_SHIELD;

        // Which one of these is better, option 1 or option 3?
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) // Figure out what this method does
    {
        ItemBanner.appendHoverTextFromTileEntityTag(stack, tooltip);
    }

    // Not sure why this was here. Commenting it out made no changes to this shield's behaviour.
    /*@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }*/

    /*
    Broken method, don't use. It was supposed to make this shield take damage,
    but it doesn't do that properly. It makes the player take damage while
    blocking even when the shield is not facing the damage source. For example,
    I could be holding right-click to block and a zombie that attacks me from
    behind would still damage the shield.
    */
    /*@SubscribeEvent
    public static void onBlockedShieldHit(LivingAttackEvent event)      // This method is required for the shield to actually take damage when hit. For some reason the shield did not take damage at all without this method despite the fact that setMaxDamage() was already set up with a value in the constructor.
    {
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer playerIn = (EntityPlayer) event.getEntityLiving();
            ItemStack itemStack = playerIn.getActiveItemStack();

            if (itemStack.getItem() instanceof OverpoweredShield && playerIn.isActiveItemStackBlocking())
            {
                if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityLivingBase)
                {
                    itemStack.damageItem(1, playerIn);
                }
            }
        }
    }*/

    /*@SideOnly(Side.CLIENT)
    public void initModel()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Objects.requireNonNull(getRegistryName()), "inventory"));
    }*/
}