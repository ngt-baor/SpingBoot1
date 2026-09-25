package com.example.buoi1.controller;

import com.example.buoi1.model.Drink;
import com.example.buoi1.repo.DrinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drinks")
public class DrinkController {
    @Autowired
    DrinkRepo drinkRepo;

    @GetMapping
    public List<Drink> getAll() {
        return drinkRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drink> getById(@PathVariable Integer id) {
        return drinkRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Drink> create(@RequestBody Drink drink) {
        return ResponseEntity.status(HttpStatus.CREATED).body(drinkRepo.save(drink));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Drink> update(@PathVariable Integer id, @RequestBody Drink drink) {
        return drinkRepo.findById(id)
                .map(existing -> {
                    existing.setName(drink.getName());
                    existing.setPrice(drink.getPrice());
                    existing.setImage(drink.getImage());
                    existing.setDescription(drink.getDescription());
                    existing.setActive(drink.getActive());
                    existing.setCategory(drink.getCategory());
                    return ResponseEntity.ok(drinkRepo.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!drinkRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        drinkRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
