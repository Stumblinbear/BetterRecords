package com.codingforcookies.betterrecords.client.render;

import com.codingforcookies.betterrecords.common.block.tile.TileEntityRecordSpeaker;
import com.codingforcookies.betterrecords.common.lib.StaticInfo;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class BlockRecordSpeakerRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale, int destroyStage) {
        int size = -1;
        if (te != null)
            size = ((TileEntityRecordSpeaker) te).type;

        GL11.glPushMatrix();
        {
            GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            if (te != null)
                GL11.glRotatef(((TileEntityRecordSpeaker)te).rotation, 0F, 1F, 0F);
            else
                GL11.glRotatef(180F, 0F, 1F, 0F);
            switch(size) {
                case 0:
                    GL11.glTranslatef(.05F, 0F, .05F);
                    bindTexture(StaticInfo.modelSMSpeakerRes);
                    StaticInfo.modelSMSpeaker.render(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
                    break;
                case 1:
                    bindTexture(StaticInfo.modelMDSpeakerRes);
                    StaticInfo.modelMDSpeaker.render(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
                    break;
                case 2:
                    bindTexture(StaticInfo.modelLGSpeakerRes);
                    StaticInfo.modelLGSpeaker.render(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
                    break;
                default:
                    bindTexture(StaticInfo.modelMDSpeakerRes);
                    StaticInfo.modelMDSpeaker.render(null, 0F, 0F, 0F, 0.0F, 0.0F, 0.0625F);
                    break;
            }
        }
        GL11.glPopMatrix();
    }
}
