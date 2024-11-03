package fr.klemad.pluginuhc.listener;

import fr.klemad.pluginuhc.Main;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class CraftListener implements Listener {
  Main main;
  
  public CraftListener(Main main) {
    this.main = main;
  }
  
  @EventHandler
  public void onCraft(CraftItemEvent event) {
    if (this.main.rapide) {
      ItemStack resultat = event.getRecipe().getResult();
      if (resultat.getType() == Material.WOOD_PICKAXE) {
        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta meta = pickaxe.getItemMeta();
        meta.setDisplayName("Pioche en PierrePierre");
        pickaxe.setItemMeta(meta);
        event.getInventory().setResult(pickaxe);
      } else if (resultat.getType() == Material.WOOD_AXE) {
        ItemStack axe = new ItemStack(Material.STONE_AXE);
        ItemMeta meta = axe.getItemMeta();
        meta.setDisplayName("Hache en PierrePierre");
        axe.setItemMeta(meta);
        event.getInventory().setResult(axe);
      } else if (resultat.getType() == Material.WOOD_SWORD) {
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName("Épée en PierrePierre");
        sword.setItemMeta(meta);
        event.getInventory().setResult(sword);
      } 
    } 
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent event) {
    ItemStack item = event.getCurrentItem();
    if (item.getType() == Material.POTION) {
      Potion potion = Potion.fromItemStack(item);
      PotionMeta meta = (PotionMeta)item.getItemMeta();
      if (potion.getType() == PotionType.STRENGTH)
        if (Potion.fromItemStack(item).hasExtendedDuration()) {
          meta.setDisplayName("Potion de force");
          List<String> loresList = new ArrayList<>();
          loresList.add(ChatColor.GRAY + "Force (8:00)");
          loresList.add("");
          loresList.add(ChatColor.DARK_PURPLE + "Effet :");
          loresList.add(ChatColor.BLUE + "+3 coeurs flat");
          meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
          meta.setLore(loresList);
          item.setItemMeta((ItemMeta)meta);
        } else {
          meta.setDisplayName("Potion de force");
          List<String> loresList = new ArrayList<>();
          loresList.add(ChatColor.GRAY + "Force (3:00)");
          loresList.add("");
          loresList.add(ChatColor.DARK_PURPLE + "Effet :");
          loresList.add(ChatColor.BLUE + "+3 coeurs flat");
          meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
          meta.setLore(loresList);
          item.setItemMeta((ItemMeta)meta);
        }  
    } 
  }
}
