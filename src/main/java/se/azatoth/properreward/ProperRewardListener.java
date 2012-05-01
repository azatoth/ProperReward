package se.azatoth.properreward;

import java.util.Random;
import java.util.Map.Entry;
import java.util.WeakHashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

public class ProperRewardListener implements Listener {
	WeakHashMap<LivingEntity, WeakHashMap<Player, Integer>> playerDamages;
	WeakHashMap<LivingEntity, Player> lastDamager;

	public ProperRewardListener() {
		playerDamages = new WeakHashMap<LivingEntity, WeakHashMap<Player, Integer>>();
		lastDamager = new WeakHashMap<LivingEntity, Player>();
	}

	@EventHandler
	public void onEntitiyDamageByEntity(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity entity = event.getEntity();
		if (damager instanceof Player && entity instanceof LivingEntity
				&& !(entity instanceof Player)) {
			WeakHashMap<Player, Integer> players = playerDamages.get(entity);
			if (players == null) {
				players = new WeakHashMap<Player, Integer>();
			}

			Integer damage = players.get(damager);
			if (damage == null) {
				damage = 0;
			}

			damage += event.getDamage();
			players.put((Player) damager, damage);
			lastDamager.put((LivingEntity) entity, (Player) damager);

		}

	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		LivingEntity player = event.getEntity();
		if (player instanceof Player
				&& player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			WeakHashMap<Player, Integer> current_damages = playerDamages
					.get(player);
			int max = 0;
			Player lastPlayer = lastDamager.get(player);
			if (lastPlayer != null) {
				for (Entry<Player, Integer> cur : current_damages.entrySet()) {
					max += cur.getValue();
				}
				if (new Random().nextInt(max) >= current_damages.get(player)) {
					event.setDroppedExp(0);
					event.getDrops().clear();
				}
			}
		} else {
			event.setDroppedExp(0);
			event.getDrops().clear();
		}

	}

}
