package jla44.example.Trading.Platform.utils;

import jakarta.persistence.*;
import jla44.example.Trading.Platform.domain.WalletTransactionType;
import jla44.example.Trading.Platform.model.Wallet;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Wallet wallet;

    private WalletTransactionType type;

    private LocalDate date;

    private String transferId;

    private String purpose;

    private Long amount;
}
