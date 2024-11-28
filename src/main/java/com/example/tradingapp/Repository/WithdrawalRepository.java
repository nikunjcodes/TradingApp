package com.example.tradingapp.Repository;

import com.example.tradingapp.model.Withdrawal;
import lombok.With;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface WithdrawalRepository extends JpaRepository<Withdrawal , Long> {
    List<Withdrawal> findByUserId(Long userId);
}
