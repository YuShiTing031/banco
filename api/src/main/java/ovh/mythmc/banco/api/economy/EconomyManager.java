package ovh.mythmc.banco.api.economy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ovh.mythmc.banco.api.Banco;
import ovh.mythmc.banco.api.logger.LoggerWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private static final List<BancoItem> items = new ArrayList<>();

    public void registerAll(List<BancoItem> bancoItemList) {
        clear();

        bancoItemList.forEach(item -> {
            items.add(item);

            if (Banco.get().getSettings().get().isDebug())
                logger.info("{} ({}) with CustomModelData {}: {}",
                        item.name(),
                        item.displayName(),
                        item.customModelData(),
                        item.value()
                );
        });
    }

    public void add(BancoItem item) {
        items.add(item);
    }

    public void remove(BancoItem item) { items.remove(item); }

    public List<BancoItem> get() { return List.copyOf(items); }

    public BancoItem get(String materialName, String displayName, int customModelData) {
        for (BancoItem item : items) {
            if (Objects.equals(item.name(), materialName) && Objects.equals(item.displayName(), displayName) && Objects.equals(item.customModelData(), customModelData))
                return item;
        }

        return null;
    }

    public boolean exists(BancoItem item) { return items.contains(item); }

    public boolean exists(String materialName, String displayName, int customModelData) {
        return get(materialName, displayName, customModelData) != null;
    }

    public void clear() { items.clear(); }

}
