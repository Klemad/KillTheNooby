package fr.klemad.pluginuhc.listener;

import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

public class FurnaceListener implements Listener {
  @EventHandler
  public void furnaceSmeltEvent(FurnaceSmeltEvent event) {
    Furnace furnace = (Furnace)event.getBlock().getState();
    furnace.setCookTime((short)170);
    furnace.update();
  }
}
