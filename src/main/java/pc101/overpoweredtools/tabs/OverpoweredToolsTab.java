package pc101.overpoweredtools.tabs;

import pc101.overpoweredtools.init.ItemInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class OverpoweredToolsTab extends CreativeTabs
{
    public OverpoweredToolsTab(String label)
    {
        super("overpoweredtoolstab");
        this.setBackgroundImageName("overpoweredtools.png");
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(ItemInit.SWORD_OVERPOWERED);
    }
}
