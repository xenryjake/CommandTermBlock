package com.xenry.commandtermblock;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henry on 8/15/15.
 */
public class CommandTermBlock extends JavaPlugin {

    private List<String> blockedTerms = new ArrayList<>(), blockInCommands = new ArrayList<>(), blockedCommands = new ArrayList<>();
    private boolean blockInAllCommands, permissionBypass;

    private static CommandTermBlock instance;

    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        saveDefaultConfig();
        updateConfig();
        instance = this;
        getLogger().info("CommandTermBlock enabled!");
    }

    public static CommandTermBlock getInstance(){
        return instance;
    }

    public void updateConfig(){
        reloadConfig();
        try{
            blockedTerms = getConfig().getStringList("blocked-terms");
            blockInCommands = getConfig().getStringList("block-terms-in-commands");
            blockedCommands = getConfig().getStringList("blocked-commands");
            blockInAllCommands = getConfig().getBoolean("block-in-all-commands");
            permissionBypass = getConfig().getBoolean("permission-bypass");
        }catch(Exception ex){
            ex.printStackTrace();
            getLogger().warning("Invalid data in configuration file!");
        }
    }

    public List<String> getBlockedTerms(){
        return blockedTerms;
    }

    public List<String> getBlockInCommands(){
        return blockInCommands;
    }

    public List<String> getBlockedCommands(){
        return blockedCommands;
    }

    public boolean blocksInAllCommands(){
        return blockInAllCommands;
    }

    public boolean isPermissionBypass(){
        return permissionBypass;
    }

}
