package com.Project.RMSSpring.restcontroller;

import com.Project.RMSSpring.entity.Food;
import com.Project.RMSSpring.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/food")
@CrossOrigin("*")
public class FoodRestController {

    @Autowired
    private FoodService foodService;

//    @PostMapping("/save")
//    public void saveFood(@RequestBody Food food) {
//        foodService.saveFood(food);
//    }

    @PostMapping("/save")
    public ResponseEntity<String> saveFood(
            @RequestPart("food") Food food,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            foodService.saveFood(food, imageFile);
            return new ResponseEntity<>("Food added successfully with image.", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to add food: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @GetMapping("/view ")
//    public List<Food> getAllFood() {
//        return foodService.getAllFood();
//    }

    @GetMapping("/view")
    public ResponseEntity<List<Food>> getAllUsers() {
        List<Food> foods = foodService.getAllFood();
        return ResponseEntity.ok(foods);
    }

    @DeleteMapping("/delete/{id}")
    public void DeleteById(@PathVariable int id) {
        foodService.deleteFoodById(id);
    }

    @PutMapping("/update/{id}")
    public void UpdateFood(@RequestBody Food food,@PathVariable int id) {
        foodService.updateFood(food, id);
    }
}
