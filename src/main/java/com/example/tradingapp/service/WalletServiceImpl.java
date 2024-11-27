package com.example.tradingapp.service;

import com.example.tradingapp.Repository.WalletRepository;
import com.example.tradingapp.domain.OrderType;
import com.example.tradingapp.model.Order;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.dnd.DragSourceMotionListener;
import java.math.BigDecimal;
import java.util.Optional;

@Service

public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findByUserId(user.getId());
        if(wallet == null) {
            wallet = new Wallet();
            wallet.setUser(user);
        }

        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));
        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findById(Long id) throws Exception {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent())
            return wallet.get();
        throw new Exception("Wallet not found");

    }

    @Override
    public Wallet walletToUserTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
        Wallet senderWallet  = getUserWallet(sender);
        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new Exception("Unsufficient Balance");
        }
        BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(senderBalance);
        walletRepository.save(senderWallet);
        BigDecimal receiverBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));
        receiverWallet.setBalance(receiverBalance);
         walletRepository.save(receiverWallet);
         return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) {
        Wallet wallet = getUserWallet(user);
        if(order.getOrderType().equals(OrderType.BUY)){
            BigDecimal balance = wallet.getBalance().subtract(order.getPrice());
            if(balance.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            wallet.setBalance(balance);
            return walletRepository.save(wallet);
        } else {
            BigDecimal balance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(balance);
            return walletRepository.save(wallet);
        }
    }
}
