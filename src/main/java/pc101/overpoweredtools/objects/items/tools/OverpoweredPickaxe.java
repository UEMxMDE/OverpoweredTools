package pc101.overpoweredtools.objects.items.tools;

import pc101.overpoweredtools.OverpoweredTools;
import pc101.overpoweredtools.init.ItemInit;
import net.minecraft.item.ItemPickaxe;

public class OverpoweredPickaxe extends ItemPickaxe
{
    public OverpoweredPickaxe(String name, ToolMaterial material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(OverpoweredTools.OVERPOWEREDTOOLSTAB);

        ItemInit.ITEMS.add(this);
    }
}
