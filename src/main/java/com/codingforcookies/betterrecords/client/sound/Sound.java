package com.codingforcookies.betterrecords.client.sound;

import javax.sound.sampled.FloatControl;

public class Sound { 
    public String name, url, local;
    public int x, y, z, dimension;
    public float playRadius = 40F;
    public FloatControl volume = null;

    public Sound() { }

    public Sound(int x, int y, int z, int dimension, float playRadius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
        this.playRadius = playRadius;
    }

    public Sound setInfo(String name, String url, String local) {
        this.name = name;
        this.url = url;
        this.local = local;
        return this;
    }

    public String toString() {
        return name + "\2476" + url + "\2476" + local;
    }

    public static Sound fromString(String str) {
        String[] strr = str.split("\2476");
        Sound snd = new Sound();
        snd.name = strr[0];
        snd.url = strr[1];
        snd.local = strr[2];
        return snd;
    }
}
