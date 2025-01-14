package pc101.overpoweredtools.entity.test;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityTest extends EntityAgeable
{
    public EntityTest(World worldIn)
    {
        super(worldIn);
        this.setSize(width, height);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(2, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWander(this, 2.0D));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.5D, true));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityMob.class, 4.0F, 2.2D, 2.2D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 1.0F));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        this.setSize(0.5f, 0.5f);
        return new EntityTest(world);
    }

    /*@Nullable
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundHandler.TEST_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundHandler.TEST_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundHandler.TEST_HURT;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return super.getLootTable();
    }*/
}