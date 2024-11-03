package fr.klemad.pluginuhc;

import java.util.ArrayList;
import java.util.Collections;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;

public class Main extends JavaPlugin {
  public boolean chute;
  
  public boolean pvp;
  
  public int tempspvp;
  
  public ScoreboardHandler sbHandler;
  
  public ArrayList<String> participants = new ArrayList<>();
  
  public int nbParticipants;
  
  public Location lobby;
  
  public int episode;
  
  public boolean rapide;
  
  public int dureebordures;
  
  public int tempsbordures;
  
  public boolean borduresBool;
  
  public boolean equipe;
  
  public void onEnable() {
    System.out.println("Test de spigot active");
    this.equipe = false;
    this.nbParticipants = 0;
    this.tempsbordures = 84000;
    this.rapide = false;
    this.equipe = false;
    this.lobby = new Location(Bukkit.getWorld("world"), 0.0D, 201.0D, 0.0D);
    Bukkit.getWorld("world").setDifficulty(Difficulty.NORMAL);
    WorldBorder wb = Bukkit.getWorld("world").getWorldBorder();
    wb.setCenter(0.0D, 0.0D);
    wb.setSize(1500.0D);
    this.tempspvp = 36000;
    this.dureebordures = 2400;
    this.chute = true;
    this.pvp = false;
    this.borduresBool = false;
    (new EventManager((Plugin)this, this)).registerEvents();
    World world = Bukkit.getWorlds().get(0);
    for (int row = -9; row < 10; row++) {
      for (int col = -9; col < 10; col++) {
        Location loc = new Location(world, row, 200.0D, col);
        loc.getBlock().setType(Material.GLASS);
      } 
    } 
    for (int compteur = -9; compteur < 10; compteur++) {
      Location loc1 = new Location(world, compteur, 202.0D, -10.0D);
      loc1.getBlock().setType(Material.GLASS);
      Location loc2 = new Location(world, -10.0D, 202.0D, compteur);
      loc2.getBlock().setType(Material.GLASS);
      Location loc3 = new Location(world, compteur, 202.0D, 10.0D);
      loc3.getBlock().setType(Material.GLASS);
      Location loc4 = new Location(world, 10.0D, 202.0D, compteur);
      loc4.getBlock().setType(Material.GLASS);
    } 
    this.sbHandler = new ScoreboardHandler(this);
  }
  
  public void onDisable() {
    this.sbHandler.scoreboard.clearSlot(DisplaySlot.SIDEBAR);
    this.sbHandler.scoreboard.clearSlot(DisplaySlot.PLAYER_LIST);
    this.sbHandler.rouge.unregister();
    this.sbHandler.bleu.unregister();
    this.sbHandler.rose.unregister();
    this.sbHandler.vert.unregister();
    this.sbHandler.jaune.unregister();
    this.sbHandler.timer.unregister();
    System.out.println("Le serveur s'éteint !");
  }
  
