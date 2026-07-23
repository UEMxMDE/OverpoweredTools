package pc101.overpoweredtools.recipes;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShieldRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import pc101.overpoweredtools.init.ItemInit;

public class CraftingRecipes
{
    private static int nextAvailableId;
    public static final RegistryNamespaced<ResourceLocation, IRecipe> REGISTRY = net.minecraftforge.registries.GameData.getWrapper(IRecipe.class);

    public static void init()
    {
        // Obsidian
        ItemStack obsidian = new ItemStack(Block.getBlockById(49), 9);     // Obsidian iD 49
        ItemStack cobblestone = new ItemStack(Block.getBlockById(4));   // Cobblestone is ID 4
        GameRegistry.addShapelessRecipe(new ResourceLocation("obsidian"), null, obsidian, Ingredient.fromStacks(cobblestone));

        // Overpowered Shield with banners
        //register("overpoweredshielddecoration", new ShieldRecipes.Decoration());
        /*try
        {
            register("overpoweredshielddecoration", new ShieldRecipes.Decoration());
        }
        catch(Throwable e)
        {
            System.err.println(e);
        }*/
    }

    // The below two "register" methods are copied from CraftingManager.java
    private static void register(String name, IRecipe recipe)
    {
        register(new ResourceLocation(name), recipe);
    }

    private static void register(ResourceLocation name, IRecipe recipe)
    {
        if (REGISTRY.containsKey(name))
        {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + name);
        }
        else
        {
            REGISTRY.register(nextAvailableId++, name, recipe);
        }
    }

    // Allows the Overpowered Shield to be crafted with banners in the same way the vanilla shield is crafted with banners.
    public static class Decoration extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
    {
        public boolean matches(InventoryCrafting inv, World worldIn)
        {
            ItemStack itemstack = ItemStack.EMPTY;
            ItemStack itemstack1 = ItemStack.EMPTY;

            for (int i = 0; i < inv.getSizeInventory(); ++i)
            {
                ItemStack itemstack2 = inv.getStackInSlot(i);

                if (!itemstack2.isEmpty())
                {
                    if (itemstack2.getItem() == Items.BANNER)
                    {
                        if (!itemstack1.isEmpty())
                        {
                            return false;
                        }

                        itemstack1 = itemstack2;
                    }
                    else
                    {
                        if (itemstack2.getItem() != ItemInit.OVERPOWERED_SHIELD)    // Used to be connected to the vanilla shield.
                        {
                            return false;
                        }

                        if (!itemstack.isEmpty())
                        {
                            return false;
                        }

                        if (itemstack2.getSubCompound("BlockEntityTag") != null)
                        {
                            return false;
                        }

                        itemstack = itemstack2;
                    }
                }
            }

            if (!itemstack.isEmpty() && !itemstack1.isEmpty())
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public ItemStack getCraftingResult(InventoryCrafting inv)
        {
            ItemStack itemstack = ItemStack.EMPTY;
            ItemStack itemstack1 = ItemStack.EMPTY;

            for (int i = 0; i < inv.getSizeInventory(); ++i)
            {
                ItemStack itemstack2 = inv.getStackInSlot(i);

                if (!itemstack2.isEmpty())
                {
                    if (itemstack2.getItem() == Items.BANNER)
                    {
                        itemstack = itemstack2;
                    }
                    else if (itemstack2.getItem() == ItemInit.OVERPOWERED_SHIELD)   // Used to be connected to the vanilla shield.
                    {
                        itemstack1 = itemstack2.copy();
                    }
                }
            }

            if (itemstack1.isEmpty())
            {
                return itemstack1;
            }
            else
            {
                NBTTagCompound nbttagcompound = itemstack.getSubCompound("BlockEntityTag");
                NBTTagCompound nbttagcompound1 = nbttagcompound == null ? new NBTTagCompound() : nbttagcompound.copy();
                nbttagcompound1.setInteger("Base", itemstack.getMetadata() & 15);
                itemstack1.setTagInfo("BlockEntityTag", nbttagcompound1);
                return itemstack1;
            }
        }

        public ItemStack getRecipeOutput()
        {
            return ItemStack.EMPTY;
        }

        public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
        {
            NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

            for (int i = 0; i < nonnulllist.size(); ++i)
            {
                ItemStack itemstack = inv.getStackInSlot(i);

                if (itemstack.getItem().hasContainerItem())
                {
                    nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
                }
            }

            return nonnulllist;
        }

        public boolean isDynamic()
        {
            return true;
        }

        public boolean canFit(int width, int height)
        {
            return width * height >= 2;
        }
    }
}
