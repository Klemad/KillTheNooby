package fr.klemad.pluginuhc;

import fr.klemad.pluginuhc.listener.BlockListener;
import fr.klemad.pluginuhc.listener.CompassListener;
import fr.klemad.pluginuhc.listener.CraftListener;
import fr.klemad.pluginuhc.listener.DamageListener;
import fr.klemad.pluginuhc.listener.DropListener;
import fr.klemad.pluginuhc.listener.FurnaceListener;
import fr.klemad.pluginuhc.listener.JoinEvent;
import fr.klemad.pluginuhc.listener.Menu;
import fr.klemad.pluginuhc.listener.RegenListener;
import fr.klemad.pluginuhc.listener.WeatherListener;
import fr.klemad.pluginuhc.listener.PotionListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class EventManager {
  public Plugin plugin;
  
  public PluginManager pm;
  
  public Main main;
  
  public EventManager(Plugin plugin, Main main) {
    this.plugin = plugin;
    this.main = main;
    this.pm = Bukkit.getPluginManager();
  }
  
  public void registerEvents() {
    Menu menu = new Menu(this.main);
    this.pm.registerEvents((Listener)menu, this.plugin);
    this.pm.registerEvents((Listener)new JoinEvent(menu, this.main), this.plugin);
    this.pm.registerEvents((Listener)new BlockListener(this.main), this.plugin);
    this.pm.registerEvents((Listener)new FurnaceListener(), this.plugin);
    this.pm.registerEvents((Listener)new RegenListener(), this.plugin);
    this.pm.registerEvents((Listener)new DamageListener(this.main), this.plugin);
    this.pm.registerEvents((Listener)new WeatherListener(), this.plugin);
    this.pm.registerEvents((Listener)new DropListener(this.main), this.plugin);
    this.pm.registerEvents((Listener)new CraftListener(this.main), this.plugin);
    this.pm.registerEvents((Listener)new CompassListener(this.main), this.plugin);
    this.pm.registerEvents((Listener)new PotionListener(this.main), this.plugin);
  }
}
