package com.codingforcookies.betterrecords.api.record;

public interface IRecordAmplitude {
	public void setTreble(float amplitude);
	public void setTreble(float amplitude, float r, float g, float b);
	public float getTreble();
	
	public void setBass(float amplitude);
	public void setBass(float amplitude, float r, float g, float b);
	public float getBass();
}