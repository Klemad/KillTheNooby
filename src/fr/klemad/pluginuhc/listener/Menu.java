package fr.klemad.pluginuhc.listener;

import fr.klemad.pluginuhc.Main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu implements Listener {
  Main main;
  
  public Menu(Main main) {
    this.main = main;
  }
  
  private ItemStack getItem(String name, Material material, String nom) {
    ItemStack it = new ItemStack(material, 1);
    ItemMeta m = it.getItemMeta();
    m.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
    m.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES });
    m.setDisplayName(name);
    it.setItemMeta(m);
    return it;
  }
  
  @EventHandler
  public void onInteract(PlayerInteractEvent e) {
    if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.hasItem()) {
      ItemStack itm = e.getItem();
      if (itm.getType() == Material.WOOD_PICKAXE || itm.getType() == Material.STONE_PICKAXE || itm.getType() == Material.IRON_PICKAXE || itm.getType() == Material.GOLD_PICKAXE || itm.getType() == Material.DIAMOND_PICKAXE) {
        Player p = e.getPlayer();
        p.sendMessage("Hauteur : " + p.getLocation().getBlockY());
      } 
    } else if (e.getAction() == Action.RIGHT_CLICK_AIR && e.hasItem()) {
      ItemStack itm = e.getItem();
      if (itm.getType() == Material.WOOD_PICKAXE || itm.getType() == Material.STONE_PICKAXE || itm.getType() == Material.IRON_PICKAXE || itm.getType() == Material.GOLD_PICKAXE || itm.getType() == Material.DIAMOND_PICKAXE) {
        Player p = e.getPlayer();
        p.sendMessage("Hauteur : " + p.getLocation().getBlockY());
      } 
      if (itm.getType() == Material.NETHER_STAR) {
        Inventory inv = Bukkit.createInventory(null, 27, "Menu");
        inv.setItem(1, getItem("Jouer en team", Material.DIAMOND_CHESTPLATE, "Jouer en team"));
        inv.setItem(11, getItem("Participer l'UHC", Material.DIAMOND_SWORD, "Participer l'UHC"));
        inv.setItem(13, getItem("Rejoindre une team", Material.DIAMOND_PICKAXE, "Rejoindre une team"));
        inv.setItem(15, getItem("Mode rapide", Material.GOLD_INGOT, "Mode rapide"));
        inv.setItem(26, getItem("Lancer l'UHC", Material.BEDROCK, "Lancer l'UHC"));
        e.getPlayer().openInventory(inv);
      } 
    } 
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent e) {
    Inventory inv = e.getInventory();
    Player p = (Player)e.getWhoClicked();
    String joueur = p.getName();
    ItemStack current = e.getCurrentItem();
    if (current == null || !current.hasItemMeta() || !current.getItemMeta().hasDisplayName())
      return; 
    if (inv.getName().equals("Menu")) {
      if (current.getItemMeta().getDisplayName().contentEquals("Participer l'UHC") && 
        !this.main.participants.contains(p.getName())) {
        this.main.participants.add(p.getName());
        this.main.nbParticipants++;
        Bukkit.broadcastMessage("Il y a " + this.main.nbParticipants + " participants.");
        this.main.sbHandler.updateJoueur();
        Bukkit.broadcastMessage(String.valueOf(p.getName()) + " participe l'UHC !");
        inv.setItem(11, current);
      } 
      Inventory team = Bukkit.createInventory(null, 27, "Team");
      ItemStack red = new ItemStack(Material.STAINED_GLASS, 1, (short)14);
      ItemMeta redMeta = red.getItemMeta();
      redMeta.setDisplayName("Rejoins les rouges, camarade !");
      red.setItemMeta(redMeta);
      ItemStack blue = new ItemStack(Material.STAINED_GLASS, 1, (short)11);
      ItemMeta blueMeta = blue.getItemMeta();
      blueMeta.setDisplayName("Enfile ton bleu !");
      blue.setItemMeta(blueMeta);
      ItemStack pink = new ItemStack(Material.STAINED_GLASS, 1, (short)6);
      ItemMeta pinkMeta = pink.getItemMeta();
      pinkMeta.setDisplayName("Tu vas vraiment choisir l'rose??");
      pink.setItemMeta(pinkMeta);
      ItemStack green = new ItemStack(Material.STAINED_GLASS, 1, (short)13);
      ItemMeta greenMeta = green.getItemMeta();
      greenMeta.setDisplayName("Tu veux du chichon?");
      green.setItemMeta(greenMeta);
      ItemStack yellow = new ItemStack(Material.STAINED_GLASS, 1, (short)4);
      ItemMeta yellowMeta = yellow.getItemMeta();
      yellowMeta.setDisplayName("JAUUUUUNNNNEEEEE");
      yellow.setItemMeta(yellowMeta);
      team.setItem(12, red);
      team.setItem(13, blue);
      team.setItem(14, pink);
      team.setItem(4, green);
      team.setItem(22, yellow);
      ItemStack poubelle = new ItemStack(Material.STAINED_GLASS);
      ItemMeta poubelleMeta = poubelle.getItemMeta();
      poubelleMeta.setDisplayName("Quitter la team");
      poubelleMeta.setLore(Arrays.asList(new String[] { "Quitter la team" }));
      poubelle.setItemMeta(poubelleMeta);
      team.setItem(26, poubelle);
      if (current.getItemMeta().getDisplayName().equals("Rejoindre une team")) {
        e.setCancelled(true);
        p.openInventory(team);
      } 
      if (current.getItemMeta().getDisplayName().equals("Jouer en team" )) {
        e.setCancelled(true);
        if (this.main.equipe) {
          this.main.equipe = false;
          Bukkit.broadcastMessage("Tout seul on va plus vite !");
        } else {
          this.main.equipe = true;
          Bukkit.broadcastMessage("Ensemble on va plus loin !");
        } 
      } 
      e.setCancelled(true);
    } else if (current.getType() == Material.STAINED_GLASS) {
      e.setCancelled(true);
      if (!this.main.sbHandler.getTeam(p).equals("none"))
        this.main.sbHandler.getTeamTeam(p).removeEntry(joueur); 
      if (current.getItemMeta().getDisplayName().equals("Rejoins les rouges, camarade !"))
        this.main.sbHandler.rouge.addEntry(joueur);
      if (current.getItemMeta().getDisplayName().equals("Enfile ton bleu !"))
        this.main.sbHandler.bleu.addEntry(joueur);
      if (current.getItemMeta().getDisplayName().equals("Tu vas vraiment choisir l'rose??"))
        this.main.sbHandler.rose.addEntry(joueur);
      if (current.getItemMeta().getDisplayName().equals("Tu veux du chichon?"))
        this.main.sbHandler.vert.addEntry(joueur); 
      if (current.getItemMeta().getDisplayName().equals("JAUUUUUNNNNEEEEE"))
        this.main.sbHandler.jaune.addEntry(joueur);
      p.closeInventory();
    } 
    if (current.getType() == Material.GOLD_INGOT && 
      current.getItemMeta().getDisplayName().equals("Mode rapide"))
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "rapide"); 
    if (current.getItemMeta().getDisplayName().equals("Lancer l'UHC")) {
      e.setCancelled(true);
      p.closeInventory();
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "gamemode 0 @a");
      ArrayList<Location> spawnList = new ArrayList<>();
      spawnList.add(new Location(Bukkit.getWorld("world"), 500.0D, 150.0D, 500.0D));
      spawnList.add(new Location(Bukkit.getWorld("world"), -500.0D, 150.0D, -500.0D));
      spawnList.add(new Location(Bukkit.getWorld("world"), -500.0D, 150.0D, 500.0D));
      spawnList.add(new Location(Bukkit.getWorld("world"), 500.0D, 150.0D, -500.0D));
      spawnList.add(new Location(Bukkit.getWorld("world"), 0.0D, 150.0D, 500.0D));
      spawnList.add(new Location(Bukkit.getWorld("world"), 0.0D, 150.0D, -500.0D));
      spawnList.add(new Location(Bukkit.getWorld("world"), -500.0D, 150.0D, 0.0D));
      spawnList.add(new Location(Bukkit.getWorld("world"), 500.0D, 150.0D, 0.0D));
      Collections.shuffle(spawnList);
      ArrayList<Location> spawnRouge = new ArrayList<>();
      ArrayList<Location> spawnBleu = new ArrayList<>();
      ArrayList<Location> spawnRose = new ArrayList<>();
      ArrayList<Location> spawnVert = new ArrayList<>();
      ArrayList<Location> spawnJaune = new ArrayList<>();
      for (String x : this.main.participants) {
        Player player = Bukkit.getServer().getPlayer(x);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setFoodLevel(20);
        ItemStack itm = new ItemStack(Material.COOKED_BEEF, 5);
        player.getInventory().setItem(8, itm);
        player.updateInventory();
        if (this.main.sbHandler.getTeam(player).equals("rouge")) {
          if (spawnRouge.isEmpty()) {
            spawnRouge.add(((Location)spawnList.get(0)).clone());
            spawnList.remove(0);
          } 
          player.teleport(spawnRouge.get(0));
          continue;
        } 
        if (this.main.sbHandler.getTeam(player).equals("bleu")) {
          if (spawnBleu.isEmpty()) {
            spawnBleu.add(((Location)spawnList.get(0)).clone());
            spawnList.remove(0);
          } 
          player.teleport(spawnBleu.get(0));
          continue;
        } 
        if (this.main.sbHandler.getTeam(player).equals("vert")) {
          if (spawnVert.isEmpty()) {
            spawnVert.add(((Location)spawnList.get(0)).clone());
            spawnList.remove(0);
          } 
          player.teleport(spawnVert.get(0));
          continue;
        } 
        if (this.main.sbHandler.getTeam(player).equals("rose")) {
          if (spawnRose.isEmpty()) {
            spawnRose.add(((Location)spawnList.get(0)).clone());
            spawnList.remove(0);
          } 
          player.teleport(spawnRose.get(0));
          continue;
        } 
        if (this.main.sbHandler.getTeam(player).equals("jaune")) {
          if (spawnJaune.isEmpty()) {
            spawnJaune.add(((Location)spawnList.get(0)).clone());
            spawnList.remove(0);
          } 
          player.teleport(spawnJaune.get(0));
          continue;
        } 
        Bukkit.broadcastMessage(String.valueOf(player.getName()) + " n'a pas d'ami.");
        player.teleport(((Location)spawnList.get(0)).clone());
        spawnList.remove(0);
      } 
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "timer");
    } 
  }
}
