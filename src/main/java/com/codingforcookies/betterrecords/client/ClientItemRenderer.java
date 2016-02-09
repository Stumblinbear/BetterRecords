//package com.codingforcookies.betterrecords.client;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.client.IItemRenderer;
//
//import org.lwjgl.opengl.GL11;
//
//import com.codingforcookies.betterrecords.BetterRecords;
//import com.codingforcookies.betterrecords.StaticInfo;
//
//public class ClientItemRenderer implements IItemRenderer {
//	public ClientItemRenderer() { }
//
//	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
//		return true;
//	}
//
//	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
//		return true;
//	}
//
//	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
//		GL11.glPushMatrix();
//		{
//			if(item.getItem() == Item.getItemFromBlock(BetterRecords.blockRecordPlayer)) {
//				switch(type) {
//					case EQUIPPED:
//						GL11.glTranslatef(.2F, 1.5F, .8F);
//						GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
//						GL11.glRotatef(10F, 0.0F, 0.0F, 1.0F);
//						GL11.glRotatef(-30F, 1.0F, 0.0F, 0.0F);
//						break;
//					case EQUIPPED_FIRST_PERSON:
//						GL11.glTranslatef(-1F, 2F, 1F);
//						GL11.glRotatef(60F, 0.0F, 1.0F, 0.0F);
//						GL11.glRotatef(30F, 0.0F, 0.0F, 1.0F);
//						GL11.glScalef(.8F, .8F, .8F);
//						break;
//					case ENTITY:
//						GL11.glTranslatef(0F, 1.5F, 0F);
//						break;
//					case INVENTORY:
//						GL11.glTranslatef(0F, 1F, 0F);
//						break;
//					default:
//						GL11.glTranslatef(0.5F, 1.3F, 0.5F);
//						GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
//						break;
//				}
//
//				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
//				Minecraft.getMinecraft().renderEngine.bindTexture(StaticInfo.modelRecordPlayerRes);
//				StaticInfo.modelRecordPlayer.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
//			}else if(item.getItem() == Item.getItemFromBlock(BetterRecords.blockRecordEtcher)) {
//				switch(type) {
//				case EQUIPPED:
//					GL11.glTranslatef(.2F, 1.5F, .8F);
//					GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
//					GL11.glRotatef(10F, 0.0F, 0.0F, 1.0F);
//					GL11.glRotatef(-30F, 1.0F, 0.0F, 0.0F);
//					break;
//				case EQUIPPED_FIRST_PERSON:
//					GL11.glTranslatef(-1F, 2F, 1F);
//					GL11.glRotatef(60F, 0.0F, 1.0F, 0.0F);
//					GL11.glRotatef(30F, 0.0F, 0.0F, 1.0F);
//					GL11.glScalef(.8F, .8F, .8F);
//					break;
//				case ENTITY:
//					GL11.glTranslatef(0F, 1.5F, 0F);
//					break;
//				case INVENTORY:
//					GL11.glTranslatef(0F, 1F, 0F);
//					break;
//				default:
//					GL11.glTranslatef(0.5F, 1.3F, 0.5F);
//					GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
//					break;
//			}
//
//			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
//			Minecraft.getMinecraft().renderEngine.bindTexture(StaticInfo.modelRecordEtcherRes);
//			StaticInfo.modelRecordEtcher.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
//		}else if(item.getItem() == Item.getItemFromBlock(BetterRecords.blockRadio)) {
//			switch(type) {
//				case EQUIPPED:
//					GL11.glTranslatef(.2F, 1.5F, .8F);
//					GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
//					GL11.glRotatef(10F, 0.0F, 0.0F, 1.0F);
//					GL11.glRotatef(-30F, 1.0F, 0.0F, 0.0F);
//					break;
//				case EQUIPPED_FIRST_PERSON:
//					GL11.glTranslatef(-1F, 2F, 1F);
//					GL11.glRotatef(60F, 0.0F, 1.0F, 0.0F);
//					GL11.glRotatef(30F, 0.0F, 0.0F, 1.0F);
//					GL11.glScalef(.8F, .8F, .8F);
//					break;
//				case ENTITY:
//					GL11.glTranslatef(0F, 1.5F, 0F);
//					break;
//				case INVENTORY:
//					GL11.glTranslatef(0F, 1F, 0F);
//					GL11.glRotatef(180F, 0F, 1F, 0F);
//					break;
//				default:
//					GL11.glTranslatef(0.5F, 1.3F, 0.5F);
//					GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
//					break;
//			}
//
//			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
//			Minecraft.getMinecraft().renderEngine.bindTexture(StaticInfo.modelRadioRes);
//			StaticInfo.modelRadio.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, null);
//		}else if(item.getItem() == Item.getItemFromBlock(BetterRecords.blockFrequencyTuner)) {
//			switch(type) {
//				case EQUIPPED:
//					GL11.glTranslatef(.2F, 1.5F, .8F);
//					GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
//					GL11.glRotatef(10F, 0.0F, 0.0F, 1.0F);
//					GL11.glRotatef(-30F, 1.0F, 0.0F, 0.0F);
//					break;
//				case EQUIPPED_FIRST_PERSON:
//					GL11.glTranslatef(-1F, 2F, 1F);
//					GL11.glRotatef(60F, 0.0F, 1.0F, 0.0F);
//					GL11.glRotatef(30F, 0.0F, 0.0F, 1.0F);
//					GL11.glScalef(.8F, .8F, .8F);
//					break;
//				case ENTITY:
//					GL11.glTranslatef(0F, 1.5F, 0F);
//					break;
//				case INVENTORY:
//					GL11.glScalef(1.3F, 1.3F, 1.3F);
//					GL11.glTranslatef(0F, 1.2F, 0F);
//					break;
//				default:
//					GL11.glTranslatef(0.5F, 1.3F, 0.5F);
//					GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
//					break;
//			}
//
//			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
//			Minecraft.getMinecraft().renderEngine.bindTexture(StaticInfo.modelFrequencyTunerRes);
//			StaticInfo.modelFrequencyTuner.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, null);
//		}else if(item.getItem() == Item.getItemFromBlock(BetterRecords.blockStrobeLight)) {
//			switch(type) {
//				case EQUIPPED:
//					GL11.glTranslatef(.2F, 1.5F, .8F);
//					GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
//					GL11.glRotatef(10F, 0.0F, 0.0F, 1.0F);
//					GL11.glRotatef(-30F, 1.0F, 0.0F, 0.0F);
//					break;
//				case EQUIPPED_FIRST_PERSON:
//					GL11.glTranslatef(-1F, 2F, 1F);
//					GL11.glRotatef(60F, 0.0F, 1.0F, 0.0F);
//					GL11.glRotatef(30F, 0.0F, 0.0F, 1.0F);
//					GL11.glScalef(.8F, .8F, .8F);
//					break;
//				case ENTITY:
//					GL11.glTranslatef(0F, 1.5F, 0F);
//					break;
//				case INVENTORY:
//					GL11.glTranslatef(0F, 1F, 0F);
//					break;
//				default:
//					GL11.glTranslatef(0.5F, 1.3F, 0.5F);
//					GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
//					break;
//			}
//
//			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
//			Minecraft.getMinecraft().renderEngine.bindTexture(StaticInfo.modelStrobeLightRes);
//			StaticInfo.modelStrobeLight.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
//		}else if(item.getItem() == Item.getItemFromBlock(BetterRecords.blockLazer)) {
//			switch(type) {
//				case EQUIPPED:
//					GL11.glTranslatef(.2F, 1.5F, .8F);
//					GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
//					GL11.glRotatef(10F, 0.0F, 0.0F, 1.0F);
//					GL11.glRotatef(-30F, 1.0F, 0.0F, 0.0F);
//					break;
//				case EQUIPPED_FIRST_PERSON:
//					GL11.glTranslatef(-1F, 2F, 1F);
//					GL11.glRotatef(60F, 0.0F, 1.0F, 0.0F);
//					GL11.glRotatef(30F, 0.0F, 0.0F, 1.0F);
//					GL11.glScalef(.8F, .8F, .8F);
//					break;
//				case ENTITY:
//					GL11.glTranslatef(0F, 1.5F, 0F);
//					break;
//				case INVENTORY:
//					GL11.glTranslatef(0F, 1F, 0F);
//					break;
//				default:
//					GL11.glTranslatef(0.5F, 1.3F, 0.5F);
//					GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
//					break;
//			}
//
//			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
//			Minecraft.getMinecraft().renderEngine.bindTexture(StaticInfo.modelLazerRes);
//			StaticInfo.modelLazer.render(null, 0.0F, 0.0F, 10.0F, 0.0F, 0.0F, 0.0625F);
//		}else{
//				switch(type) {
//					case EQUIPPED:
//						GL11.glTranslatef(.2F, 1.5F, .8F);
//						GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
//						GL11.glRotatef(10F, 0.0F, 0.0F, 1.0F);
//						GL11.glRotatef(-30F, 1.0F, 0.0F, 0.0F);
//						break;
//					case EQUIPPED_FIRST_PERSON:
//						GL11.glTranslatef(-1F, 2F, 1F);
//						GL11.glRotatef(60F, 0.0F, 1.0F, 0.0F);
//						GL11.glRotatef(30F, 0.0F, 0.0F, 1.0F);
//						GL11.glScalef(.8F, .8F, .8F);
//						break;
//					case ENTITY:
//						GL11.glTranslatef(0F, 1.5F, 0F);
//						break;
//					case INVENTORY:
//						GL11.glTranslatef(0F, 1F, 0F);
//						GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
//						break;
//					default:
//						GL11.glTranslatef(0.5F, 1.3F, 0.5F);
//						GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
//						break;
//				}
//
//				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
//
//				if(item.getItem() == Item.getItemFromBlock(BetterRecords.blockSMSpeaker)) {
//					Minecraft.getMinecraft().renderEngine.bindTexture(StaticInfo.modelSMSpeakerRes);
//					StaticInfo.modelSMSpeaker.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
//				}else if(item.getItem() == Item.getItemFromBlock(BetterRecords.blockMDSpeaker)) {
//					Minecraft.getMinecraft().renderEngine.bindTexture(StaticInfo.modelMDSpeakerRes);
//					StaticInfo.modelMDSpeaker.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
//				}else if(item.getItem() == Item.getItemFromBlock(BetterRecords.blockLGSpeaker)) {
//					Minecraft.getMinecraft().renderEngine.bindTexture(StaticInfo.modelLGSpeakerRes);
//					StaticInfo.modelLGSpeaker.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
//				}
//			}
//		}
//		GL11.glPopMatrix();
//	}
//}