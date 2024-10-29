package fr.klemad.pluginuhc.listener;

import fr.klemad.pluginuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CompassListener implements Listener {
  Main main;
  
  public CompassListener(Main main) {
    this.main = main;
  }
  
  @EventHandler
  public void onInteract(PlayerInteractEvent e) {
    if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.hasItem()) {
      ItemStack boussole = e.getItem();
      if (boussole.getType() == Material.COMPASS) {
        e.setCancelled(true);
        Player p = e.getPlayer();
        int distance = 1000000000;
        int d = 0;
        int x = 0;
        int z = 0;
        int pX = p.getLocation().getBlockX();
        int pZ = p.getLocation().getBlockZ();
        Location proche = p.getLocation();
        Player cible = p;
        for (String joueur : this.main.participants) {
          Player temp = Bukkit.getServer().getPlayer(joueur);
          if (!joueur.equalsIgnoreCase(p.getName()) && !this.main.sbHandler.memeEquipe(p, temp)) {
            Location loc = temp.getLocation();
            x = loc.getBlockX();
            z = loc.getBlockZ();
            d = (pX - x) * (pX - x) + (pZ - z) * (pZ - z);
            if (d < distance) {
              Bukkit.getConsoleSender().sendMessage(String.valueOf(joueur) + " x:" + x + ", z:" + z + ", d:" + d);
              distance = d;
              proche = loc;
              cible = temp;
            } 
          } 
        } 
        p.setCompassTarget(proche);
        if (p == cible) {
          p.sendMessage("t'es tout seul couillon");
        } else {
          cible.sendMessage("Tu es traqu!");
        } 
      } 
    } 
  }
}
