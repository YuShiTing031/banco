package ovh.mythmc.banco.api.economy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.simpleyaml.configuration.ConfigurationSection;
import ovh.mythmc.banco.api.Banco;
import ovh.mythmc.banco.api.logger.LoggerWrapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EconomyManager {

    static final LoggerWrapper logger = new LoggerWrapper() {
        @Override
        public void info(String message, Object... args) {
            Banco.get().getLogger().info("[eco-manager] " + message, args);
        }

        @Override
        public void warn(String message, Object... args) {
            Banco.get().getLogger().warn("[eco-manager] " + message, args);
        }

        @Override
        public void error(String message, Object... args) {
            Banco.get().getLogger().error("[eco-manager] " + message, args);
        }
    };

    public static final EconomyManager instance = new EconomyManager();
    //private static final Map<String, Double> valuesMap = new HashMap<>();
    private static final List<ValuableItem> items = new ArrayList<>();

    public void registerAll(ConfigurationSection configurationSection) {
        items.clear();

        configurationSection.getKeys(false).forEach(key -> {
            ConfigurationSection itemSection = configurationSection.getConfigurationSection(key);
            int customModelData = itemSection.getInt("custom-model-data");
            double value = itemSection.getDouble("value");

            add(new ValuableItem(key, customModelData, value));

            if (Banco.get().getConfig().getSettings().isDebug())
                logger.info(key + " (" + customModelData + "): " + value);
        });
    }

    public void add(ValuableItem item) { items.add(item); }

    public void remove(ValuableItem item) { items.remove(item); }

    //public void register(String materialName, double value) { valuesMap.put(materialName, value); }

    //public void unregister(String materialName) { valuesMap.remove(materialName); }

    public void clear() { items.clear(); }

    //public Map<String, Double> values() { return items; }

    public List<ValuableItem> get() { return items; }

    public ValuableItem get(String materialName) {
        for (ValuableItem item : get()) {
            if (item.getMaterialName().equalsIgnoreCase(materialName))
                return item;
        }

        return null;
    }

    public int customModelData(String materialName) {
        for (ValuableItem item : get()) {
            if (!item.getMaterialName().equals(materialName))
                continue;
            return item.getCustomModelData();
        }

        return 0;
    }

    public double value(String materialName, int customModelData) { return value(materialName, customModelData, 1); }

    public double value(String materialName, int customModelData, int amount) {
        for (ValuableItem item : get()) {
            if (item.getMaterialName().equals(materialName) && item.getCustomModelData() == customModelData)
                return item.getValue() * amount;
        }

        return 0;
    }

    public Map<String, Double> values() {
        Map<String, Double> map = new LinkedHashMap<>();
        get().forEach(item -> map.put(item.getMaterialName(), item.getValue()));
        return map;
    }

    //public double value(String materialName) { return value(materialName, 1); }

    /*
    public double value(String materialName, int amount) {
        for (Map.Entry<String, Double> entry : valuesMap.entrySet()) {
            if (!materialName.equals(entry.getKey())) continue;
            return entry.getValue() * amount;
        }

        return 0;
    }

     */

}
