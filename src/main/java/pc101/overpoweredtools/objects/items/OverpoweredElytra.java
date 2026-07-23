package pc101.overpoweredtools.objects.items;

import net.minecraft.block.BlockDispenser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.OverpoweredTools;
import pc101.overpoweredtools.init.ItemInit;
import pc101.overpoweredtools.util.Reference;

/*
Mod that adds new elytras as separate items to the game (the code did not help much): https://www.curseforge.com/minecraft/mc-mods/powered-elytra
- https://github.com/GlassPane/Powered-Elytra
- The library mod required for the mod above: https://www.curseforge.com/minecraft/mc-mods/glasspane
- - The source code for the library mod above: https://github.com/GlassPane/GlassPane

Another mod that adds new elytras but it is so broken that I can't even compile and run it: https://github.com/elytra/Wings/

Another mod I found that comes from this forum post: https://forums.minecraftforge.net/topic/63746-question-about-tweaked-elytra/
Which then leads to this PR: https://github.com/MinecraftForge/MinecraftForge/pull/4476
Which then (if you scroll down): leads to an elytra in this "test mod" by maxanier: https://github.com/pWn3d1337/MinecraftForge/blob/b0da9c0f92992eeeca588e8876b47286214f7ab3/src/test/java/net/minecraftforge/debug/ElytraTest.java
*/

import javax.annotation.Nullable;

public class OverpoweredElytra extends ItemElytra
{
    public OverpoweredElytra(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(OverpoweredTools.OVERPOWEREDTOOLSTAB);
        setMaxDamage(1000);

        addPropertyOverride(new ResourceLocation("broken"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return ItemElytra.isUsable(stack) ? 0.0F : 1.0F;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);

        ItemInit.ITEMS.add(this);
    }

    // The below 3 methods came from my research in figuring out how to make this elytra equipable on the chestplate slot of the player: https://forums.minecraftforge.net/topic/64864-solved-creating-a-wearable-item/
    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        // Option 1: The below line doesn't fix the issue of the player not being able to put this elytra in their chestplate slot.
        //return super.isValidArmor(stack, armorType, entity);

        // Option 2: The below line fixes the above issue (see Item.java).
        return armorType == EntityEquipmentSlot.CHEST;

        // Option 3: The below line also fixes the above issue, but it also introduces a new issue, which is that it allows this elytra to be equipped in all 4 armor slots.
        //return true;
    }

    // This method allows this elytra to be equipped in the chest slot if:
    // 1. The player is holding this elytra in their hand
    // 2. The player's chestplate slot is empty.
    // This method also works without @Override, which is cool.
    // If this method was not included, right clicking this elytra would not work.
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        // The non-commented code in this method is copied from the vanilla ItemElytra.java file.
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        EntityEquipmentSlot entityequipmentslot =
                EntityEquipmentSlot.CHEST;  // In ItemElytra.java this line was EntityLiving.getSlotForItemStack(itemstack);. I had to change it to EntityEquipmentSlot.CHEST because otherwise right-clicking this elytra while holding it would not equip this elytra.
        ItemStack itemstack1 = playerIn.getItemStackFromSlot(entityequipmentslot);

        if (itemstack1.isEmpty())
        {
            playerIn.setItemStackToSlot(entityequipmentslot, itemstack.copy());
            itemstack.setCount(0);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        }

        // The code between lines 163-172 (including line 163 and line 172) from here also does the same as above: https://github.com/GlassPane/Powered-Elytra/blob/efe9d42a7032b61813cb14cbf56f35a25100f939/src/main/java/com/github/upcraftlp/powerelytra/item/ItemPowerElytra.java#L163
    }

    // This method allows the player to SHIFT+Left Click this elytra into their chest slot.
    // If this method was not included, SHIFT+Left Clicking this elytra would not work.
    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        // In Item.java, this line is return null;. I had to change it to EntityEquipmentSlot.CHEST because otherwise SHIFT-Left Clicking this elytra in your inventory would not equip this elytra.
        return EntityEquipmentSlot.CHEST;
    }

    // Does not work, I'm not 100% sure what it is supposed to do.
    /*
    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if(slot == EntityEquipmentSlot.CHEST) { // If this elytra is equipped in the chest slot
            return Reference.MOD_ID + ":textures/entity/overpowered_elytra.png";
        }
        return null;
    }
    */

    // I don't know what this is supposed to do.
    /*
    @Nullable
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
    }
    */
}