  public void updateJour() {
    Bukkit.getScheduler().runTaskTimer((Plugin)this, new Runnable() {
          public void run() {
            Main.this.sbHandler.updateJour();
            if (Main.this.sbHandler.jour == 1) { /* Pas de message au début de l'uhc */
            	
            }
            else if (Main.this.sbHandler.jour == 2) { /* La première journée dure 20 minutes, pas de message au bout de 10 minutes. */
            	
            }
            else if (Main.this.sbHandler.jour % 2 == 0) { /* S'il faisait nuit et que ce n'était pas la "première nuit" (que l'on saute) */
              for (String player : Main.this.participants)
                Bukkit.getServer().getPlayer(player).sendMessage("C'est le jour " + Main.this.sbHandler.compteurString); 
            } else if (Main.this.sbHandler.jour % 2 == 1) {
              Bukkit.broadcastMessage("Ca va être tout noir !");
            } 
          }
        }, 0L, 12000L); /* 12000L pour le cycle normal, 600L pour 30s */
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("randomteam") && 
      args.length != 0) {
      Collections.shuffle(this.participants);
      int n = Integer.parseInt(args[0]);
      for (String joueur : this.participants) {
        Player p = Bukkit.getServer().getPlayer(joueur);
        if (!this.sbHandler.getTeam(p).equals("none"))
          this.sbHandler.getTeamTeam(p).removeEntry(joueur); 
        if (this.sbHandler.rouge.getSize() < n) {
          System.out.println("test rouge");
          this.sbHandler.rouge.addEntry(joueur);
          continue;
        } 
        if (this.sbHandler.bleu.getSize() < n) {
          this.sbHandler.bleu.addEntry(joueur);
          continue;
        } 
        if (this.sbHandler.rose.getSize() < n) {
          this.sbHandler.rose.addEntry(joueur);
          continue;
        } 
        if (this.sbHandler.vert.getSize() < n) {
          this.sbHandler.vert.addEntry(joueur);
          continue;
        } 
        this.sbHandler.jaune.addEntry(joueur);
      } 
    } 
    if (cmd.getName().equalsIgnoreCase("timer")) {
      for (String player : this.participants)
        Bukkit.getServer().getPlayer(player).sendMessage(ChatColor.LIGHT_PURPLE + "Zééé parti"); 
      this.sbHandler.updateJour();
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "gamerule doDaylightCycle false");
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "time set day");
      this.sbHandler.pvp = this.sbHandler.timer.getScore("PVP : " + ChatColor.RED + "Off");
      this.sbHandler.pvp.setScore(5);
      BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
      Bukkit.getScheduler().runTaskTimer((Plugin)this, new Runnable() {
            public void run() {
              Main.this.sbHandler.updateTime();
              Main.this.sbHandler.updateBordures();
            }
          },  0L, 20L);
      scheduler.scheduleSyncDelayedTask((Plugin)this, new Runnable() {
            public void run() {
              Main.this.updateJour();
              Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "gamerule doDaylightCycle true");
            }
          }, 12000L); /* vrai temps 12000L*/
      scheduler.scheduleSyncDelayedTask((Plugin)this, new Runnable() {
            public void run() {
              if (!Main.this.borduresBool)
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "bordures"); 
            }
          }, this.tempsbordures);
      scheduler.scheduleSyncDelayedTask((Plugin)this, new Runnable() {
            public void run() {
              Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "gamerule doDaylightCycle false");
              Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "time set day");
            }
          }, (this.tempsbordures + this.dureebordures * 20));
      scheduler.scheduleSyncDelayedTask((Plugin)this, new Runnable() {
            public void run() {
              Bukkit.broadcastMessage("Damages actifs !");
              Main.this.chute = false;
            }
          },  600L);
      scheduler.scheduleSyncDelayedTask((Plugin)this, new Runnable() {
            public void run() {
              Bukkit.broadcastMessage("Le PVP est actif !");
              Main.this.pvp = true;
              Main.this.sbHandler.updatePVP();
            }
          },  this.tempspvp);
    } 
    if (cmd.getName().equalsIgnoreCase("steak"))
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "give @a minecraft:cooked_beef 5"); 
    if (cmd.getName().equalsIgnoreCase("bordures")) {
      for (String player : this.participants)
        Bukkit.getServer().getPlayer(player).sendMessage("Les bordures retrecissent en " + (this.dureebordures / 60) + " min. Sortez vite du nether !!"); 
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "worldborder set 200 " + this.dureebordures);
      this.borduresBool = true;
      this.sbHandler.scoreboard.resetScores("Bordures : " + this.sbHandler.bord);
      this.sbHandler.updateBordures();
    } 
    if (cmd.getName().equalsIgnoreCase("resetbordures")) {
      this.borduresBool = false;
      if (cmd.getName().equalsIgnoreCase("tempspvp"))
        if (args.length == 0) {
          Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "worldborder set 1500");
        } else {
          Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "worldborder set " + args[0]);
        }  
    } 
    if (cmd.getName().equalsIgnoreCase("pvp")) {
      Bukkit.broadcastMessage("Le PVP est actif !");
      this.pvp = true;
      this.sbHandler.updatePVP();
    } 
    if (cmd.getName().equalsIgnoreCase("tempspvp")) {
      if (args.length == 0)
        return false; 
      this.tempspvp = Integer.parseInt(args[0]) * 1200;
    } 
    if (cmd.getName().equalsIgnoreCase("rapide"))
      if (this.rapide) {
        this.tempspvp = 36000;
        this.rapide = false;
        this.sbHandler.offRapide();
        this.tempsbordures = 84000;
      } else {
        this.tempspvp = 24000;
        this.rapide = true;
        this.sbHandler.onRapide();
        this.tempsbordures = 48000;
      }  
    return false;
  }
}
