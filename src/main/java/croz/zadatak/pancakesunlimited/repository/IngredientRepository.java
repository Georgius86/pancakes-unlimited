package croz.zadatak.pancakesunlimited.repository;

import croz.zadatak.pancakesunlimited.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
