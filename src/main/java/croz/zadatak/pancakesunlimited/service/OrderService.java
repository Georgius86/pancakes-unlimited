/**
 This class implements the service layer for Order related operations
 */
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

    /**
     * Constructor for the OrderService class
     * @param orderRepository The repository for Order entities
     */
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;

    }

    /**
     * Returns a list of all Ingredient entities
     * @return List of Order entities
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Returns the Order with the specified ID, if it exists.
     * @param id the ID of the Order to retrieve.
     * @return Optional containing the Order entity if it exists, empty Optional otherwise
     */
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Creates a new Order entity in the repository
     * @param order The Order entity to be created
     * @return The created Order entity
     * @throws IllegalArgumentException if any of the Pancakes in the Order are invalid.
     */
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

    /**
     * Checks if the specified Pancake is valid. A valid Pancake must have exactly one base Ingredient and at least one
     * topping Ingredient.
     * The method first uses the stream method on the pancake's list of ingredients,
     * which allows for easy filtering and processing of the ingredients.
     * Two separate lists are created: baseIngredients and toppingIngredients,
     * which are filtered based on their respective ingredient categories (BASE or TOPPING).
     * @param pancake the Pancake to validate.
     * @return true if the Pancake is valid, false otherwise.
     */
    private boolean isValidPancake(Pancake pancake) {

        List<Ingredient> baseIngredients = pancake.getIngredients().stream()
                .filter(ingredient -> ingredient.getCategory() == IngredientCategory.BASE)
                .toList();

        List<Ingredient> toppingIngredients = pancake.getIngredients().stream()
                .filter(ingredient -> ingredient.getCategory() == IngredientCategory.TOPPING)
                .toList();
       // a valid pancake must have exactly one base ingredient and at least one topping ingredient
        return baseIngredients.size() == 1 && !toppingIngredients.isEmpty();
    }

    /**
     * Updates the Order with the specified ID with the specified data and saves the changes to the database.
     *
     * @param id    the ID of the Order to update.
     * @param order the updated data for the Order.
     * @return an Optional containing the updated Order, if it exists.
     */
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

    /**
     * Delete a order from the database by its ID
     * @param id The ID of the order to delete
     */
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
