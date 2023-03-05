package croz.zadatak.pancakesunlimited.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredient_category")
public enum IngredientCategory {
    BASE(1, "Base"),
    FILLING(2, "Filling"),
    TOPPING(3, "Topping"),
    FRUIT(4, "Fruit");
    @Id
    private  int id;
    private  String name;


    // constructor with arguments
    IngredientCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    IngredientCategory() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
