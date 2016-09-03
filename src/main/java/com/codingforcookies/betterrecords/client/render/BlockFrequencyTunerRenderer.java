package com.codingforcookies.betterrecords.client.render;

import com.codingforcookies.betterrecords.common.block.tile.TileEntityFrequencyTuner;
import com.codingforcookies.betterrecords.common.lib.StaticInfo;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class BlockFrequencyTunerRenderer extends TileEntitySpecialRenderer<TileEntityFrequencyTuner> {

    @Override
    public void renderTileEntityAt(TileEntityFrequencyTuner te, double x, double y, double z, float scale, int destroyStage) {
        if(te == null) {
            GL11.glPushMatrix();
            {
                GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                bindTexture(StaticInfo.modelFrequencyTunerRes);
                StaticInfo.modelFrequencyTuner.render(null, 0, 0F, 0F, 0.0F, 0.0F, 0.0625F, null);
            }
            GL11.glPopMatrix();
            return;
        }

        GL11.glPushMatrix();
        {
            GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(te.getBlockMetadata() * 90 + 180, 0.0F, 1.0F, 0.0F);
            bindTexture(StaticInfo.modelFrequencyTunerRes);
            StaticInfo.modelFrequencyTuner.render(null, te.crystalFloaty, 0F, 0F, 0.0F, 0.0F, 0.0625F, te.crystal);
        }
        GL11.glPopMatrix();
    }
}
