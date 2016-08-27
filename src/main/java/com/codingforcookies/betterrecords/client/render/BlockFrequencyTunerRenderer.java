package com.codingforcookies.betterrecords.client.render;

import com.codingforcookies.betterrecords.common.block.tile.TileEntityFrequencyTuner;
import com.codingforcookies.betterrecords.common.lib.StaticInfo;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class BlockFrequencyTunerRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale, int destroyStage) {
        if(!(te instanceof TileEntityFrequencyTuner)) {
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

        TileEntityFrequencyTuner tileEntityFrequencyTuner = (TileEntityFrequencyTuner)te;

        GL11.glPushMatrix();
        {
            GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(te.getBlockMetadata() * 90 + 180, 0.0F, 1.0F, 0.0F);
            bindTexture(StaticInfo.modelFrequencyTunerRes);
            StaticInfo.modelFrequencyTuner.render(null, tileEntityFrequencyTuner.crystalFloaty, 0F, 0F, 0.0F, 0.0F, 0.0625F, tileEntityFrequencyTuner.crystal);
        }
        GL11.glPopMatrix();
    }
}
