package croz.zadatak.pancakesunlimited.service;

import croz.zadatak.pancakesunlimited.domain.Ingredient;
import croz.zadatak.pancakesunlimited.domain.IngredientCategory;
import croz.zadatak.pancakesunlimited.domain.Pancake;
import croz.zadatak.pancakesunlimited.domain.Order;
import croz.zadatak.pancakesunlimited.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;

    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        // validate each pancake in the order
        for (Pancake pancake : order.getPancakes()) {
            if (!isValidPancake(pancake)) {
                throw new IllegalArgumentException("Invalid pancake: " + pancake.getName() + ". Please add at least base and one topping!");
            }
        }

        // save the order if all pancakes are valid
        return orderRepository.save(order);
    }

    private boolean isValidPancake(Pancake pancake) {
        // a valid pancake must have exactly one base ingredient and at least one topping ingredient
        List<Ingredient> baseIngredients = pancake.getIngredients().stream()
                .filter(ingredient -> ingredient.getCategory() == IngredientCategory.BASE)
                .toList();

        List<Ingredient> toppingIngredients = pancake.getIngredients().stream()
                .filter(ingredient -> ingredient.getCategory() == IngredientCategory.TOPPING)
                .toList();

        return baseIngredients.size() == 1 && !toppingIngredients.isEmpty();
    }

    public Optional<Order> updateOrder(Long id, Order order) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order updatedOrder = existingOrder.get();
            updatedOrder.setOrderNumber(order.getOrderNumber());
            updatedOrder.setPancakes(order.getPancakes());
            updatedOrder.setDescription(order.getDescription());
            updatedOrder.setOrderTime(order.getOrderTime());
            return Optional.of(orderRepository.save(updatedOrder));
        }
        return Optional.empty();
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
