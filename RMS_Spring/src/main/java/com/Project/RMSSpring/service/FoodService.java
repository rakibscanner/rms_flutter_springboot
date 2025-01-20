package com.Project.RMSSpring.service;

import com.Project.RMSSpring.entity.Food;
import com.Project.RMSSpring.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @Transactional
    public void saveFood(Food food, MultipartFile imageFile) throws IOException {
        // Save the image if it exists
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFilename = saveImage(imageFile);
            food.setImage(imageFilename); // Set the image filename in the user entity
        }
        // Save the food
        foodRepository.save(food);
    }

    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(imageUploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique filename
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);

        // Save the file
        Files.copy(file.getInputStream(), filePath);

        return filename; // Return the filename for storing in the database
    }

    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }
    public void saveFood(Food food) {
        foodRepository.save(food);
    }
    public void updateFood(Food food, int id) {
        foodRepository.save(food);
    }
    public void deleteFoodById(int id) {
        foodRepository.deleteById(id);
    }
    public Food findFoodById(int id) {
        return foodRepository.findById(id).get();
    }
}