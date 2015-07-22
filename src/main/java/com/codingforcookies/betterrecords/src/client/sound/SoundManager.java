package com.codingforcookies.betterrecords.src.client.sound;

import java.util.ArrayList;
import java.util.List;

public class SoundManager {
	public boolean repeat = false;
	public int current = -1;
	public List<Sound> songs = new ArrayList<Sound>();

	public SoundManager(boolean repeat) {
		this.repeat = repeat;
	}
	
	public SoundManager(Sound sound, boolean repeat) {
		songs.add(sound);
		this.repeat = repeat;
	}

	public Sound getCurrentSong() {
		return current == -1 ? null : current < songs.size() ? songs.get(current) : null;
	}

	public void addSong(Sound sound) {
		songs.add(sound);
	}

	public Sound nextSong() {
		current++;
		if(current >= songs.size() && repeat)
			current = 0;
		return getCurrentSong();
	}
	
}