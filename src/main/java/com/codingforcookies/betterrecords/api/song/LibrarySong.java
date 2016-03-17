package com.codingforcookies.betterrecords.api.song;

public class LibrarySong {
    public String local = "";
    public String author = "";
    public String name = "";
    public String url = "";
    public int color = 0;

    public LibrarySong(String local, String author, String name, String url, int color) {
        this.local = local;
        this.author = author;
        this.name = name;
        this.url = url;
        this.color = color;
    }
}
