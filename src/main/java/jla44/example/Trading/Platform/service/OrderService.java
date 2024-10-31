package jla44.example.Trading.Platform.service;

import jla44.example.Trading.Platform.domain.OrderType;
import jla44.example.Trading.Platform.model.Coin;
import jla44.example.Trading.Platform.model.Order;
import jla44.example.Trading.Platform.model.OrderItem;
import jla44.example.Trading.Platform.model.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
