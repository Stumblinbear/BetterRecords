package com.codingforcookies.betterrecords.src.betterenums;

import java.util.ArrayList;

public interface IRecordWire {
	public ArrayList<RecordConnection> getConnections();
	
	public String getName();
	public float getSongRadiusIncrease();
}