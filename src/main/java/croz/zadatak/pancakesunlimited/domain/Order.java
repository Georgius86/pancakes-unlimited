/**
 Represents an order for pancakes. An order consists of a list of pancakes and a description,
 and has an associated order number and order time. Provides functionality for calculating the
 total price of the order, including any applicable discounts.
 */
package croz.zadatak.pancakesunlimited.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String orderNumber;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "order_pancakes",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "pancake_id"))
     /*JPA annotation used to define a many-to-many relationship between two entities.
    In this case, Order entity has many-to-many relationship with Pancake entity,
    which means that one pancake can have many orders and an order can have many pancakes.
     */
    private List<Pancake> pancakes = new ArrayList<>();


    private String description;


    private LocalDateTime orderTime;

    // constructors, getters and setters

    protected Order() {
        // a no-argument constructor is required by JPA
    }
    // constructor with arguments
    public Order(String orderNumber, List<Pancake> pancakes, String description, LocalDateTime orderTime) {
        this.orderNumber = orderNumber;
        this.pancakes = pancakes;
        this.description = description;
        this.orderTime = orderTime;
    }
    /**

     Calculates the total price of the order including discounts.

     @return the total price of the order.
     */
    public BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal individualPancakeDiscount = BigDecimal.ZERO;
        BigDecimal orderDiscount = BigDecimal.ZERO;
        boolean hasHealthyPancake = false;

        for (Pancake pancake : pancakes) {
            BigDecimal pancakePrice = pancake.calculatePrice();
            totalPrice = totalPrice.add(pancakePrice);

            if (pancake.isHealthy()) {
                BigDecimal pancakeDiscountAmount = pancakePrice.multiply(BigDecimal.valueOf(0.15));
                individualPancakeDiscount = individualPancakeDiscount.add(pancakeDiscountAmount);
                hasHealthyPancake = true;
            }
        }

        if (totalPrice.compareTo(BigDecimal.valueOf(30)) >= 0) {
            orderDiscount = totalPrice.multiply(BigDecimal.valueOf(0.1));
        } else if (totalPrice.compareTo(BigDecimal.valueOf(15)) >= 0) {
            orderDiscount = totalPrice.multiply(BigDecimal.valueOf(0.05));
        }

        BigDecimal appliedDiscount = BigDecimal.ZERO;
        if (hasHealthyPancake) {
            BigDecimal healthyPancakeDiscount = individualPancakeDiscount;
            if (healthyPancakeDiscount.compareTo(orderDiscount) > 0) {
                appliedDiscount = healthyPancakeDiscount;
            }
        } else {
            appliedDiscount = orderDiscount;
        }

        totalPrice = totalPrice.subtract(appliedDiscount);

        return totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<Pancake> getPancakes() {
        return pancakes;
    }

    public void setPancakes(List<Pancake> pancakes) {
        this.pancakes = pancakes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

}
