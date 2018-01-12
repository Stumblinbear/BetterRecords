package com.codingforcookies.betterrecords.client.sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoundManager{

    public boolean repeat;
    public boolean shuffle;
    public int current = -1;
    public List<Sound> songs = new ArrayList<>();
    public List<Integer> completedSongs = new ArrayList<>();
    private final Random rand;

    public SoundManager(boolean repeat, boolean shuffle){
        this.repeat = repeat;
        this.shuffle = shuffle;
        this.rand = new Random();
    }

    public SoundManager(Sound sound, boolean repeat, boolean shuffle){
        songs.add(sound);
        this.repeat = repeat;
        this.shuffle = shuffle;
        this.rand = new Random();
    }

    public Sound getCurrentSong(){
        return current == -1 ? null : current < songs.size() ? songs.get(current) : null;
    }

    public void addSong(Sound sound){
        songs.add(sound);
    }

    public Sound nextSong(){
        if(shuffle && current != -1) completedSongs.add(current);
        if(shuffle && completedSongs.size() > 0 && !(completedSongs.size() >= songs.size())) while(completedSongs.contains(current))
            current = rand.nextInt(songs.size());
        else current++;
        if(current >= songs.size() && repeat){
            completedSongs.clear();
            for(Sound s : songs)
                s.volume = null;
            if(shuffle) current = rand.nextInt(songs.size());
            else current = 0;
        }
        return getCurrentSong();
    }
}
