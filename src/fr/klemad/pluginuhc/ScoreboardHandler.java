package fr.klemad.pluginuhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardHandler implements Listener {
  public Main main;
  
  public Scoreboard scoreboard;
  
  public Objective timer;
  
  public Score score;
  
  public Score day;
  
  public Score joueurs;
  
  public Score bordures;
  
  public Score tempsBordures;
  
  public Score pvp;
  
  public Score rapide;
  
  public Score equipe;
  
  public Team rouge;
  
  public Team bleu;
  
  public Team rose;
  
  public Team vert;
  
  public Team jaune;
  
  public Objective vie;
  
  public int heure;
  
  public int minute;
  
  public int seconde;
  
  public int jour = 0;
  
  public int compteurString = 0;
  
  double bord;
  
  public ScoreboardHandler(Main main) {
    this.main = main;
    this.heure = 0;
    this.minute = 0;
    this.seconde = 0;
    this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    if (this.scoreboard.getObjectives().isEmpty()) {
      this.vie = this.scoreboard.registerNewObjective("vie", "health");
      this.vie.setDisplayName(ChatColor.RED + "vie");
      this.vie.setDisplaySlot(DisplaySlot.PLAYER_LIST);
      this.timer = this.scoreboard.registerNewObjective("Timer", "dummy");
      this.timer.setDisplayName(ChatColor.DARK_AQUA + "UHC");
      this.timer.setDisplaySlot(DisplaySlot.SIDEBAR);
      this.day = this.timer.getScore("Nuit 0");
      this.bord = main.lobby.getWorld().getWorldBorder().getSize();
      this.bordures = this.timer.getScore("Bordures :" + this.bord);
      this.rouge = this.scoreboard.registerNewTeam("rouge");
      this.bleu = this.scoreboard.registerNewTeam("bleu");
      this.rose = this.scoreboard.registerNewTeam("rose");
      this.vert = this.scoreboard.registerNewTeam("vert");
      this.jaune = this.scoreboard.registerNewTeam("jaune");
      this.rouge.setDisplayName("rouge");
      this.bleu.setDisplayName("bleu");
      this.rose.setDisplayName("rose");
      this.vert.setDisplayName("vert");
      this.jaune.setDisplayName("jaune");
      this.rouge.setAllowFriendlyFire(false);
      this.bleu.setAllowFriendlyFire(false);
      this.rose.setAllowFriendlyFire(false);
      this.vert.setAllowFriendlyFire(false);
      this.jaune.setAllowFriendlyFire(false);
      this.rouge.setPrefix(ChatColor.RED.toString());
      this.bleu.setPrefix(ChatColor.AQUA.toString());
      this.rose.setPrefix(ChatColor.LIGHT_PURPLE.toString());
      this.vert.setPrefix(ChatColor.GREEN.toString());
      this.jaune.setPrefix(ChatColor.YELLOW.toString());
      this.rouge.setSuffix(ChatColor.RESET.toString());
      this.bleu.setSuffix(ChatColor.RESET.toString());
      this.rose.setSuffix(ChatColor.RESET.toString());
      this.vert.setSuffix(ChatColor.RESET.toString());
      this.jaune.setSuffix(ChatColor.RESET.toString());
      this.rapide = this.timer.getScore("Rapide : " + ChatColor.RED + "Off");
      this.rapide.setScore(6);
      this.joueurs = this.timer.getScore("Participants : " + main.nbParticipants);
      this.joueurs.setScore(2);
    } 
  }
  
  public String getTeam(Player p) {
	  String joueur = p.getName();
    for (Team t : this.scoreboard.getTeams()) {
      if (t.hasEntry(joueur))
        return t.getName(); 
    } 
    return "none";
  }
  
  public Team getTeamTeam(Player p) {
	  String joueur = p.getName();
    for (Team t : this.scoreboard.getTeams()) {
      if (t.hasEntry(joueur))
        return t; 
    }
    return null;
  }
  
  public void sendPlayerScoreboard(Player p) {
    p.setScoreboard(this.scoreboard);
  }
  
  public void updateTime() {
    this.scoreboard.resetScores(ChatColor.DARK_AQUA + Integer.toString(this.heure) + "h " + Integer.toString(this.minute) + "min " + Integer.toString(this.seconde) + "s");
    this.seconde++;
    if (this.seconde >= 60) {
      this.minute++;
      this.seconde %= 60;
      if (this.minute >= 60) {
        this.heure++;
        this.minute %= 60;
      } 
    }
    this.score = this.timer.getScore(ChatColor.DARK_AQUA + Integer.toString(this.heure) + "h " + Integer.toString(this.minute) + "min " + Integer.toString(this.seconde) + "s");
    this.score.setScore(0);
  }
  
  public void updateJour() {
	if (this.jour == 0) {
		this.scoreboard.resetScores("Nuit " + this.compteurString);
	    this.compteurString++;
	    this.day = this.timer.getScore("Jour " + this.compteurString);
	}
	else if (this.jour == 1) { /* On ne fait rien à la fin de la première nuit*/
		
	}
	else if (this.jour % 2 == 1) { /* Si on appelle updateJour alors qu'il faisait nuit */
      this.scoreboard.resetScores("Nuit " + this.compteurString);
      this.compteurString++;
      this.day = this.timer.getScore("Jour " + this.compteurString);
    }
	else { /* Si on appelle updateJour alors qu'il faisait nuit */    	
      this.scoreboard.resetScores("Jour " + this.compteurString);
      this.day = this.timer.getScore("Nuit " + this.compteurString);
    } 
    this.jour++;
    this.day.setScore(1);
  }
  
  public void updateJoueur() {
    int ancienParticipants = this.main.nbParticipants - 1;
    this.scoreboard.resetScores("Participants : " + ancienParticipants);
    this.joueurs = this.timer.getScore("Participants : " + this.main.nbParticipants);
    this.joueurs.setScore(2);
  }
  
  public void dead() {
    int ancienParticipants = this.main.nbParticipants + 1;
    this.scoreboard.resetScores("Participants : " + ancienParticipants);
    this.joueurs = this.timer.getScore("Participants : " + this.main.nbParticipants);
    this.joueurs.setScore(2);
  }
  
  public void updateBordures() {
    if (!this.main.borduresBool) {
      this.scoreboard.resetScores("Bordures : " + this.bord);
      this.bord = (int)(this.main.lobby.getWorld().getWorldBorder().getSize() / 2.0D);
      this.bordures = this.timer.getScore("Bordures : " + this.bord);
      this.bordures.setScore(3);
    } else {
      this.scoreboard.resetScores("Bordures : " + ChatColor.RED + this.bord);
      this.bord = (int)(this.main.lobby.getWorld().getWorldBorder().getSize() / 2.0D);
      this.bordures = this.timer.getScore("Bordures : " + ChatColor.RED + this.bord);
      this.bordures.setScore(3);
    } 
  }
  
  public void onRapide() {
    this.scoreboard.resetScores("Rapide : " + ChatColor.RED + "Off");
    this.rapide = this.timer.getScore("Rapide : " + ChatColor.GREEN + "On");
    this.rapide.setScore(5);
  }
  
  public void offRapide() {
    this.scoreboard.resetScores("Rapide : " + ChatColor.GREEN + "On");
    this.rapide = this.timer.getScore("Rapide : " + ChatColor.RED + "Off");
    this.rapide.setScore(5);
  }
  
  public void onEquipe() {
    this.scoreboard.resetScores("Solo");
    this.equipe = this.timer.getScore("Equipe");
    this.equipe.setScore(5);
  }
  
  public void offEquipe() {
    this.scoreboard.resetScores("Equipe");
    this.equipe = this.timer.getScore("Solo");
    this.equipe.setScore(5);
  }
  
  public void updatePVP() {
    this.scoreboard.resetScores("PVP : " + ChatColor.RED + "Off");
    this.pvp = this.timer.getScore("PVP : " + ChatColor.GREEN + "On");
    this.pvp.setScore(4);
  }
  
  public boolean memeEquipe(Player p1, Player p2) {
    String equipe1 = getTeam(p1);
    String equipe2 = getTeam(p2);
    if (!equipe1.equalsIgnoreCase("none"))
      return equipe1.equalsIgnoreCase(equipe2); 
    return false;
  }
}
