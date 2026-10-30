
package com.greenroots.nursery;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@CrossOrigin(origins = "http://localhost:5173")
public class PlantController {

    private final PlantRepository plantRepository;

    public PlantController(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    // Get all plants
    @GetMapping
    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    // Get a plant by ID
    @GetMapping("/{id}")
    public Plant getPlantById(@PathVariable Long id) {
        return plantRepository.findById(id)
                .orElseThrow(() ->
                        new PlantNotFoundException("Plant not found with ID: " + id));
    }

    // Filter plants by category
    @GetMapping("/category/{category}")
    public List<Plant> getPlantsByCategory(@PathVariable String category) {
        return plantRepository.findByCategoryIgnoreCase(category);
    }

    // Search plants by name
    @GetMapping("/search")
    public List<Plant> searchPlants(@RequestParam String name) {
        return plantRepository.findByNameContainingIgnoreCase(name);
    }

    // Add a plant
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Plant addPlant(@Valid @RequestBody Plant plant) {
        plant.setId(null);
        return plantRepository.save(plant);
    }

    // Update a plant
    @PutMapping("/{id}")
    public Plant updatePlant(@PathVariable Long id,
                             @Valid @RequestBody Plant updatedPlant) {
        Plant existing = plantRepository.findById(id)
                .orElseThrow(() ->
                        new PlantNotFoundException("Plant not found with ID: " + id));

        existing.setName(updatedPlant.getName());
        existing.setCategory(updatedPlant.getCategory());
        existing.setPrice(updatedPlant.getPrice());
        existing.setQuantity(updatedPlant.getQuantity());
        existing.setDescription(updatedPlant.getDescription());
        existing.setImageUrl(updatedPlant.getImageUrl());

        return plantRepository.save(existing);
    }

    // Delete a plant
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlant(@PathVariable Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() ->
                        new PlantNotFoundException("Plant not found with ID: " + id));

        plantRepository.delete(plant);
    }
}

