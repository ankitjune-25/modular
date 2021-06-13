package com.ankit.modular.repository;

import com.ankit.modular.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepostory extends JpaRepository<Balance, Integer> {
}
