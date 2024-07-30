package ovh.mythmc.banco.api.economy;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter(AccessLevel.PUBLIC)
public class ValuableItem {

    private final String materialName;
    private final int customModelData;
    private final double value;

    public ValuableItem(final @NotNull String materialName,
                        final int customModelData,
                        final double value) {
        this.materialName = materialName;
        this.customModelData = customModelData;
        this.value = value;
    }
}
