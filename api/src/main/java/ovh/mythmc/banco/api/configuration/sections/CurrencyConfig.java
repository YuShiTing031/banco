package ovh.mythmc.banco.api.configuration.sections;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ovh.mythmc.banco.api.economy.BancoItem;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@Getter
public class CurrencyConfig {

    @Comment("Currency name in singular")
    private @NotNull String nameSingular = "Dollar";

    @Comment("Currency name in plural")
    private @NotNull String namePlural = "Dollars";

    @Comment("Currency symbol")
    private @NotNull String symbol = "$";

    @Comment("How money should be displayed (default = 1,451.34)")
    private @NotNull String format = "#,###.##";

    @Comment("Whether to prevent valuable items from dropping when killing mobs or not")
    private boolean removeDrops = true;

    @Comment("Whether to count items stored in a player's ender chest or not")
    private boolean countEnderChest = true;

    @Comment({"Configure items and their respective value", "Please, put less valuable items first. You can use '§' to format text"})
    private List<BancoItem> items = List.of(
            new BancoItem("COPPER_INGOT", "§eCent", List.of("This is a simple setup example!", "You can use §bcolours §rto format text", " ", "§dCustom model data §ris also supported!"), 1009, BigDecimal.valueOf(0.1)),
            new BancoItem("GOLD_INGOT", null, null, null, BigDecimal.valueOf(1)),
            new BancoItem("GOLD_BLOCK", null, null, null, BigDecimal.valueOf(9))
    );

}
