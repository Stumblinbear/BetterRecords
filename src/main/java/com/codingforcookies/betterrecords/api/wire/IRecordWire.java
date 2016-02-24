package com.codingforcookies.betterrecords.api.wire;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;

import java.util.ArrayList;

public interface IRecordWire {
	public ArrayList<RecordConnection> getConnections();
	
	public String getName();
	public float getSongRadiusIncrease();
}