package croz.zadatak.pancakesunlimited.service;

import croz.zadatak.pancakesunlimited.domain.Ingredient;
import croz.zadatak.pancakesunlimited.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> getIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Optional<Ingredient> updateIngredient(Long id, Ingredient ingredient) {
        Optional<Ingredient> existingIngredient = ingredientRepository.findById(id);
        if (existingIngredient.isPresent()) {
            // update the existing ingredient with new values
            Ingredient updatedIngredient = existingIngredient.get();
            updatedIngredient.setName(ingredient.getName());
            return Optional.of(ingredientRepository.save(updatedIngredient));
        }
        return Optional.empty();
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}