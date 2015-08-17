package fr.mrsheepsheep.pulsehealth;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerListener implements Listener {

	PulseHealth plugin;
	HashMap<String, Float> hitPlayers;

	public PlayerListener(PulseHealth plugin){
		this.plugin = plugin;
		hitPlayers = new HashMap<String, Float>();

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerHit(EntityDamageEvent e){
		if (plugin.playOnHit){
			if (e.getEntity() instanceof Player){
				Player p = (Player) e.getEntity();
				hitPlayers.put(p.getName(), plugin.duration);
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		if (plugin.playOnHit){
			if (hitPlayers.containsKey(e.getEntity().getName())){
				hitPlayers.remove(e.getEntity().getName());
			}
		}
	}
}
