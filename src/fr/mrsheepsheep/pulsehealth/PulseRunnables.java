package fr.mrsheepsheep.pulsehealth;

import java.util.HashMap;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

public class PulseRunnables {

	PulseHealth plugin;

	public PulseRunnables(final PulseHealth plugin){
		this.plugin = plugin;
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, pulser1, 0L, 2L);
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, pulser2, 5L, 2L);
	}

	private Runnable pulser1 = new Runnable(){
		private int beat = 0;
		@Override
		public void run() {
			pulse(beat, 1);
			beat++;
			if (beat > 9) beat = 0;
		}
	};

	private Runnable pulser2 = new Runnable(){
		private int beat = 0;
		@Override
		public void run() {
			pulse(beat, 2);
			beat++;
			if (beat > 9) beat = 0;
		}
	};

	private void sendMap(Player p, int id, String sound, float volume, float pitch, HashMap<String, Float> map, float f){
		if (plugin.customsounds)
			p.playSound(p.getLocation(), sound, volume, pitch);
		else
			p.playSound(p.getLocation(), Sound.valueOf(sound), volume, pitch);

		if (id == 2){
			map.put(p.getName(), map.get(p.getName()) - f);
			if (map.get(p.getName()) == 0){
				map.remove(p.getName());
			}	
		}
		plugin.pl.hitPlayers = map;
	}

	private void pulse(int beat, int id){
		Sound sound = plugin.sound1;
		float volume = plugin.volume1;
		float pitch = plugin.pitch1;

		if (id == 2){
			sound = plugin.sound2;
			volume = plugin.volume2;
			pitch = plugin.pitch2;
		}
		HashMap<String, Float> map = plugin.pl.hitPlayers;
		for (Player p : plugin.getServer().getOnlinePlayers()){
			if (plugin.playOnHit){
				if (map.containsKey(p.getName())){
					double health = ((Damageable) p).getHealth();
					if (health > plugin.beat){
						plugin.pl.hitPlayers.remove(p.getName());
					}
					else if (health <= plugin.beat && health > plugin.fastbeat && beat == 0){
						sendMap(p, id, sound.toString(), volume, pitch, map, 1);
					}
					else if (health <= plugin.fastbeat && (beat == 0 || beat == 5)){
						sendMap(p, id, sound.toString(), volume, pitch, map, 0.5f);
					}
				}
			}
			else
			{
				double health = ((Damageable) p).getHealth();
				if (health <= plugin.beat && health > plugin.fastbeat && beat == 0){
					p.playSound(p.getLocation(), sound, volume, pitch);
				}
				else if (health <= plugin.fastbeat){
					if (beat == 0 || beat == 5){
						p.playSound(p.getLocation(), sound, volume, pitch);
					}
				}
			}
		}
	}
}
