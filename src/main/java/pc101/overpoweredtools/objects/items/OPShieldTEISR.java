package pc101.overpoweredtools.objects.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pc101.overpoweredtools.init.ItemInit;

@SideOnly(Side.CLIENT)
public class OPShieldTEISR extends TileEntityItemStackRenderer {
    public static OPShieldTEISR instance;   // May be redundant and thus may need to be removed.
    private final TileEntityBanner banner = new TileEntityBanner();
    private final ModelShield modelShield = new ModelShield();
    private final ModelOPShield modelOPShield = new ModelOPShield();

    private static final BannerTextures.Cache OP_SHIELD_DESIGNS =
            new BannerTextures.Cache(
                    "OVERPOWERED_SHIELD",
                    new ResourceLocation("overpoweredtools:textures/entity/overpowered_shield_base.png"),  // The texture of any overpowered shield that DOES NOT have a banner on it.
                    "textures/entity/shield/"   // Every banner pattern on this shield. This line is currently pointing to the vanilla directory of textures/entity/shield/
            );

    public void renderByItem(ItemStack itemStackIn)
    {
        this.renderByItem(itemStackIn, 1.0F);
    }

    public void renderByItem(ItemStack p_192838_1_, float partialTicks)
    {
        Item item = p_192838_1_.getItem();

        if (item == Items.BANNER)
        {
            this.banner.setItemValues(p_192838_1_, false);
            TileEntityRendererDispatcher.instance.render(this.banner, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks);
        }
        else if (item == ItemInit.OVERPOWERED_SHIELD)
        {
            if (p_192838_1_.getSubCompound("BlockEntityTag") != null)
            {
                this.banner.setItemValues(p_192838_1_, true);
                // In the line below, I replaced "BannerTextures.SHIELD_DESIGNS" with "OP_SHIELD_DESIGNS"
                // because BannerTextures.SHIELD_DESIGNS was rendering a banner on a vanilla shield texture,
                // then it put that texture on the overpowered shield. I created OP_SHIELD_DESIGNS to make
                // sure that the banner texture gets rendered on my overpowered shield texture instead.
                Minecraft.getMinecraft().getTextureManager().bindTexture(OP_SHIELD_DESIGNS.getResourceLocation(this.banner.getPatternResourceLocation(), this.banner.getPatternList(), this.banner.getColorList()));
            }
            else
            {
                // In the line below, I replaced BannerTextures.SHIELD_BASE_TEXTURE (from the TEISR parent class) with
                // new ResourceLocation("overpoweredtools:textures/items/overpowered_shield_base_nopattern.png")
                // because I wanted overpowered shields that had been crafted with a banner to use my custom shield
                // base "nopattern" texture instead of the vanilla shield base texture.
                // (not the banner itself but the rest of the shield texture)
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("overpoweredtools:textures/entity/overpowered_shield_base_nopattern.png"));
            }

            // I added the line below (GlStateManager.disableCull();) to fix a bug where:
            // 1. A banner on an overpowered shield would not render in first person
            // 2. Nor would the banner render if an overpowered shield item was dropped and viewed from behind.
            // This bug only existed because this overpowered shield uses transparent textures in places that allow for the viewing of banners on the shield from behind the shield.
            // Note: GlStateManager.disableCull(); must be called BEFORE the this.modelShield.render(); call or else it will not fix this bug at all.
            GlStateManager.disableCull();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, -1.0F, -1.0F);
            //this.modelShield.render();
            this.modelOPShield.render();    // The vanilla ModelShield.java can be used as well, but I don't know if that is a good idea. Maybe it's better to use my own model even though it extends the vanilla ModelShield because I don't know if I want changes made to ModelShield caused by other mods to be applied to this shield as well.
            // If cull is ever disabled, it needs to be re-enabled (see the GlStateManager.enableCull(); line below) to prevent potential rendering bugs in other parts of Minecraft.
            // In the case of this shield, GlStateManager.enableCull(); needs to be called AFTER the this.modelShield.render(); call or else it will reintroduce the bug that the GlStateManager.disableCull(); line above is supposed to fix.
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }
    }
}
