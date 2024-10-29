package fr.klemad.pluginuhc.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

public class RegenListener implements Listener {
  @EventHandler
  public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
    if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN)
      event.setCancelled(true); 
  }
  
  @EventHandler
  public void onItemConsume(PlayerItemConsumeEvent e) {
    ItemStack consumed = e.getItem();
    if (consumed.getType().equals(Material.POTION)) {
      Potion potion = Potion.fromItemStack(consumed);
      if (potion.getLevel() == 2) {
        e.setCancelled(true);
        Bukkit.broadcastMessage(String.valueOf(e.getPlayer().getName()) + " a essayde faire des potions de niveau 2. Il perd donc sa potion et n'aura pas de compensation.");
      } 
    } 
  }
  
  @EventHandler
  public void onSplash(PotionSplashEvent e) {
    ItemStack consumed = e.getPotion().getItem();
    if (consumed.getType().equals(Material.POTION)) {
      Potion potion = Potion.fromItemStack(consumed);
      if (potion.getLevel() == 2) {
        e.setCancelled(true);
        Bukkit.broadcastMessage("Espde sale merde essayer les potions splash de niveau 2.");
      } 
    } 
  }
}
