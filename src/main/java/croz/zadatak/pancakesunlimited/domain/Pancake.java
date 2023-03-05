/**
  The Pancake class represents a pancake in the Pancakes Unlimited domain. Each pancake has a unique identifier,
  a name and a set of ingredients. The class provides methods for calculating the price of the pancake and for
  determining whether the pancake is healthy.
 */
package croz.zadatak.pancakesunlimited.domain;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pancakes")
public class Pancake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "pancake_ingredients",
            joinColumns = @JoinColumn(name = "pancake_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    /*JPA annotation used to define a many-to-many relationship between two entities.
    In this case, Pancake entity has a many-to-many relationship with Ingredient entity,
    which means that a pancake can have many ingredients and an ingredient can be used in many pancakes.
     */
    private Set<Ingredient> ingredients = new HashSet<>();

    // constructors, getters and setters


    protected Pancake() {
        // a no-argument constructor is required by JPA
    }
    // constructor with arguments
    public Pancake(java.lang.Long id, java.lang.String name, Set<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }
    /**
     * Calculates the price of the pancake based on the sum of the prices of its ingredients.
     * @return the price of the pancake
     */
    public BigDecimal calculatePrice() {
        BigDecimal pancakePrice = BigDecimal.ZERO;

        for (Ingredient ingredient : ingredients) {
            pancakePrice = pancakePrice.add(ingredient.getPrice());
        }

        return pancakePrice;
    }
    /**
     * Determines whether the pancake is healthy. A pancake is considered healthy if it has more than 75%
     * healthy ingredients.
     * @return true if the pancake is healthy, false otherwise
     */
    public boolean isHealthy() {
        int numHealthyIngredients = 0;

        for (Ingredient ingredient : ingredients) {
            if (ingredient.isHealthy()) {
                numHealthyIngredients++;
            }
        }

        double healthyIngredientRatio = (double) numHealthyIngredients / ingredients.size();
        return healthyIngredientRatio >= 0.75;
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

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
