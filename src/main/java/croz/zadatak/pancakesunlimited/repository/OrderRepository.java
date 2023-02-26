package croz.zadatak.pancakesunlimited.repository;

import croz.zadatak.pancakesunlimited.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}