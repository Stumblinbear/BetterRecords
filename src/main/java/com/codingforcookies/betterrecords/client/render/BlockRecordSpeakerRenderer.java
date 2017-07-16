package com.codingforcookies.betterrecords.client.render;

import com.codingforcookies.betterrecords.ConstantsKt;
import com.codingforcookies.betterrecords.client.model.ModelLGSpeaker;
import com.codingforcookies.betterrecords.client.model.ModelMDSpeaker;
import com.codingforcookies.betterrecords.client.model.ModelSMSpeaker;
import com.codingforcookies.betterrecords.block.tile.TileSpeaker;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BlockRecordSpeakerRenderer extends TileEntitySpecialRenderer {

    private static final ModelSMSpeaker MODEL_SM = new ModelSMSpeaker();
    private static final ResourceLocation TEXTURE_SM = new ResourceLocation(ConstantsKt.ID, "textures/models/smspeaker.png");
    private static final ModelMDSpeaker MODEL_MD = new ModelMDSpeaker();
    private static final ResourceLocation TEXTURE_MD = new ResourceLocation(ConstantsKt.ID, "textures/models/mdspeaker.png");
    private static final ModelLGSpeaker MODEL_LG = new ModelLGSpeaker();
    private static final ResourceLocation TEXTURE_LG = new ResourceLocation(ConstantsKt.ID, "textures/models/lgspeaker.png");

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale, int destroyStage) {
        int size = -1;
        if (te != null)
            size = ((TileSpeaker) te).type;

        GL11.glPushMatrix();
        {
            GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            if (te != null)
                GL11.glRotatef(((TileSpeaker)te).rotation, 0F, 1F, 0F);
            switch(size) {
                case 0:
                    GL11.glTranslatef(.05F, 0F, .05F);
                    bindTexture(TEXTURE_SM);
                    MODEL_SM.render(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
                    break;
                case 1:
                    bindTexture(TEXTURE_MD);
                    MODEL_MD.render(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
                    break;
                case 2:
                    bindTexture(TEXTURE_LG);
                    MODEL_LG.render(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
                    break;
                default:
                    bindTexture(TEXTURE_MD);
                    MODEL_MD.render(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
                    break;
            }
        }
        GL11.glPopMatrix();
    }
}
