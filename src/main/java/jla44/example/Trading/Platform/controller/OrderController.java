package jla44.example.Trading.Platform.controller;

import jla44.example.Trading.Platform.domain.OrderType;
import jla44.example.Trading.Platform.model.Coin;
import jla44.example.Trading.Platform.model.Order;
import jla44.example.Trading.Platform.model.User;
import jla44.example.Trading.Platform.service.CoinService;
import jla44.example.Trading.Platform.service.OrderService;
import jla44.example.Trading.Platform.service.UserService;
import jla44.example.Trading.Platform.service.WalletService;
import jla44.example.Trading.Platform.utils.CreateOrderRequest;
import jla44.example.Trading.Platform.utils.WalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;


    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest request
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(request.getCoinId());

        Order order = orderService.processOrder(coin, request.getQuantity(), request.getOrderType(), user);

        return ResponseEntity.ok(order);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable Long orderId
    ) throws Exception {

        User user = userService.findUserProfileByJwt(jwtToken);

        Order order = orderService.getOrderById(orderId);
        if(order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);
        } else{
            throw new Exception("you don't have access....");
        }
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersForUser(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) OrderType order_type,
            @RequestParam(required = false) String asset_symbol
    ) throws Exception {


        Long userId = userService.findUserProfileByJwt(jwt).getId();

        List<Order> userOrders = orderService.getAllOrdersOfUser(userId, order_type, asset_symbol);
        return ResponseEntity.ok(userOrders);
    }

}