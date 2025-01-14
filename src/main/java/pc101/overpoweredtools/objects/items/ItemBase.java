package pc101.overpoweredtools.objects.items;

import pc101.overpoweredtools.OverpoweredTools;
import pc101.overpoweredtools.init.ItemInit;
import net.minecraft.item.Item;

public class ItemBase extends Item
{
    public ItemBase(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(OverpoweredTools.OVERPOWEREDTOOLSTAB);

        ItemInit.ITEMS.add(this);
    }
}