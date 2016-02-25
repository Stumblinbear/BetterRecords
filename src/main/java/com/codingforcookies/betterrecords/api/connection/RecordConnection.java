package com.codingforcookies.betterrecords.api.connection;

public class RecordConnection {
    public int x1, y1, z1, x2, y2, z2;
    public boolean fromHome = false;

    public RecordConnection(int x, int y, int z, boolean fromHome) {
        if(fromHome)
            setConnection1(x, y, z);
        else
            setConnection2(x, y, z);
        this.fromHome = fromHome;
    }

    public RecordConnection(String string) {
        String[] str = string.split(",");
        x1 = Integer.parseInt(str[0]);
        y1 = Integer.parseInt(str[1]);
        z1 = Integer.parseInt(str[2]);
        x2 = Integer.parseInt(str[3]);
        y2 = Integer.parseInt(str[4]);
        z2 = Integer.parseInt(str[5]);
    }

    public String toString() {
        return x1 + "," + y1 + "," + z1 + "," + x2 + "," + y2 + "," + z2;
    }

    public RecordConnection setConnection1(int x, int y, int z) {
        x1 = x;
        y1 = y;
        z1 = z;
        return this;
    }

    public RecordConnection setConnection2(int x, int y, int z) {
        x2 = x;
        y2 = y;
        z2 = z;
        return this;
    }

    public boolean sameInitial(int x, int y, int z) {
        return x1 == x && y1 == y && z1 == z;
    }

    public boolean same(int x1, int y1, int z1, int x2, int y2, int z2) {
        return this.x1 == x1 && this.y1 == y1 && this.z1 == z1 && this.x2 == x2 && this.y2 == y2 && this.z2 == z2;
    }

    public boolean same(RecordConnection rec) {
        return x1 == rec.x1 && y1 == rec.y1 && z1 == rec.z1 && x2 == rec.x2 && y2 == rec.y2 && z2 == rec.z2;
    }

    public boolean sameBetween(RecordConnection rec) {
        return x1 == rec.x2 && y1 == rec.y2 && z1 == rec.z2 && x2 == rec.x1 && y2 == rec.y1 && z2 == rec.z1;
    }
}
