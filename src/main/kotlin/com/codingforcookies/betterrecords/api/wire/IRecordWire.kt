package com.codingforcookies.betterrecords.api.wire

import com.codingforcookies.betterrecords.api.connection.RecordConnection

interface IRecordWire {

    val connections: MutableList<RecordConnection>

    fun getName(): String

    val songRadiusIncrease: Float
}
