package jla44.example.Trading.Platform.controller;

import jla44.example.Trading.Platform.model.Order;
import jla44.example.Trading.Platform.model.User;
import jla44.example.Trading.Platform.model.Wallet;
import jla44.example.Trading.Platform.service.OrderService;
import jla44.example.Trading.Platform.service.UserService;
import jla44.example.Trading.Platform.service.WalletService;
import jla44.example.Trading.Platform.utils.WalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet
            (@RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }



    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer
            (@RequestHeader("Authorization") String jwt,
             @PathVariable Long walletId,
            @RequestBody WalletTransaction request)
            throws Exception {
        User senderUser = userService.findUserProfileByJwt(jwt);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransfer(
                senderUser,receiverWallet, request.getAmount()
        );
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> walletToWalletTransfer
            (@RequestHeader("Authorization") String jwt,
             @PathVariable Long orderId
            ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order,user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
}
