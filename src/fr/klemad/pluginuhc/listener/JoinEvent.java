package fr.klemad.pluginuhc.listener;

import fr.klemad.pluginuhc.Main;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class JoinEvent implements Listener {
  Menu menu;
  
  Main main;
  
  public ArrayList<String> participants;
  
  public Location lobby;
  
  public JoinEvent(Menu menu, Main main) {
    this.menu = menu;
    this.participants = main.participants;
    this.main = main;
    this.lobby = main.lobby;
  }
  
  public void setPlayer(Player p) {
    p.teleport(this.lobby);
    p.setGameMode(GameMode.ADVENTURE);
    p.setHealth(20.0D);
    p.setFoodLevel(20);
    p.setExp(0.0F);
    p.setLevel(0);
  }
  
  public void setItemStack(Player p) {
    p.getInventory().clear();
    ItemStack itm = new ItemStack(Material.NETHER_STAR);
    ItemMeta itmM = itm.getItemMeta();
    itmM.setDisplayName("Menu");
    itm.setItemMeta(itmM);
    ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
    BookMeta bookMeta = (BookMeta)writtenBook.getItemMeta();
    bookMeta.setTitle("Rde l'UHC");
    bookMeta.setAuthor("Klemad");
    List<String> pages = new ArrayList<>();
    pages.add(ChatColor.RED + "  ** Kill The Nooby **" + ChatColor.RESET + 
        "\n1 Sommaire" + 
        ChatColor.LIGHT_PURPLE + "\n2 R" + ChatColor.RESET + 
        ChatColor.RED + "\n3 PVP" + ChatColor.RESET + 
        ChatColor.GOLD + "\n4 Bordures" + ChatColor.RESET + 
        ChatColor.BLUE + "\n5 Drop" + ChatColor.RESET + 
        ChatColor.GREEN + "\n6 Potions" + ChatColor.RESET + 
        ChatColor.GRAY + "\n7 Cuisson" + ChatColor.RESET + 
        ChatColor.AQUA + "\n8 Ajouts" + ChatColor.RESET + 
        ChatColor.DARK_GREEN + "\n9 Participer" + ChatColor.RESET + 
        ChatColor.DARK_PURPLE + "\n10 Equipes" + ChatColor.RESET + 
        ChatColor.BLACK + "\n11 Spawn" + ChatColor.RESET + 
        ChatColor.BOLD + "\n12 Jouer" + ChatColor.RESET + 
        
        ChatColor.ITALIC + "\nPour " + p.getName() + " le noob." + ChatColor.BLACK);
    pages.add(ChatColor.LIGHT_PURPLE + "  ** R**" + ChatColor.RESET + 
        "\nLes coeurs ne rpas avec la nourriture. " + 
        "\n\nIl faut utiliser des pommes d'or, des potions de soin ou de rpour rde la vie.");
    pages.add(ChatColor.RED + "  **      PVP      **" + ChatColor.RESET + 
        "\nLe PVP est actif " + (this.main.tempspvp / 1200) + " minutes par d" + 
        "\n\nPas de friendly-fire." + 
        "\n\nChaque joueur qui meurt drop une pomme d'or en plus de son stuff.");
    pages.add(ChatColor.GOLD + "  **  Bordures   **" + ChatColor.RESET + 
        "\nLes bordures vont de -750 750 en chaque coordonnee" + 
        "\n\nElles rapr1h" + (this.main.tempsbordures / 1200 % 60) + " en " + (this.main.dureebordures / 60) + " minutes." + 
        "\n\nA la fin du rles bordures vont de -100 100 en chaque coordonee");
    pages.add(ChatColor.BLUE + "  **     Drop     **" + ChatColor.RESET + 
        "\nDrop de 1/5 des pommes sur toutes les feuilles." + 
        "\nDrop de 1/5 du silex sur le gravier." + 
        "\nLimite de 18 diamants mindrop ensuite 1 lingot d'or la place." + 
        "\nDrop de 1/20 de pomme d'or sur les chauves-souris.");
    pages.add(ChatColor.DARK_GREEN + "  **    Potions   **" + ChatColor.RESET + 
        "\nLes potions de force donnent +2,5 coeurs de davant rpar l'armure." + 
        "\n" + "\n" + "Potions de force en splash interdites." + 
        "\n" + "\n" + "Potions de niveau 2 interdites.");
    pages.add(ChatColor.GRAY + "  **    Cuisson   **" + ChatColor.RESET + 
        "\nLes fours cuisent plus rapidement aprune premicuisson complete");
    pages.add(ChatColor.AQUA + "  **    Ajouts   **" + ChatColor.RESET + 
        "\nPour connata hauteur, clic-droit avec une pioche." + 
        "\nLa boussole pointe vers la position du joueur le plus proche au moment du clic. Il sera pr");
    pages.add(ChatColor.DARK_GREEN + "  **  Participer  **" + ChatColor.RESET + 
        "\nClic droit vers le ciel sur la netherstar pour ouvrir le menu." + 
        "\n\nClic sur " + ChatColor.BOLD + "Participer l'UHC" + ChatColor.BLACK + " pour jouer.");
    pages.add(ChatColor.DARK_PURPLE + "  **  Equipes  **" + ChatColor.RESET + 
        "\nClic sur " + ChatColor.BOLD + "Rejoindre une team" + ChatColor.BLACK + " puis sur la couleur de la team que tu veux rejoindre." + 
        "\n\nPour des altape la commande :" + ChatColor.BOLD + "\n/randomteam nombreDeJoueursParEquipes" + ChatColor.BLACK);
    pages.add(ChatColor.BLACK + "  **   Spawn   **" + ChatColor.BLACK + 
        "\nIl y a 8 spawns disponibles. Les membres d'une mspawn au mpoint.");
    pages.add(ChatColor.BOLD + "  **  Jouer   **" + ChatColor.BLACK + 
        "\n\n\nSi tout le monde est prclic sur " + ChatColor.BOLD + "Lancer l'UHC" + ChatColor.BLACK + "." + 
        "\n\nTout le monde recevra 5 steaks et la partie commencera !");
    bookMeta.setPages(pages);
    writtenBook.setItemMeta((ItemMeta)bookMeta);
    p.getInventory().setItem(0, writtenBook);
    p.getInventory().setItem(4, itm);
    p.updateInventory();
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    Bukkit.getServer().getPlayer(p.getName()).sendMessage(ChatColor.RED + "Bienvenue dans Kill The Nooby !");
    if (!this.participants.contains(p.getName())) {
      setItemStack(p);
      setPlayer(p);
      e.setJoinMessage(String.valueOf(p.getName()) + " ddans Kill The Nooby !");
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "spawnpoint " + p.getName() + " 0 201 0");
    } 
    this.main.sbHandler.sendPlayerScoreboard(e.getPlayer());
  }
  
  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent e) {
    if (e.getEntityType() == EntityType.PLAYER) {
      ItemStack pomme = new ItemStack(Material.GOLDEN_APPLE);
      e.getDrops().add(pomme);
      Player joueur = e.getEntity();
      this.participants.remove(joueur.getName());
      this.main.nbParticipants--;
      this.main.sbHandler.dead();
      joueur.setGameMode(GameMode.SPECTATOR);
      joueur.teleport(this.lobby);
    } 
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    e.setQuitMessage(String.valueOf(p.getName()) + " est un noob cosmique.");
  }
}
