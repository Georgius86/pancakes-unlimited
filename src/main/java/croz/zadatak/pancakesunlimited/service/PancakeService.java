/**
 This class implements the service layer for Pancake related operations
 */
package croz.zadatak.pancakesunlimited.service;

import croz.zadatak.pancakesunlimited.domain.Ingredient;
import croz.zadatak.pancakesunlimited.domain.Pancake;
import croz.zadatak.pancakesunlimited.repository.IngredientRepository;
import croz.zadatak.pancakesunlimited.repository.PancakeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PancakeService {

    private final PancakeRepository pancakeRepository;
    private final IngredientRepository ingredientRepository;

    /**
     * Constructor for the PancakeService class
     * @param pancakeRepository The repository for Ingredient entities
     * @param ingredientRepository The repository for Ingredient entities
     */
    public PancakeService(PancakeRepository pancakeRepository, IngredientRepository ingredientRepository) {
        this.pancakeRepository = pancakeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Get a list of all the pancakes in the database
     * @return List of Pancake objects
     */
    public List<Pancake> getAllPancakes() {
        return pancakeRepository.findAll();
    }

    /**
     * Get a single pancake from the database by its ID
     * @param id The ID of the pancake to retrieve
     * @return Optional of Pancake object, which may or may not be present depending on whether a pancake with the specified ID exists
     */
    public Optional<Pancake> getPancakeById(Long id) {
        return pancakeRepository.findById(id);
    }

    /**
     * Create a new pancake and save it to the database
     * @param pancake The Pancake object to create and save
     * @return The Pancake object that was created and saved
     */
    public Pancake createPancake(Pancake pancake) {
        // check if ingredients already exist in the database
        Set<Ingredient> ingredients = new HashSet<>();
        for (Ingredient ingredient : pancake.getIngredients()) {
            if (ingredient.getId() != null) {
                Optional<Ingredient> existingIngredient = ingredientRepository.findById(ingredient.getId());
                existingIngredient.ifPresent(ingredients::add);
            }
            else {
                ingredients.add(ingredient);
            }
        }
        pancake.setIngredients(ingredients);
        return pancakeRepository.save(pancake);
    }

    /**
     * Update an existing pancake in the database with new values
     * @param id The ID of the pancake to update
     * @param pancake The Pancake object containing the new values
     * @return Optional of Pancake object, which may or may not be present depending on whether a pancake with the specified ID exists
     */
    public Optional<Pancake> updatePancake(Long id, Pancake pancake) {
        Optional<Pancake> existingPancake = pancakeRepository.findById(id);
        if (existingPancake.isPresent()) {
            // update the existing pancake with new values
            Pancake updatedPancake = existingPancake.get();
            updatedPancake.setName(pancake.getName());
            updatedPancake.setIngredients(pancake.getIngredients());
            return Optional.of(pancakeRepository.save(updatedPancake));
        }
        return Optional.empty();
    }

    /**
     * Delete a pancake from the database by its ID
     * @param id The ID of the pancake to delete
     */
    public void deletePancake(Long id) {
        pancakeRepository.deleteById(id);
    }
}