package jla44.example.Trading.Platform.controller;


import jla44.example.Trading.Platform.domain.WalletTransactionType;
import jla44.example.Trading.Platform.model.User;
import jla44.example.Trading.Platform.model.Wallet;
import jla44.example.Trading.Platform.model.Withdrawal;
import jla44.example.Trading.Platform.service.UserService;
import jla44.example.Trading.Platform.service.WalletService;
import jla44.example.Trading.Platform.service.WithdrawalService;
import jla44.example.Trading.Platform.utils.WalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {
    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;

    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount,user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());

//        WalletTransaction walletTransaction = walletTransactionService.createTransaction(
//                userWallet,
//                WalletTransactionType.WITHDRAWAL,null,
//                "bank account withdrawal",
//                withdrawal.getAmount()
//        );
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @PatchMapping("api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user =userService.findUserProfileByJwt(jwt);

        Withdrawal withdrawal=withdrawalService.proceedWithWithdrawal(id,accept);

        Wallet userWallet = walletService.getUserWallet(user);
        if(!accept){
            walletService.addBalance(userWallet, withdrawal.getAmount());
        }
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        List<Withdrawal> withdrawals = withdrawalService.getUsersWithdrawalHistory(user);

        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
    }

}
