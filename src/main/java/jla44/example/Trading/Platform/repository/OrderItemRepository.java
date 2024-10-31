package jla44.example.Trading.Platform.repository;

import jla44.example.Trading.Platform.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
