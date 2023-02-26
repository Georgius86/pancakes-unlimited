package croz.zadatak.pancakesunlimited.domain;
import jakarta.persistence.*;

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
