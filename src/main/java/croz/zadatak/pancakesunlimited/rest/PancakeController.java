package croz.zadatak.pancakesunlimited.rest;

import croz.zadatak.pancakesunlimited.domain.Pancake;
import croz.zadatak.pancakesunlimited.service.PancakeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pancakes")
public class PancakeController {

    private final PancakeService pancakeService;

    public PancakeController(PancakeService pancakeService) {
        this.pancakeService = pancakeService;
    }

    @GetMapping
    public ResponseEntity<List<Pancake>> getAllPancakes() {
        List<Pancake> pancakes = pancakeService.getAllPancakes();
        if (pancakes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pancakes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pancake> getPancakeById(@PathVariable Long id) {
        Optional<Pancake> optionalPancake = pancakeService.getPancakeById(id);
        return optionalPancake.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pancake> createPancake(@RequestBody Pancake pancake) {
        Pancake newPancake = pancakeService.createPancake(pancake);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPancake);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pancake> updatePancake(@PathVariable Long id, @RequestBody Pancake pancake) {
        Optional<Pancake> optionalPancake = pancakeService.updatePancake(id, pancake);
        return optionalPancake.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePancake(@PathVariable Long id) {
        pancakeService.deletePancake(id);
        return ResponseEntity.noContent().build();
    }
}
