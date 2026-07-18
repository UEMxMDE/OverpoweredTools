package pc101.overpoweredtools.objects.items;

import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
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
import pc101.overpoweredtools.util.Reference;

/*
Mod that adds new elytras as separate items to the game (the code did not help much): https://www.curseforge.com/minecraft/mc-mods/powered-elytra
- https://github.com/GlassPane/Powered-Elytra
- The library mod required for the mod above: https://www.curseforge.com/minecraft/mc-mods/glasspane
- - The source code for the library mod above: https://github.com/GlassPane/GlassPane
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

    // The below 2 methods came from my research in figuring out how to make this elytra equipable on the chestplate slot of the player: https://forums.minecraftforge.net/topic/64864-solved-creating-a-wearable-item/
    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        // The below line doesn't fix the issue
        //return super.isValidArmor(stack, armorType, entity);

        // The below 2 lines fix the issue?
        return armorType == EntityEquipmentSlot.CHEST;

        //return true;
    }

    // This method seems to not maka a difference in-game?
    /*
    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return super.getEquipmentSlot(stack);
    }
    */

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