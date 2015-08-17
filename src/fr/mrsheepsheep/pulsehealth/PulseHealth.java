package fr.mrsheepsheep.pulsehealth;

import java.io.IOException;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.mrsheepsheep.pulsehealth.Metrics;

public class PulseHealth extends JavaPlugin {

	PulseRunnables pr;
	PlayerListener pl;
	
	Sound sound1 = Sound.NOTE_BASS_DRUM;
	Sound sound2 = Sound.NOTE_BASS_DRUM;
	
	float volume1 = 0.1f;
	float volume2 = 0.1f;
	
	float pitch1 = 0f;
	float pitch2 = 0f;
	
	int beat = 12;
	int fastbeat = 6;
	
	boolean playOnHit = false;
	float duration = 5f;
	
	public void onEnable(){
		
	    try {
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	    } catch (IOException e) {
	        getLogger().warning("Metrics cannot be started");
	        e.printStackTrace();
	    }
	    
		loadConfig();
		pr = new PulseRunnables(this);
		pl = new PlayerListener(this);
	}
	
	public void loadConfig(){
		FileConfiguration c = getConfig();
		c.options().copyDefaults(true);
		saveConfig();
		
		sound1 = Sound.valueOf(c.getString("sounds.first.name"));
		sound2 = Sound.valueOf(c.getString("sounds.second.name"));
		
		volume1 = Float.valueOf(c.getString("sounds.first.volume"));
		volume2 = Float.valueOf(c.getString("sounds.second.volume"));
		
		pitch1 = Float.valueOf(c.getString("sounds.first.pitch"));
		pitch2 = Float.valueOf(c.getString("sounds.second.pitch"));
		
		beat = c.getInt("health.beat");
		fastbeat = c.getInt("health.fastbeat");
		
		playOnHit = c.getBoolean("play-on-hit.enabled");
		duration = Float.valueOf(c.getString("play-on-hit.duration"));
	}
}
