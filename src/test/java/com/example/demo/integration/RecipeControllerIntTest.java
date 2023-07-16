package com.example.demo.integration;

import com.example.demo.controller.RecipeController;
import com.example.demo.dto.RecipeDtoRegular;
import com.example.demo.dto.UserDto;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.request.SearchRequest;
import com.example.demo.service.RecipeService;
import exceptions.UnauthorizedAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(RecipeController.class)
public class RecipeControllerIntTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private RecipeRepository recipeRepository;

    RecipeDtoRegular recipe1;

    RecipeDtoRegular recipe2;


    @BeforeEach
    void setUp(){
        RecipeController recipeController = new RecipeController(recipeService, recipeRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName("Recipe 1");

        recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName("Recipe 2");
    }

    @Test
    void testCreateRecipe() throws Exception{
        String requestBody = "{\"recipeId\": 1, \"recipeName\": \"Recipe 1\", \"foodId\": 1, \"isPublic\": true, \"cookingTime\": 30, \"cuisine\": \"Italian\", \"ingredientsRecipesList\": []}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/recipes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

    }

    @Test
    void getAllRecipes_ShouldReturnListOfRecipes() throws Exception {
        List<RecipeDtoRegular> recipes = Arrays.asList(recipe1, recipe2);
        when(recipeService.getAllRecipes()).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }


    @Test
    void deleteRecipe_WhenAuthorized() throws Exception {
        int recipeId = 1;
        UserDto user = new UserDto();
        user.setUsername("admin");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/recipes/delete/{recipeId}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Recipe deleted successfully"));
    }

    @Test
    void deleteRecipe_WhenUnauthorized() throws Exception, UnauthorizedAccessException {
        int recipeId = 1;
        UserDto user = new UserDto();
        user.setUsername("randomUser");

        doThrow(UnauthorizedAccessException.class).when(recipeService).deleteRecipe(eq(user), eq(recipeId));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/recipes/delete/{recipeId}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"randomUser\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("You are not authorized to delete this recipe. Only Admin or Creator can"));
    }

    @Test
    void getRecipeByName_ShouldReturn_RecipeDtoRegular() throws Exception {
        String recipeName = "Recipe 1";
        RecipeDtoRegular recipeDto = new RecipeDtoRegular();
        recipeDto.setRecipeId(1);
        recipeDto.setRecipeName(recipeName);
        when(recipeService.searchRecipeByName(eq(recipeName))).thenReturn(recipeDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/by-name/{recipeName}", recipeName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId").value(1))
                .andExpect(jsonPath("$.recipeName").value(recipeName));
    }

    @Test
    void getRecipesByUser_ShouldReturn_ListOfRecipes() throws Exception {
        int userId = 1;
        String recipeName1 = "Recipe 1";
        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName(recipeName1);

        String recipeName2 = "Recipe 2";
        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName(recipeName2);

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.getRecipeDtosByUser(eq(userId))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/by-user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }

    @Test
    void getAllPublicRecipes_ShouldReturn_ListOfRecipes() throws Exception {
        String recipeName1 = "Recipe 1";
        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName(recipeName1);

        String recipeName2 = "Recipe 2";
        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName(recipeName2);

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.getAllPublicRecipes(eq(true))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/public", true))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }

    @Test
    void getRecipesByIngredient_ShouldReturn_ListOfRecipes() throws Exception {
        String ingredient = "Ingredient";

        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName("Recipe 1");

        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName("Recipe 2");

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.searchRecipesByIngredients(eq(ingredient))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/ingredient/{ingredient}", ingredient))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }


    @Test
    void getRecipesByProteinAndUser_ShouldReturnListOfRecipes() throws Exception {
        int userId = 1;
        double minProtein = 10.5;
        String recipeName1 = "Recipe 1";
        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName(recipeName1);

        String recipeName2 = "Recipe 2";
        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName(recipeName2);

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.searchRecipesByProteinAndUser(eq(userId), eq(minProtein))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/protein")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"minProtein\": 10.5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }

    @Test
    void getRecipesByFatAndUser_ShouldReturnListOfRecipes() throws Exception {
        int userId = 1;
        double maxFat = 5.0;
        String recipeName1 = "Recipe 1";
        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName(recipeName1);

        String recipeName2 = "Recipe 2";
        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName(recipeName2);

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.searchRecipesByFatAndUser(eq(userId), eq(maxFat))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/fat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\": 1, \"maxFat\": 5.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }

    @Test
    void getRecipesByCaloriesAndUser_ShouldReturnListOfRecipes() throws Exception {
        int userId = 1;
        double minCalories = 450.0;
        String recipeName1 = "Recipe 1";
        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName(recipeName1);

        String recipeName2 = "Recipe 2";
        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName(recipeName2);

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.searchRecipesByCaloriesAndUser(eq(userId), eq(minCalories))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/calories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"minCalories\": 450.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }

    @Test
    void getRecipesByCarbohydratesAndUser_ShouldReturnListOfRecipes() throws Exception {
        int userId = 1;
        double minCarbohydrates = 15.0;
        String recipeName1 = "Recipe 1";
        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName(recipeName1);

        String recipeName2 = "Recipe 2";
        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName(recipeName2);

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.searchRecipesByCarbohydratesAndUser(eq(userId), eq(minCarbohydrates))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/carbohydrates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"minCarbohydrates\": 15.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }


    @Test
    void getRecipesByCarbohydratesRangeAndUser_ShouldReturnListOfRecipes() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setUserId(1);
        searchRequest.setMinCarbohydrates(10.0);
        searchRequest.setMaxCarbohydrates(80.0);

        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName("Recipe 1");

        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName("Recipe 2");

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.searchRecipesByCarbohydratesRangeAndUser(eq(1), eq(10.0), eq(80.0))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/carbohydrates-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"minCarbohydrates\": 10.0, \"maxCarbohydrates\": 80.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }

    @Test
    void getRecipesByProteinsRangeAndUser_ShouldReturnListOfRecipes() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setUserId(1);
        searchRequest.setMinProtein(30.0);
        searchRequest.setMaxProtein(60.0);

        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName("Recipe 1");

        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName("Recipe 2");

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.searchRecipesByProteinsRangeAndUser(eq(1), eq(30.0), eq(60.0))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/proteins-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"minProteins\": 30.0, \"maxProteins\": 60.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }


    @Test
    void searchRecipesByFatRangeAndUser_ShouldReturnListOfRecipes() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setUserId(1);
        searchRequest.setMinFats(10.0);
        searchRequest.setMaxFats(20.0);

        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName("Recipe 1");

        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName("Recipe 2");

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.searchRecipesByFatRangeAndUser(eq(1), eq(10.0), eq(20.0))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/fat-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"minFats\": 10.0, \"maxFats\": 20.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }

    @Test
    void searchRecipesByCaloriesRangeAndUser_ShouldReturnListOfRecipes() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setUserId(1);
        searchRequest.setMinCalories(200.0);
        searchRequest.setMaxCalories(800.0);

        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName("Recipe 1");

        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName("Recipe 2");

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.searchRecipesByCaloriesRangeAndUser(eq(1), eq(200.0), eq(800.0))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/calories-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"minCalories\": 200.0, \"maxCalories\": 800.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }

    @Test
    void searchRecipesByNutrientRangesAndUser_ShouldReturnListOfRecipes() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setUserId(1);
        searchRequest.setMinCarbohydrates(10.0);
        searchRequest.setMaxCarbohydrates(80.0);
        searchRequest.setMinFats(10.0);
        searchRequest.setMaxFats(20.0);
        searchRequest.setMinProtein(30.0);
        searchRequest.setMaxProtein(60.0);
        searchRequest.setMinCalories(200.0);
        searchRequest.setMaxCalories(800.0);

        RecipeDtoRegular recipe1 = new RecipeDtoRegular();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName("Recipe 1");

        RecipeDtoRegular recipe2 = new RecipeDtoRegular();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName("Recipe 2");

        List<RecipeDtoRegular> recipes = List.of(recipe1, recipe2);
        when(recipeService.searchRecipesByNutrientRangesAndUser(eq(1), eq(searchRequest))).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/recipes/nutrients-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"minCarbohydrates\": 10.0, \"maxCarbohydrates\": 80.0, " +
                                "\"minFats\": 10.0, \"maxFats\": 20.0, \"minProteins\": 30.0, " +
                                "\"maxProteins\": 60.0, \"minCalories\": 200.0, \"maxCalories\": 800.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeId").value(1))
                .andExpect(jsonPath("$[0].recipeName").value("Recipe 1"))
                .andExpect(jsonPath("$[1].recipeId").value(2))
                .andExpect(jsonPath("$[1].recipeName").value("Recipe 2"));
    }

}
