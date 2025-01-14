package pc101.overpoweredtools.objects.items;

import net.minecraft.item.ItemFlintAndSteel;
import pc101.overpoweredtools.OverpoweredTools;
import pc101.overpoweredtools.init.ItemInit;

public class OverpoweredFlintAndSteel extends ItemFlintAndSteel
{
    public OverpoweredFlintAndSteel(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(OverpoweredTools.OVERPOWEREDTOOLSTAB);
        setMaxDamage(1000);

        ItemInit.ITEMS.add(this);
    }
}
