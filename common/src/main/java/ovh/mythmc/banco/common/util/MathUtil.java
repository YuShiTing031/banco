package ovh.mythmc.banco.common.util;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ovh.mythmc.banco.api.Banco;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public final class MathUtil {

    public static <K, V extends Comparable<? super V>> LinkedHashMap<K, V> sortByValue(final @NotNull Map<K, V> map) {
        return map
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public static boolean isDouble(final @NotNull String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    public static BigDecimal value(ItemStack item) {
        String materialName = item.getType().name();
        String displayName = null;
        int customModelData = -1;
        if (item.hasItemMeta()) {
            displayName = item.getItemMeta().getDisplayName();
            if (item.getItemMeta().hasCustomModelData())
                customModelData = item.getItemMeta().getCustomModelData();
        }

        if (Banco.get().getEconomyManager().exists(materialName, displayName, customModelData))
            return Banco.get().getEconomyManager().get(materialName, displayName, customModelData).value().multiply(BigDecimal.valueOf(item.getAmount()));

        return BigDecimal.valueOf(0);
    }

}
