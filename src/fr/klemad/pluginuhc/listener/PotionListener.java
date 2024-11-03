package fr.klemad.pluginuhc.listener;

import fr.klemad.pluginuhc.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionListener implements Listener {
	  public Main main;
	  
	  public PotionListener(Main main) {
	    this.main = main;
	  }
	  
	  //Modification de l'effet de la potion de force
	  @EventHandler
	  public void onPlayerDamage(EntityDamageByEntityEvent event) {
	      if (event.getDamager() instanceof Player) {
	        Player player = (Player)event.getDamager();
	        if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
	          for (PotionEffect effect : player.getActivePotionEffects()) {
	            if (effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
	              double strengthDamage = event.getDamage(EntityDamageEvent.DamageModifier.BASE);
	              double baseDamage = strengthDamage/ (2.3D) ;
	              double newDamage =  baseDamage + 6D ;
	              double damagePercent = newDamage / strengthDamage;
	              try {
	                event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, event.getDamage(EntityDamageEvent.DamageModifier.ARMOR) * damagePercent);
	              } catch (Exception exception) {}
	              try {
	                event.setDamage(EntityDamageEvent.DamageModifier.MAGIC, event.getDamage(EntityDamageEvent.DamageModifier.MAGIC) * damagePercent);
	              } catch (Exception exception) {}
	              try {
	                event.setDamage(EntityDamageEvent.DamageModifier.RESISTANCE, event.getDamage(EntityDamageEvent.DamageModifier.RESISTANCE) * damagePercent);
	              } catch (Exception exception) {}
	              try {
	                event.setDamage(EntityDamageEvent.DamageModifier.BLOCKING, event.getDamage(EntityDamageEvent.DamageModifier.BLOCKING) * damagePercent);
	              } catch (Exception exception) {}
	              event.setDamage(EntityDamageEvent.DamageModifier.BASE, newDamage);
	              break;
	            } 
	          }  
	      }  
	  }
}
