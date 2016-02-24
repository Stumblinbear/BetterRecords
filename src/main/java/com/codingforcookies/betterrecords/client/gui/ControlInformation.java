package com.codingforcookies.betterrecords.client.gui;

public abstract class ControlInformation {
	public abstract void onActivated(int mousebtn, int ctrlNum);
	public abstract void onHover(int mouseX, int mouseY);
	public abstract String getText();
	public abstract float[] getColor();
}