package com.xenry.commandtermblock;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by henry on 8/15/15.
 */
public class CommandListener implements Listener {

    @EventHandler
    public void on(PlayerCommandPreprocessEvent e){
        String[] split = e.getMessage().substring(1).split(" ");
        if(split.length < 1) return;
        String label = split[0];
        if(label.equalsIgnoreCase("reload-ctb")){
            e.setCancelled(true);
            if(!e.getPlayer().hasPermission("commandtermblock.reload")){
                e.getPlayer().sendMessage("§cYou are not permitted to do that.");
                return;
            }
            CommandTermBlock.getInstance().updateConfig();
            e.getPlayer().sendMessage("§aConfig reloaded.");
            return;
        }
        for(String cmdl : CommandTermBlock.getInstance().getBlockedCommands()){
            if(cmdl.equalsIgnoreCase(label)){
                e.getPlayer().sendMessage("§cThat command is disabled.");
                e.setCancelled(true);
                return;
            }
        }
        if(CommandTermBlock.getInstance().isPermissionBypass() && e.getPlayer().hasPermission("commandtermblock.bypass")) return;
        if(split.length < 2) return;
        String args = "";
        for(int i = 1; i < split.length; i++)
            args = args + split[i];
        boolean blockInCommand = CommandTermBlock.getInstance().blocksInAllCommands();
        if(!blockInCommand){
            for(String cmdl : CommandTermBlock.getInstance().getBlockInCommands()){
                if(cmdl.equalsIgnoreCase(label)){
                    blockInCommand = true;
                    break;
                }
            }
        }
        if(!blockInCommand) return;
        args=args.toLowerCase();
        for(String term : CommandTermBlock.getInstance().getBlockedTerms()){
            if(args.contains(term.toLowerCase())){
                e.setCancelled(true);
                e.getPlayer().sendMessage("§cYou are not permitted to use the term '" + term + "' in this command.");
            }
        }
    }

}
