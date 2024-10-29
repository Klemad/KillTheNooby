package fr.klemad.pluginuhc.listener;

import fr.klemad.pluginuhc.Main;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DropListener implements Listener {
  Main main;
  
  public DropListener(Main main) {
    this.main = main;
  }
  
  @EventHandler
  public void onDrop(EntityDeathEvent event) {
    EntityType eT = event.getEntityType();
    if (eT == EntityType.BAT) {
      Random rand = new Random();
      int randomNumber = rand.nextInt(20);
      switch (randomNumber) {
        case 1:
          event.getEntity().getWorld().dropItemNaturally(event.getEntity().getKiller().getLocation().add(0.5D, 0.5D, 0.5D), new ItemStack(Material.GOLDEN_APPLE, 1));
          break;
      } 
    } 
    if (this.main.rapide) {
      List<ItemStack> drops = event.getDrops();
      if (eT == EntityType.CHICKEN) {
        drops.clear();
        drops.add(drops.size(), new ItemStack(Material.COOKED_CHICKEN));
        drops.add(drops.size(), new ItemStack(Material.COOKED_CHICKEN));
        drops.add(drops.size(), new ItemStack(Material.FEATHER));
        drops.add(drops.size(), new ItemStack(Material.FEATHER));
      } else if (eT == EntityType.PIG) {
        drops.clear();
        drops.add(drops.size(), new ItemStack(Material.GRILLED_PORK));
        drops.add(drops.size(), new ItemStack(Material.GRILLED_PORK));
        drops.add(new ItemStack(Material.LEATHER));
      } else if (eT == EntityType.COW) {
        drops.clear();
        drops.add(new ItemStack(Material.COOKED_BEEF));
        drops.add(new ItemStack(Material.COOKED_BEEF));
        drops.add(new ItemStack(Material.LEATHER));
      } else if (eT == EntityType.SHEEP) {
        drops.clear();
        drops.add(new ItemStack(Material.COOKED_MUTTON));
        drops.add(new ItemStack(Material.COOKED_MUTTON));
        drops.add(new ItemStack(Material.LEATHER));
      } else if (eT == EntityType.RABBIT) {
        drops.clear();
        drops.add(new ItemStack(Material.COOKED_RABBIT));
        drops.add(new ItemStack(Material.COOKED_RABBIT));
        drops.add(new ItemStack(Material.LEATHER));
      } else if (eT == EntityType.ZOMBIE) {
        drops.clear();
        drops.add(new ItemStack(Material.COOKED_BEEF));
      } 
    } 
  }
}
