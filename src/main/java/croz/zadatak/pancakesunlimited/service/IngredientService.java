/**
 This class implements the service layer for Ingredient related operations
 */
package croz.zadatak.pancakesunlimited.service;

import croz.zadatak.pancakesunlimited.domain.Ingredient;
import croz.zadatak.pancakesunlimited.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    /**
     * Constructor for the IngredientService class
     * @param ingredientRepository The repository for Ingredient entities
     */
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }
    /**
     * Returns a list of all Ingredient entities
     * @return List of Ingredient entities
     */
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }
    /**
     * Returns the Ingredient entity with the specified id
     * @param id The id of the Ingredient entity to retrieve
     * @return Optional containing the Ingredient entity if it exists, empty Optional otherwise
     */
    public Optional<Ingredient> getIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }
    /**
     * Creates a new Ingredient entity in the repository
     * @param ingredient The Ingredient entity to be created
     * @return The created Ingredient entity
     */
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
    /**
     * Updates an existing Ingredient entity in the repository with new values
     * @param id The id of the Ingredient entity to be updated
     * @param ingredient The Ingredient entity with new values
     * @return Optional containing the updated Ingredient entity if it exists, empty Optional otherwise
     */
    public Optional<Ingredient> updateIngredient(Long id, Ingredient ingredient) {
        Optional<Ingredient> existingIngredient = ingredientRepository.findById(id);
        if (existingIngredient.isPresent()) {
            // update the existing ingredient with new values
            Ingredient updatedIngredient = existingIngredient.get();
            updatedIngredient.setName(ingredient.getName());
            updatedIngredient.setPrice(ingredient.getPrice());
            updatedIngredient.setCategory(ingredient.getCategory());
            return Optional.of(ingredientRepository.save(updatedIngredient));
        }
        return Optional.empty();
    }
    /**
     * Deletes the Ingredient entity with the specified id from the repository
     * @param id The id of the Ingredient entity to be deleted
     */
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}