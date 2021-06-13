package com.ankit.modular.entity;

import com.ankit.modular.enumuration.Currency;
import com.ankit.modular.enumuration.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Account account;

    @Column(nullable = false)
    private BigDecimal ammount;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private TransactionType direction;

    @Column(nullable = true)
    private String description;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
