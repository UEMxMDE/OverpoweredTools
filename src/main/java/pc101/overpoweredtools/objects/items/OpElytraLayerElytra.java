package pc101.overpoweredtools.objects.items;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.init.ItemInit;
import pc101.overpoweredtools.util.Reference;

@SideOnly(Side.CLIENT)
public class OpElytraLayerElytra extends LayerElytra
{
    public static final ResourceLocation TEXTURE_OVERPOWERED_ELYTRA = new ResourceLocation(Reference.MOD_ID + ":textures/entity/overpowered_elytra.png");
    public final RenderLivingBase<?> renderPlayer;
    public final ModelElytra modelElytra = new ModelElytra();
    public final ModelOPElytra modelOPElytra = new ModelOPElytra(); // The vanilla ModelElytra.java can be used as well, but I don't know if that is a good idea. Maybe it's better to use my own model even though it extends the vanilla ModelElytra because I don't know if I want changes made to ModelElytra caused by other mods to be applied to this elytra as well.

    public OpElytraLayerElytra(RenderLivingBase<?> p_i47185_1_) {
        super(p_i47185_1_);
        this.renderPlayer = p_i47185_1_;
    }

    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

        if (itemstack.getItem() == ItemInit.OVERPOWERED_ELYTRA)     // Connects this renderer to the Overpowered Elytra item.
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

            if (entitylivingbaseIn instanceof AbstractClientPlayer)
            {
                AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entitylivingbaseIn;

                if (abstractclientplayer.isPlayerInfoSet() && abstractclientplayer.getLocationElytra() != null) // abstractclientplayer.getLocationElytra() is hard-coded to the vanilla elytra. Check if it is necessary to replace this with my custom elytra or to keep this code the way it is.
                {
                    this.renderPlayer.bindTexture(abstractclientplayer.getLocationElytra());    // abstractclientplayer.getLocationElytra() is hard-coded to the vanilla elytra. Check if it is necessary to replace this with my custom elytra or to keep this code the way it is.
                }
                else if (abstractclientplayer.hasPlayerInfo() && abstractclientplayer.getLocationCape() != null && abstractclientplayer.isWearing(EnumPlayerModelParts.CAPE))
                {
                    this.renderPlayer.bindTexture(abstractclientplayer.getLocationCape());
                }
                else
                {
                    this.renderPlayer.bindTexture(TEXTURE_OVERPOWERED_ELYTRA);
                }
            }
            else
            {
                this.renderPlayer.bindTexture(TEXTURE_OVERPOWERED_ELYTRA);
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.125F);
            this.modelOPElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
            this.modelOPElytra.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

            if (itemstack.isItemEnchanted())
            {
                LayerArmorBase.renderEnchantedGlint(this.renderPlayer, entitylivingbaseIn, this.modelOPElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }

            // If this line is not included, then if the player equips an enchanted overpowered elytra, the knowledge book button in the survival mode player's inventory will turn into a dark shade of opaque purple.
            // This is because LayerArmorBase#renderEnchantedGlint never resets the GlStateManager.color() back to white for some reason.
            // To make things weirder, the vanilla LayerElytra does not have the line below and yet the enchanted vanilla elytra does not have this bug. Why does a custom elytra renderer need the line below but not the vanilla elytra renderer (a.k.a. LayerElytra.java)?
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
}
