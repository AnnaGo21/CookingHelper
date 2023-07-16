package com.example.demo.integration;

import com.example.demo.controller.FoodController;
import com.example.demo.dto.FoodDto;
import com.example.demo.dto.FoodDtoRegular;
import com.example.demo.request.SearchRequest;
import com.example.demo.service.FoodService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FoodController.class)
class FoodControllerIntTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodService foodService;

    @BeforeEach
    void setUp() {
        FoodController foodController = new FoodController(foodService);
        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testCreateFood() throws Exception {
        FoodDto foodDto = new FoodDto();
        foodDto.setFoodId(1);
        foodDto.setFoodName("Food 1");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/food/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"foodId\": 1, \"foodName\": \"Food 1\"}"))
                .andExpect(status().isOk());

        Mockito.verify(foodService, Mockito.times(1)).createFood(Mockito.any(FoodDto.class));

    }

    @Test
    void testGetAllFood() throws Exception {
        String foodName1 = "Food 1";
        FoodDto food1 = new FoodDto();
        food1.setFoodId(1);
        food1.setFoodName(foodName1);

        String foodName2 = "Food 2";
        FoodDto food2 = new FoodDto();
        food2.setFoodId(2);
        food2.setFoodName(foodName2);

        List<FoodDto> foodList = List.of(food1, food2);
        when(foodService.getAllFoods()).thenReturn(foodList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/food"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].foodId").value(1))
                .andExpect(jsonPath("$[0].foodName").value("Food 1"))
                .andExpect(jsonPath("$[1].foodId").value(2))
                .andExpect(jsonPath("$[1].foodName").value("Food 2"));
    }

    @Test
    void testDeleteFoodById() throws Exception {
        // Send Get request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/food/delete/{foodId}", 1))
                .andExpect(status().isOk());

        Mockito.verify(foodService, Mockito.times(1)).deleteFood(1);
    }

    @Test
    void getFoodByIngredientId_ShouldReturn_ListOfFoods() throws Exception {
        FoodDto food1 = new FoodDto();
        food1.setFoodId(1);
        food1.setFoodName("Food 1");
        food1.setCalories(100.00);

        FoodDto food2 = new FoodDto();
        food2.setFoodId(2);
        food2.setFoodName("Food 2");
        food2.setCalories(200.00);

        List<FoodDto> foodList = List.of(food1,food2);
        when(foodService.getFoodByIngredientId(anyInt())).thenReturn(foodList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/food/ingredient-id/{ingredientId}", anyInt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].foodId").value(1))
                .andExpect(jsonPath("$[0].foodName").value("Food 1"))
                .andExpect(jsonPath("$[1].foodId").value(2))
                .andExpect(jsonPath("$[1].foodName").value("Food 2"));

        Mockito.verify(foodService, Mockito.times(1)).getFoodByIngredientId(Mockito.anyInt());
    }


    @Test
    void getFoodByRecipeId() throws Exception {
        FoodDto food = new FoodDto();
        food.setFoodId(1);
        food.setFoodName("Food 1");
        food.setCalories(200.0);

        when(foodService.getFoodByRecipeId(anyInt())).thenReturn(food);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/food/recipe-id/{recipeId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.foodId").value(1))
                .andExpect(jsonPath("$.foodName").value("Food 1"));

        Mockito.verify(foodService, Mockito.times(1)).getFoodByRecipeId(Mockito.anyInt());
    }

    @Test
    void getFoodByCalories() throws Exception {
        double minCalories = 150.0;
        String foodName1 = "Food 1";
        FoodDto food1 = new FoodDto();
        food1.setFoodId(1);
        food1.setFoodName(foodName1);

        String foodName2 = "Food 2";
        FoodDto food2 = new FoodDto();
        food2.setFoodId(2);
        food2.setFoodName(foodName2);

        List<FoodDto> foodList = List.of(food1, food2);
        when(foodService.searchFoodsByCalories(eq(minCalories))).thenReturn(foodList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/food/min-calories/{minCalories}", minCalories))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].foodId").value(1))
                .andExpect(jsonPath("$[0].foodName").value("Food 1"))
                .andExpect(jsonPath("$[1].foodId").value(2))
                .andExpect(jsonPath("$[1].foodName").value("Food 2"));
    }

    @Test
    void getFoodByCarbohydrates() throws Exception {
        double minCarbohydrates = 150.0;
        String foodName1 = "Food 1";
        FoodDto food1 = new FoodDto();
        food1.setFoodId(1);
        food1.setFoodName(foodName1);

        String foodName2 = "Food 2";
        FoodDto food2 = new FoodDto();
        food2.setFoodId(2);
        food2.setFoodName(foodName2);

        List<FoodDto> foodList = List.of(food1, food2);
        when(foodService.searchFoodsByCarbohydrates(eq(minCarbohydrates))).thenReturn(foodList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/food/min-carbos/{minCarbohydrates}", minCarbohydrates))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].foodId").value(1))
                .andExpect(jsonPath("$[0].foodName").value("Food 1"))
                .andExpect(jsonPath("$[1].foodId").value(2))
                .andExpect(jsonPath("$[1].foodName").value("Food 2"));
    }

    @Test
    void getFoodByFat() throws Exception {
        double maxFat = 5.0;
        String foodName1 = "Food 1";
        FoodDto food1 = new FoodDto();
        food1.setFoodId(1);
        food1.setFoodName(foodName1);

        String foodName2 = "Food 2";
        FoodDto food2 = new FoodDto();
        food2.setFoodId(2);
        food2.setFoodName(foodName2);

        List<FoodDto> foodList = List.of(food1, food2);
        when(foodService.searchFoodsByFat(eq(maxFat))).thenReturn(foodList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/food/max-fat/{maxFat}", maxFat))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].foodId").value(1))
                .andExpect(jsonPath("$[0].foodName").value("Food 1"))
                .andExpect(jsonPath("$[1].foodId").value(2))
                .andExpect(jsonPath("$[1].foodName").value("Food 2"));
    }

    @Test
    void getFoodByProtein() throws Exception {
        double minProtein = 20.0;
        String foodName1 = "Food 1";
        FoodDto food1 = new FoodDto();
        food1.setFoodId(1);
        food1.setFoodName(foodName1);

        String foodName2 = "Food 2";
        FoodDto food2 = new FoodDto();
        food2.setFoodId(2);
        food2.setFoodName(foodName2);

        List<FoodDto> foodList = List.of(food1, food2);
        when(foodService.searchFoodsByProtein(eq(minProtein))).thenReturn(foodList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/food/min-protein/{minProtein}", minProtein))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].foodId").value(1))
                .andExpect(jsonPath("$[0].foodName").value("Food 1"))
                .andExpect(jsonPath("$[1].foodId").value(2))
                .andExpect(jsonPath("$[1].foodName").value("Food 2"));
    }

    @Test
    void getFoodByName() throws Exception {
        String foodName = "Food 1";
        FoodDto foodDto = new FoodDto();
        foodDto.setFoodId(1);
        foodDto.setFoodName(foodName);
        when(foodService.searchFoodByName(eq(foodName))).thenReturn(foodDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/food/name/{foodName}", foodName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.foodId").value(1))
                .andExpect(jsonPath("$.foodName").value(foodName));
    }

    @Test
    void getFoodByProteinsRangeAndType() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setType("Vegetable");
        searchRequest.setMinProtein(30.0);
        searchRequest.setMaxProtein(60.0);

        FoodDtoRegular food1 = new FoodDtoRegular();
        food1.setFoodId(1);
        food1.setFoodName("Food 1");

        FoodDtoRegular food2 = new FoodDtoRegular();
        food2.setFoodId(2);
        food2.setFoodName("Food 2");

        List<FoodDtoRegular> foodList = List.of(food1, food2);
        when(foodService.searchFoodByProteinsRangeAndType(eq("Vegetable"), eq(30.0), eq(60.0))).thenReturn(foodList);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/food/protein-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"Vegetable\", \"minProteins\": 30.0, \"maxProteins\": 60.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].foodId").value(1))
                .andExpect(jsonPath("$[0].foodName").value("Food 1"))
                .andExpect(jsonPath("$[1].foodId").value(2))
                .andExpect(jsonPath("$[1].foodName").value("Food 2"));
    }

    @Test
    void getFoodByFatRangeAndType() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setType("Meat");
        searchRequest.setMinProtein(10.0);
        searchRequest.setMaxProtein(20.0);

        FoodDtoRegular food1 = new FoodDtoRegular();
        food1.setFoodId(1);
        food1.setFoodName("Food 1");

        FoodDtoRegular food2 = new FoodDtoRegular();
        food2.setFoodId(2);
        food2.setFoodName("Food 2");

        List<FoodDtoRegular> foodList = List.of(food1, food2);
        when(foodService.searchFoodByFatRangeAndType(eq("Meat"), eq(10.0), eq(20.0))).thenReturn(foodList);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/food/fat-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"Meat\", \"minFats\": 10.0, \"maxFats\": 20.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].foodId").value(1))
                .andExpect(jsonPath("$[0].foodName").value("Food 1"))
                .andExpect(jsonPath("$[1].foodId").value(2))
                .andExpect(jsonPath("$[1].foodName").value("Food 2"));
    }

    @Test
    void getFoodByCarbohydratesRangeAndType() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setType("Vegetable");
        searchRequest.setMinProtein(10.0);
        searchRequest.setMaxProtein(80.0);

        FoodDtoRegular food1 = new FoodDtoRegular();
        food1.setFoodId(1);
        food1.setFoodName("Food 1");

        FoodDtoRegular food2 = new FoodDtoRegular();
        food2.setFoodId(2);
        food2.setFoodName("Food 2");

        List<FoodDtoRegular> foodList = List.of(food1, food2);
        when(foodService.searchFoodByCarbohydratesRangeAndType(eq("Vegetable"), eq(10.0), eq(80.0))).thenReturn(foodList);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/food/carbo-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"Vegetable\", \"minCarbohydrates\": 10.0, \"maxCarbohydrates\": 80.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].foodId").value(1))
                .andExpect(jsonPath("$[0].foodName").value("Food 1"))
                .andExpect(jsonPath("$[1].foodId").value(2))
                .andExpect(jsonPath("$[1].foodName").value("Food 2"));
    }

    @Test
    void getFoodByCaloriesRangeAndType() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setType("Vegetable");
        searchRequest.setMinProtein(200.0);
        searchRequest.setMaxProtein(800.0);

        FoodDtoRegular food1 = new FoodDtoRegular();
        food1.setFoodId(1);
        food1.setFoodName("Food 1");

        FoodDtoRegular food2 = new FoodDtoRegular();
        food2.setFoodId(2);
        food2.setFoodName("Food 2");

        List<FoodDtoRegular> foodList = List.of(food1, food2);
        when(foodService.searchFoodByCaloriesRangeAndType(eq("Vegetable"), eq(200.0), eq(800.0))).thenReturn(foodList);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/food/calories-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"Vegetable\", \"minCalories\": 200.0, \"maxCalories\": 800.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].foodId").value(1))
                .andExpect(jsonPath("$[0].foodName").value("Food 1"))
                .andExpect(jsonPath("$[1].foodId").value(2))
                .andExpect(jsonPath("$[1].foodName").value("Food 2"));
    }
}