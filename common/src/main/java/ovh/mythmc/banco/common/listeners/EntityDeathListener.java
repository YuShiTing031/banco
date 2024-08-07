package ovh.mythmc.banco.common.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import ovh.mythmc.banco.common.util.MathUtil;

import java.math.BigDecimal;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER))
            return;

        event.getDrops().removeIf(item ->
            MathUtil.value(item).compareTo(BigDecimal.valueOf(0)) > 0
        );
    }

}
