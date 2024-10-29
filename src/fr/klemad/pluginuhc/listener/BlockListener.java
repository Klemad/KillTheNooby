package fr.klemad.pluginuhc.listener;

import fr.klemad.pluginuhc.Main;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

public class BlockListener implements Listener {
  public Main main;
  
  public int diamantRouge;
  
  public int diamantBleu;
  
  public int diamantVert;
  
  public int diamantRose;
  
  public int diamantJaune;
  
  Map<Player, Integer> map;
  
  Map<Team, Integer> mapTeam;
  
  public BlockListener(Main main) {
    this.map = new WeakHashMap<>();
    this.mapTeam = new WeakHashMap<>(500);
    this.main = main;
  }
  
  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();
    if (this.main.rapide) {
      if (event.getBlock().getType() == Material.GOLD_ORE) {
        event.setCancelled(true);
        block.setType(Material.AIR);
        player.getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), new ItemStack(Material.GOLD_INGOT, 2));
        event.getPlayer().giveExp(3);
      } 
      if (event.getBlock().getType() == Material.IRON_ORE) {
        event.setCancelled(true);
        block.setType(Material.AIR);
        player.getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), new ItemStack(Material.IRON_INGOT, 2));
        event.getPlayer().giveExp(2);
      } 
      if (block.getType().equals(Material.GRAVEL)) {
        event.setCancelled(true);
        block.setType(Material.AIR);
        player.getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), new ItemStack(Material.FLINT, 1));
      } 
      if (block.getType().equals(Material.SAND)) {
        event.setCancelled(true);
        block.setType(Material.AIR);
        player.getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), new ItemStack(Material.GLASS_BOTTLE, 1));
      } 
      if (block.getType().equals(Material.COAL_ORE)) {
        event.setCancelled(true);
        block.setType(Material.AIR);
        player.getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), new ItemStack(Material.TORCH, 4));
        event.getPlayer().giveExp(2);
      } 
    } 
    if (block.getType() == Material.DIAMOND_ORE) {
      int mined = ((Integer)this.map.getOrDefault(player, Integer.valueOf(0))).intValue();
      if (this.main.rapide && mined < 18) {
        this.map.put(player, Integer.valueOf(++mined));
        player.getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), new ItemStack(Material.DIAMOND, 1));
      } 
      if (mined >= 18) {
        event.setCancelled(true);
        block.setType(Material.AIR);
        player.getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), new ItemStack(Material.GOLD_INGOT, 1));
        event.getPlayer().giveExp(4);
      } 
      this.map.put(player, Integer.valueOf(++mined));
    } 
    if (block.getType().equals(Material.LEAVES) || block.getType().equals(Material.LEAVES_2)) {
      event.setCancelled(true);
      block.setType(Material.AIR);
      Random rand = new Random();
      int randomNumber = rand.nextInt(5);
      switch (randomNumber) {
        default:
          return;
        case 1:
          break;
      } 
      event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), new ItemStack(Material.APPLE, 1));
    } 
    if (!this.main.rapide && block.getType().equals(Material.GRAVEL)) {
      event.setCancelled(true);
      event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), new ItemStack(Material.GRAVEL, 1));
      block.setType(Material.AIR);
      Random rand = new Random();
      int randomNumber = rand.nextInt(5);
      switch (randomNumber) {
        default:
        
        case 1:
          break;
      } 
      event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), new ItemStack(Material.FLINT, 1));
    } 
  }
}
