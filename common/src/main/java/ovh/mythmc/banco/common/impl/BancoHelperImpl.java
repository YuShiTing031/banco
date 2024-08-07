package ovh.mythmc.banco.common.impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ovh.mythmc.banco.api.Banco;
import ovh.mythmc.banco.api.economy.BancoHelper;
import ovh.mythmc.banco.api.economy.BancoHelperSupplier;
import ovh.mythmc.banco.api.economy.BancoItem;
import ovh.mythmc.banco.api.economy.accounts.Account;
import ovh.mythmc.banco.common.util.MathUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BancoHelperImpl implements BancoHelper {

    public BancoHelperImpl() {
        BancoHelperSupplier.set(this);
    }

    @Override
    public final BigDecimal add(UUID uuid, BigDecimal amount) {
        Player player = Bukkit.getOfflinePlayer(uuid).getPlayer();
        if (player == null)
            return BigDecimal.valueOf(0);

        BigDecimal amountGiven = BigDecimal.valueOf(0);

        for (ItemStack item : convertAmountToItems(amount)) {
            amountGiven = amountGiven.add(MathUtil.value(item));

            if (!player.getInventory().addItem(item).isEmpty())
                player.getWorld().dropItemNaturally(player.getLocation(), item);
           }

        return amount.subtract(amountGiven);
    }

    // Todo: look for less valuable items first
    @Override
    public BigDecimal remove(UUID uuid, BigDecimal amount) {
        Player player = Bukkit.getOfflinePlayer(uuid).getPlayer();
        if (player == null)
            return BigDecimal.valueOf(0);

        if (Banco.get().getSettings().get().getCurrency().isCountEnderChest()) {
            BigDecimal remainingAmount = removeFromInventory(player.getEnderChest().getContents(), uuid, amount);

            if (remainingAmount.compareTo(BigDecimal.valueOf(0)) > 0)
                return removeFromInventory(player.getInventory().getContents(), uuid, remainingAmount);
        }

        return removeFromInventory(player.getInventory().getContents(), uuid, amount);
    }

    private BigDecimal removeFromInventory(ItemStack[] inventory, UUID uuid, BigDecimal amount) {
        for (ItemStack item : inventory) {
            if (item == null) continue;
            if (amount.compareTo(BigDecimal.valueOf(0)) < 0.01) continue;

            BigDecimal value = MathUtil.value(item);

            if (value.compareTo(BigDecimal.valueOf(0)) > 0) {
                item.setAmount(0);
                BigDecimal added = BigDecimal.valueOf(0);
                if (value.compareTo(amount) > 0) {
                    added = value.subtract(amount);
                    Account account = Banco.get().getAccountManager().get(uuid);
                    Banco.get().getAccountManager().set(account, account.amount().add(added));
                }

                amount = amount.subtract(value.subtract(added));
            }
        }

        return amount;
    }

    @Override
    public boolean isOnline(UUID uuid) {
        return Bukkit.getOfflinePlayer(uuid).isOnline();
    }

    @Override
    public BigDecimal getInventoryValue(UUID uuid) {
        BigDecimal value = BigDecimal.valueOf(0);

        for (ItemStack item : Objects.requireNonNull(Bukkit.getPlayer(uuid)).getInventory()) {
            if (item != null)
                value = value.add(MathUtil.value(item));
        }

        if (Banco.get().getSettings().get().getCurrency().isCountEnderChest()) {
            for (ItemStack item : Objects.requireNonNull(Bukkit.getPlayer(uuid)).getEnderChest()) {
                if (item != null)
                    value = value.add(MathUtil.value(item));
            }
        }

        return value;
    }

    public List<ItemStack> convertAmountToItems(BigDecimal amount) {
        List<ItemStack> items = new ArrayList<>();

        for (BancoItem item : Banco.get().getEconomyManager().get()) {
            int itemAmount = amount.divide(item.value(), RoundingMode.FLOOR).intValue();

            if (itemAmount < 1)
                continue;

            ItemStack itemStack = new ItemStack(Material.getMaterial(item.name()), itemAmount);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(item.displayName());
            itemMeta.setLore(item.lore());
            itemMeta.setCustomModelData(item.customModelData());
            itemStack.setItemMeta(itemMeta);

            amount = amount.subtract(item.value().multiply(BigDecimal.valueOf(itemAmount)));
            items.add(itemStack);
        }

        return items;
    }
}
