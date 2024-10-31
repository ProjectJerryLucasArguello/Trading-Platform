package jla44.example.Trading.Platform.utils;

import jla44.example.Trading.Platform.domain.OrderType;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
