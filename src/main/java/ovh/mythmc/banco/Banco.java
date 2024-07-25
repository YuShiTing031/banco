package ovh.mythmc.banco;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import ovh.mythmc.banco.commands.BalanceCommand;
import ovh.mythmc.banco.commands.BancoCommand;
import ovh.mythmc.banco.economy.AccountManager;
import ovh.mythmc.banco.listeners.EntityDeathListener;
import ovh.mythmc.banco.listeners.PlayerJoinListener;
import ovh.mythmc.banco.listeners.PlayerQuitListener;
import ovh.mythmc.banco.utils.TranslationUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Banco extends JavaPlugin {

    private static Banco instance;

    private AccountManager accountManager;
    private BancoVaultImpl vaultImpl;

    private BukkitTask autoSaveTask;

    @Override
    public void onEnable() {
        instance = this;
        vaultImpl = new BancoVaultImpl();
        accountManager = new AccountManager();

        saveDefaultResources();
        TranslationUtils.register();

        registerListeners();
        registerCommands();

        loadData();

        if (getConfig().getBoolean("auto-save.enabled"))
            startAutoSaver();

        hook();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
            new BancoPlaceholderExpansion().register();
    }

    @Override
    public void onDisable() {
        unhook();

        stopAutoSaver();
        try {
            saveData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveDefaultResources() {
        File config = new File(getDataFolder(), "config.yml");
        File data = new File(getDataFolder(), "data.yml");

        if (!config.exists())
            saveResource("config.yml", false);
        if (!data.exists())
            saveResource("data.yml", false);
    }

    public void reload() {
        reloadConfig();

        if (autoSaveTask != null)
            stopAutoSaver();

        startAutoSaver();
    }

    private void loadData() {
        File dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists())
            return;

        YamlConfiguration dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        getEconomyManager().loadData(dataConfig);
    }

    private void saveData() throws IOException {
        File dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists())
            dataFile.createNewFile();

        YamlConfiguration dataConfig = new YamlConfiguration();
        getEconomyManager().saveData(dataConfig);
        dataConfig.save(dataFile);
    }

    private void startAutoSaver() {
        this.autoSaveTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            try {
                saveData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 0, getConfig().getInt("auto-save.frequency") * 20L);
    }

    private void stopAutoSaver() {
        this.autoSaveTask.cancel();
    }

    private void registerListeners() {
        if (getConfig().getBoolean("currency.remove-drops"))
            Bukkit.getPluginManager().registerEvents(new EntityDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("banco")).setExecutor(new BancoCommand());
        Objects.requireNonNull(getCommand("balance")).setExecutor(new BalanceCommand());
    }

    private void hook() {
        Bukkit.getServicesManager().register(Economy.class, vaultImpl, this, ServicePriority.Normal);
    }

    private void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class, vaultImpl);
    }

    @Deprecated
    public static Banco getInstance() {
        return instance;
    }

    public static Banco get() { return instance; }

    @Deprecated
    public AccountManager getEconomyManager() { return accountManager; }

    public AccountManager getAccountManager() { return accountManager; }
}
