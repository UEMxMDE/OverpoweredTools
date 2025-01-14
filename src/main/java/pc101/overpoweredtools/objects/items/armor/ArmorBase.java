package pc101.overpoweredtools.objects.items.armor;

import pc101.overpoweredtools.OverpoweredTools;
import pc101.overpoweredtools.init.ItemInit;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ArmorBase extends ItemArmor
{
    public ArmorBase(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot)
    {
        super(material, renderIndex, equipmentSlot);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(OverpoweredTools.OVERPOWEREDTOOLSTAB);

        ItemInit.ITEMS.add(this);
    }
}