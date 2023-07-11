package com.example.demo.controller;

import com.example.demo.dto.RecipeDto;
import com.example.demo.dto.RecipeDtoRegular;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.IngredientsRecipes;
import com.example.demo.entity.User;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.request.*;
import com.example.demo.service.RecipeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest
class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeController recipeController;

    RecipeDto recipeDtoOne;
    RecipeDto recipeDtoTwo;
    RecipeDtoRegular recipeDtoRegularOne;
    RecipeDtoRegular recipeDtoRegularTwo;
    UserDto userDtoOne;
    UserDto userDtoTwo;
    List<IngredientsRecipes> ingredientsRecipesList;

    @BeforeEach
    void setUp() {
        recipeRepository = mock(RecipeRepository.class);
        recipeService = mock(RecipeService.class);

        recipeController = new RecipeController(recipeService, recipeRepository);

//        MockitoAnnotations.openMocks(this);
        ingredientsRecipesList = new ArrayList<>();
        userDtoOne = new UserDto("Emma28", "Emma", "Swan", "emma.sw@gmail.com");
        userDtoTwo = new UserDto("Killian125", "Killian", "Jones", "ki.jo@gmail.com");
        recipeDtoOne = new RecipeDto(userDtoOne,1,1,1,"Greek Salad", true,
                30, "Greek",ingredientsRecipesList);
        recipeDtoTwo = new RecipeDto(userDtoTwo,2,2,1,"Greek Salad 2", true,
                35, "Greek",ingredientsRecipesList);

        recipeDtoRegularOne = new RecipeDtoRegular(recipeDtoOne.getCreatorUserId(),recipeDtoOne.getRecipeId(), recipeDtoOne.getFoodId(),
                recipeDtoOne.getRecipeName(),true,30,120,15,120);

        recipeDtoRegularTwo = new RecipeDtoRegular(recipeDtoTwo.getCreatorUserId(),recipeDtoTwo.getRecipeId(), recipeDtoTwo.getFoodId(),
                recipeDtoTwo.getRecipeName(),true,35,110,12,150);


    }

    @AfterEach
    void tearDown(){

    }

    @Test
    void createRecipe() {
        RecipeDto recipeDto = new RecipeDto();
        RecipeDto expectedRecipeDto = new RecipeDto();
        when(recipeService.createRecipe(recipeDtoOne)).thenReturn(expectedRecipeDto);

        recipeController.createRecipe(recipeDto);

        assertEquals(expectedRecipeDto, recipeDto);  // Compare with the expected result
        verify(recipeService, times(1)).createRecipe(recipeDto);
    }

    @Test
    void getAllRecipes() {
        List<RecipeDtoRegular> expectedRecipes = new ArrayList<>();
        when(recipeService.getAllRecipes()).thenReturn(expectedRecipes);

        List<RecipeDtoRegular> actualRecipes = recipeController.getAllRecipes();

        assertEquals(expectedRecipes, actualRecipes);
    }

    @Test
    void deleteRecipe() {

    }

    @Test
    void getRecipeByName_withValidRecipeName_shouldReturnRecipeDtoRegular() {
        String recipeName = "Greek Salad";
        RecipeDtoRegular expectedRecipe = new RecipeDtoRegular();
        when(recipeService.searchRecipeByName(recipeName)).thenReturn(expectedRecipe);

        RecipeDtoRegular actualRecipe = recipeController.getRecipeByName(recipeName);
        assertEquals(expectedRecipe, actualRecipe);
        verify(recipeService, times(1)).searchRecipeByName(recipeName);
    }

    @Test
    void getRecipesByUser() {
        int userId = 1;
        List<RecipeDtoRegular> expectedRecipes = new ArrayList<>();
        when(recipeService.getRecipeDtosByUser(userId)).thenReturn(expectedRecipes);

        List<RecipeDtoRegular> actualRecipes = recipeController.getRecipesByUser(userId);

        assertEquals(expectedRecipes, actualRecipes);
    }

    @Test
    void getAllPublicRecipes() {
        List<RecipeDtoRegular> expectedRecipes = new ArrayList<>();
        when(recipeService.getAllPublicRecipes(true)).thenReturn(expectedRecipes);

        List<RecipeDtoRegular> actualRecipes = recipeController.getAllPublicRecipes();

        assertEquals(expectedRecipes, actualRecipes);
    }

    @Test
    void getRecipesByIngredient() {
        String ingredient = "Olive Oil";
        List<RecipeDtoRegular> expectedRecipes = new ArrayList<>();
        when(recipeService.searchRecipesByIngredients(ingredient)).thenReturn(expectedRecipes);

        List<RecipeDtoRegular> actualRecipes = recipeController.getRecipesByIngredient(ingredient);

        assertEquals(expectedRecipes, actualRecipes);
    }

    @Test
    void getRecipesByProteinAndUser() {
        ProteinRequest proteinRequest = new ProteinRequest(1, 10.40);
        List<RecipeDtoRegular> expectedRecipes = new ArrayList<>();
        when(recipeService.searchRecipesByProteinAndUser(proteinRequest.getUserId(), proteinRequest.getMinProtein())).thenReturn(expectedRecipes);

        List<RecipeDtoRegular> actualRecipes = recipeController.getRecipesByProteinAndUser(proteinRequest);

        assertEquals(expectedRecipes, actualRecipes);
    }

    @Test
    void getRecipesByFatAndUser() {
        FatRequest fatRequest = new FatRequest(1, 5.5);
        List<RecipeDtoRegular> expectedRecipes = new ArrayList<>();
        when(recipeService.searchRecipesByFatAndUser(fatRequest.getUserId(), fatRequest.getMaxFat())).thenReturn(expectedRecipes);

        List<RecipeDtoRegular> actualRecipes = recipeController.getRecipesByFatAndUser(fatRequest);

        assertEquals(expectedRecipes, actualRecipes);
    }

    @Test
    void getRecipesByCaloriesAndUser() {
        CaloriesRequest caloriesRequest = new CaloriesRequest(1, 250);
        List<RecipeDtoRegular> expectedRecipes = new ArrayList<>();
        when(recipeService.searchRecipesByCaloriesAndUser(caloriesRequest.getUserId(), caloriesRequest.getMinCalories())).thenReturn(expectedRecipes);

        List<RecipeDtoRegular> actualRecipes = recipeController.getRecipesByCaloriesAndUser(caloriesRequest);

        assertEquals(expectedRecipes, actualRecipes);
    }

    @Test
    void getRecipesByCarbohydratesAndUser() {
        CarbohydratesRequest carbohydratesRequest = new CarbohydratesRequest(1, 50.65);
        List<RecipeDtoRegular> expectedRecipes = new ArrayList<>();
        when(recipeService.searchRecipesByCarbohydratesAndUser(carbohydratesRequest.getUserId(), carbohydratesRequest.getMinCarbohydrates())).thenReturn(expectedRecipes);

        List<RecipeDtoRegular> actualRecipes = recipeController.getRecipesByCarbohydratesAndUser(carbohydratesRequest);

        assertEquals(expectedRecipes, actualRecipes);
    }


    @Test
    void getRecipesByProteinsRangeAndUser() {
        SearchRequest searchRequest = new SearchRequest(1, 10.5, 30.5);
        List<RecipeDtoRegular> expectedRecipes = new ArrayList<>();
        when(recipeService.searchRecipesByProteinsRangeAndUser(searchRequest.getUserId(), searchRequest.getMinProtein(), searchRequest.getMaxProtein())).thenReturn(expectedRecipes);

        List<RecipeDtoRegular> actualRecipes = recipeController.getRecipesByProteinsRangeAndUser(searchRequest);

        assertEquals(expectedRecipes, actualRecipes);
    }


    @Test
    void searchRecipesByNutrientRangesAndUser() {
        SearchRequest searchRequest = new SearchRequest(1, 20.5, 50.5, 10.5,
                30.5, 5.5, 10.5, 100.45, 250.45);
        List<RecipeDtoRegular> expectedRecipes = new ArrayList<>();
        when(recipeService.searchRecipesByNutrientRangesAndUser(searchRequest.getUserId(), searchRequest)).thenReturn(expectedRecipes);

        List<RecipeDtoRegular> actualRecipes = recipeController.searchRecipesByNutrientRangesAndUser(searchRequest);

        assertEquals(expectedRecipes, actualRecipes);
    }
}