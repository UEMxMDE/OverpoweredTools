package pc101.overpoweredtools.init;

import pc101.overpoweredtools.objects.items.*;
import pc101.overpoweredtools.objects.items.armor.ArmorBase;
import pc101.overpoweredtools.objects.items.tools.OverpoweredSword;
import pc101.overpoweredtools.objects.items.tools.OverpoweredHoe;
import pc101.overpoweredtools.objects.items.tools.OverpoweredPickaxe;
import pc101.overpoweredtools.objects.items.tools.OverpoweredShovel;
import pc101.overpoweredtools.objects.items.tools.OverpoweredAxe;
import pc101.overpoweredtools.util.Reference;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemInit
{
    public static final List<Item> ITEMS = new ArrayList<Item>();

    //Materials
    public static final ArmorMaterial ARMOR_OVERPOWERED = EnumHelper.addArmorMaterial("armor_overpowered", Reference.MOD_ID + ":overpowered", 1500, new int[] {4, 7, 9, 5}, 17, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F);
    public static final ToolMaterial TOOL_OVERPOWERED = EnumHelper.addToolMaterial("tool_overpowered", 7, 1500, 15.0f, 7.0f, 19);

    //Armor
    public static final Item HELMET_OVERPOWERED = new ArmorBase("overpowered_helmet", ARMOR_OVERPOWERED, 1, EntityEquipmentSlot.HEAD);
    public static final Item CHESTPLATE_OVERPOWERED = new ArmorBase("overpowered_chestplate", ARMOR_OVERPOWERED, 1, EntityEquipmentSlot.CHEST);
    public static final Item LEGGINGS_OVERPOWERED = new ArmorBase("overpowered_leggings", ARMOR_OVERPOWERED, 2, EntityEquipmentSlot.LEGS);
    public static final Item BOOTS_OVERPOWERED = new ArmorBase("overpowered_boots", ARMOR_OVERPOWERED, 1, EntityEquipmentSlot.FEET);

    //Tools
    public static final Item AXE_OVERPOWERED = new OverpoweredAxe("overpowered_axe", TOOL_OVERPOWERED);
    public static final Item HOE_OVERPOWERED = new OverpoweredHoe("overpowered_hoe", TOOL_OVERPOWERED);
    public static final Item PICKAXE_OVERPOWERED = new OverpoweredPickaxe("overpowered_pickaxe", TOOL_OVERPOWERED);
    public static final Item SHOVEL_OVERPOWERED = new OverpoweredShovel("overpowered_shovel", TOOL_OVERPOWERED);
    public static final Item SWORD_OVERPOWERED = new OverpoweredSword("overpowered_sword", TOOL_OVERPOWERED);

    //Miscellaneous
    public static final Item OVERPOWERED_BOW = new OverpoweredBow("overpowered_bow");
    public static final Item OVERPOWERED_ARROW = new OverpoweredArrow("overpowered_arrow");
    public static final Item OVERPOWERED_FISHING_ROD = new OverpoweredFishingRod("overpowered_fishing_rod");
    public static final Item OVERPOWERED_SHIELD = new OverpoweredShield("overpowered_shield");
    public static final Item OVERPOWERED_SHEARS = new OverpoweredShears("overpowered_shears");
    public static final Item OVERPOWERED_FLINT_AND_STEEL = new OverpoweredFlintAndSteel("overpowered_flint_and_steel");
    public static final Item OVERPOWERED_ELYTRA = new OverpoweredElytra("overpowered_elytra");
}