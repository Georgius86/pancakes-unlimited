package croz.zadatak.pancakesunlimited.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    @Enumerated(EnumType.STRING) // specifies that the value of this field will be persisted as a string
    private IngredientCategory category;

    // constructors, getters and setters

    protected Ingredient() {
        // a no-argument constructor is required by JPA
    }
    // constructor with arguments
    public Ingredient(java.lang.Long id, java.lang.String name, BigDecimal price, IngredientCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // getters and setters for the private fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public IngredientCategory getCategory() {
        return category;
    }

    public void setCategory(IngredientCategory category) {
        this.category = category;
    }
}
