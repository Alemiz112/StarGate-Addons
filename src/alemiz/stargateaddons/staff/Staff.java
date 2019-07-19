package alemiz.stargateaddons.staff;


import alemiz.stargateaddons.StarGateAddons;
import alemiz.stargateaddons.commands.PlayerFindCommand;
import alemiz.stargateaddons.commands.StaffFindCommand;

public class Staff {

    protected StarGateAddons plugin;

    public Staff(StarGateAddons plugin){
        this.plugin = plugin;

        this.registerCommands();
    }

    private void registerCommands(){
        plugin.getProxy().getPluginManager().registerCommand(plugin, new PlayerFindCommand(this));

        plugin.getProxy().getPluginManager().registerCommand(plugin, new StaffFindCommand(this));
    }

    
}
