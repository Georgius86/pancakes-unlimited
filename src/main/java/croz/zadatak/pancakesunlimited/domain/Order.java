package croz.zadatak.pancakesunlimited.domain;

import jakarta.persistence.*;
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
     /*JPA annotation used to define a many-to-many relationship between two entities.
    In this case, Pancake entity has a many-to-many relationship with Order entity,
    which means that a pancake can have many orders and an order can have many pancakes.
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
