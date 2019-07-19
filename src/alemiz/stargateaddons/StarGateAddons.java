package alemiz.stargateaddons;

import alemiz.stargateaddons.staff.Staff;
import alemiz.stargateaddons.staffchat.StaffChat;
import com.google.common.io.ByteStreams;
import io.github.waterfallmc.waterfall.QueryResult;
import io.github.waterfallmc.waterfall.event.ProxyQueryEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StarGateAddons extends Plugin implements Listener {

    public Configuration cfg;

    public StaffChat StaffChat;
    public Staff Staff;

    private static StarGateAddons instance;

    @Override
    public void onEnable() {
        instance = this;

        this.getLogger().info("§cRegistring Config");
        this.registerConfig();

        this.getLogger().info("§cRegistring StarGate-Addons Listener");
        getProxy().getPluginManager().registerListener(this, this);

        this.StaffChat = new StaffChat(this);
        this.Staff = new Staff(this);
    }

    public static StarGateAddons getInstance() {
        return instance;
    }

    private void registerConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = getResourceAsStream("config.yml");
                     OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }

        try {
            cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Color Translator
    public String colorText(String message) {
        return ChatColor.translateAlternateColorCodes('§', message);
    }

    //LISTENERS
    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing ping = event.getResponse();
        ServerPing.Players p = ping.getPlayers();

        ping.setPlayers(new ServerPing.Players(p.getOnline() + 1, p.getOnline(), ping.getPlayers().getSample()));

        ServerPing.Protocol prot = new ServerPing.Protocol("", ping.getVersion().getProtocol());
        ping.setVersion(prot);

        event.setResponse(ping);
    }

    @EventHandler
    public void SetQuery(ProxyQueryEvent event) {
        QueryResult result = event.getResult();
        result.setVersion("1.12.0");
        //result.setWorldName("Test");
        event.setResult(result);
    }
}
