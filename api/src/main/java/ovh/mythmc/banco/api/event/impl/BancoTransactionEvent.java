package ovh.mythmc.banco.api.event.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import ovh.mythmc.banco.api.economy.accounts.Account;
import ovh.mythmc.banco.api.event.BancoEvent;

import java.math.BigDecimal;

@Getter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class BancoTransactionEvent implements BancoEvent {
    private final Account account;
    private final BigDecimal amount;
}
