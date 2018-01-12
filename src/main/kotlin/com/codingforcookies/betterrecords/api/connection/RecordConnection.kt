package com.codingforcookies.betterrecords.api.connection

class RecordConnection {
    var x1: Int = 0
    var y1: Int = 0
    var z1: Int = 0
    var x2: Int = 0
    var y2: Int = 0
    var z2: Int = 0
    var fromHome = false

    constructor(x: Int, y: Int, z: Int, fromHome: Boolean) {
        if (fromHome)
            setConnection1(x, y, z)
        else
            setConnection2(x, y, z)
        this.fromHome = fromHome
    }

    constructor(string: String) {
        val str = string.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        x1 = Integer.parseInt(str[0])
        y1 = Integer.parseInt(str[1])
        z1 = Integer.parseInt(str[2])
        x2 = Integer.parseInt(str[3])
        y2 = Integer.parseInt(str[4])
        z2 = Integer.parseInt(str[5])
    }

    override fun toString(): String {
        return x1.toString() + "," + y1 + "," + z1 + "," + x2 + "," + y2 + "," + z2
    }

    fun setConnection1(x: Int, y: Int, z: Int): RecordConnection {
        x1 = x
        y1 = y
        z1 = z
        return this
    }

    fun setConnection2(x: Int, y: Int, z: Int): RecordConnection {
        x2 = x
        y2 = y
        z2 = z
        return this
    }

    fun sameInitial(x: Int, y: Int, z: Int): Boolean {
        return x1 == x && y1 == y && z1 == z
    }

    fun same(x1: Int, y1: Int, z1: Int, x2: Int, y2: Int, z2: Int): Boolean {
        return this.x1 == x1 && this.y1 == y1 && this.z1 == z1 && this.x2 == x2 && this.y2 == y2 && this.z2 == z2
    }

    fun same(rec: RecordConnection): Boolean {
        return x1 == rec.x1 && y1 == rec.y1 && z1 == rec.z1 && x2 == rec.x2 && y2 == rec.y2 && z2 == rec.z2
    }

    fun sameBetween(rec: RecordConnection): Boolean {
        return x1 == rec.x2 && y1 == rec.y2 && z1 == rec.z2 && x2 == rec.x1 && y2 == rec.y1 && z2 == rec.z1
    }
}
