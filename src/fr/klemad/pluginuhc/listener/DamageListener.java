package fr.klemad.pluginuhc.listener;

import fr.klemad.pluginuhc.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
  public Main main;
  
  public DamageListener(Main main) {
    this.main = main;
  }
  
  @EventHandler
  public void onFallDamage(EntityDamageEvent event) {
    if (this.main.chute && event.getEntity() instanceof Player)
      event.setCancelled(true); 
  }
  
  @EventHandler
  public void onPVPDamage(EntityDamageByEntityEvent event) {
    if (!this.main.pvp && event.getEntity() instanceof Player && event.getDamager() instanceof Player)
      event.setCancelled(true); 
  }
  
  /*
  @EventHandler
  public void strNerf(EntityDamageByEntityEvent e) {
    if (e.getDamager() instanceof Player) {
      Player p = (Player)e.getDamager();
      for (PotionEffect effect : p.getActivePotionEffects()) {
        if (effect.getType().equals(PotionEffectType.INCREASE_DAMAGE) && 
          e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE)
          e.setDamage(e.getDamage() * 0.44D + 5.0D); 
      } 
    } 
  } */
  
}
