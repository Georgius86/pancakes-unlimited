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

    public PancakeService(PancakeRepository pancakeRepository, IngredientRepository ingredientRepository) {
        this.pancakeRepository = pancakeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Pancake> getAllPancakes() {
        return pancakeRepository.findAll();
    }

    public Optional<Pancake> getPancakeById(Long id) {
        return pancakeRepository.findById(id);
    }

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

    public void deletePancake(Long id) {
        pancakeRepository.deleteById(id);
    }
}