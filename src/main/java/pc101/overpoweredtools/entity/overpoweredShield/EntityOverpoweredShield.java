package pc101.overpoweredtools.entity.overpoweredShield;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityOverpoweredShield extends Entity
{
    public EntityOverpoweredShield(World worldIn)
    {
        super(worldIn);
    }

    @Override
    protected void entityInit()
    {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {

    }
}
