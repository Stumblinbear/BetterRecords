package com.codingforcookies.betterrecords.api.wire;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;

import java.util.ArrayList;

public interface IRecordWire {
    ArrayList<RecordConnection> getConnections();

    String getName();
    float getSongRadiusIncrease();
}
