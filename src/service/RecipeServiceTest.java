package com.example.demo.service;

import com.example.demo.dto.RecipeDto;
import com.example.demo.dto.RecipeDtoRegular;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.User;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.SearchRequest;
import exceptions.UnauthorizedAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RecipeServiceTest {
    private RecipeRepository recipeRepository;

    private UserRepository userRepository;
    @InjectMocks
    private RecipeService recipeService;
    AutoCloseable autoCloseable;
    Recipe recipe;
    User user;
    List<Recipe> recipes;

    @BeforeEach
    void setUp() {
        recipeRepository = mock(RecipeRepository.class);
        userRepository = mock(UserRepository.class);
        recipeService = new RecipeService(recipeRepository, userRepository);
        autoCloseable = MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setUsername("Emma28");
        user.setFirstName("Emma");
        user.setLastName("Swan");
        user.setEmail("emma.sw@gmail.com");

        recipe = new Recipe();
        recipe.setRecipeId(1);
        recipe.setCreatedBy(user);
        recipe.setRecipeName("Greek Salad");
        recipe.setTotalCarbohydrates(200.00);

//        recipes = Arrays.asList(
//                createRecipe(1, "Greek Salad", true),
//                createRecipe(1, "Recipe 1", true),
//                createRecipe(2, "Recipe 2", true));
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
        recipes = new ArrayList<>();
        Mockito.reset();
    }

    @Test
    void createRecipe() {
        RecipeDto recipeDto = RecipeDto.builder()
                .recipeName("Greek Salad")
                .creatorUserId(1)
                .foodId(1)
                .isPublic(true)
                .cuisine("italian")
                .build();

        User createdBy = new User();
        when(userRepository.getUserById(anyInt())).thenReturn(createdBy);

        recipe.setCreatedBy(createdBy);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        RecipeDto result = recipeService.createRecipe(recipeDto);

        assertEquals(recipe.getRecipeId(), result.getRecipeId());
    }

    @Test
    void getAllRecipes() {
        List<Recipe> recipeList = Collections.singletonList(new Recipe());
        when(recipeRepository.findAll()).thenReturn(recipeList);

        List<RecipeDtoRegular> result = recipeService.getAllRecipes();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void deleteRecipe_When_AuthorizedUser_DeletesRecipe() throws UnauthorizedAccessException {
        UserDto creatorUserDto = UserDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();

        recipe.setCreatedBy(user);

        when(userRepository.getUserById(anyInt())).thenReturn(user);
        when(recipeRepository.findByRecipeId(anyInt())).thenReturn(recipe);

        UnauthorizedAccessException exception = null;
        try {
            recipeService.deleteRecipe(creatorUserDto, 1);
        } catch (UnauthorizedAccessException e) {
            exception = e;
        }
        assertNull(exception);
    }

    @Test
    void deleteRecipe_When_UnauthorizedUser_DeletesRecipe() throws UnauthorizedAccessException {
        UserDto unauthorizedUserDto = UserDto.builder()
                .username("UnauthorizedUser")
                .firstName("xxx")
                .lastName("yyyyy")
                .email("xx.yyy@example.com")
                .build();

        recipe.setCreatedBy(user);

        when(userRepository.getUserById(anyInt())).thenReturn(user);
        when(recipeRepository.findByRecipeId(anyInt())).thenReturn(recipe);

        UnauthorizedAccessException exception = null;
        try {
            recipeService.deleteRecipe(unauthorizedUserDto, 1);
        } catch (UnauthorizedAccessException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    void getRecipeDtosByUser() {
        int userId = 1;

        User user = new User();
        user.setId(userId);

        when(userRepository.getUserById(userId)).thenReturn(user);
        when(recipeRepository.findByCreatedById(userId)).thenReturn(Collections.singletonList(recipe));

        List<RecipeDtoRegular> recipeDtos = recipeService.getRecipeDtosByUser(userId);

        assertNotNull(recipeDtos);
        assertEquals(1, recipeDtos.size());
        assertEquals(userId, recipeDtos.get(0).getCreatorUserId());
    }

    @Test
    void searchRecipesByName() {
        String recipeName = "Greek Salad";
        //Recipe recipe = new Recipe();
        when(recipeRepository.findByRecipeNameContainingIgnoreCase(recipeName)).thenReturn(recipe);

        RecipeDtoRegular result = recipeService.searchRecipeByName(recipeName);

        assertNotNull(result);
    }

    @Test
    void getRecipeById() {
        int recipeId = 1;

        when(recipeRepository.findByRecipeId(recipeId)).thenReturn(recipe);
        RecipeDtoRegular result = recipeService.getRecipeById(recipeId);

        assertNotNull(result);
    }

    private Recipe createRecipe(int recipeId, String recipeName, boolean isPublic) {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        recipe.setRecipeName(recipeName);
        recipe.setPublic(isPublic);
        return recipe;
    }

    @Test
    void getAllPublicRecipes() {
        List<Recipe> publicRecipes = Collections.singletonList(recipe);
        when(recipeRepository.findByIsPublic(true)).thenReturn(publicRecipes);

        List<RecipeDtoRegular> result = recipeService.getAllPublicRecipes(true);

        assertEquals(publicRecipes.size(), result.size());
    }

    @Test
    void searchRecipesByIngredients() {
        String ingredientName = "Mushroom";
        List<Recipe> recipeList = Collections.singletonList(recipe);
        when(recipeRepository.findByIngredientsRecipesList_Ingredient_IngredientName(ingredientName)).thenReturn(recipeList);

        List<RecipeDtoRegular> result = recipeService.searchRecipesByIngredients(ingredientName);

        assertEquals(recipeList.size(), result.size());
    }

    @Test
    void searchRecipesByProteinAndUser() {
        int userId = 1;
        double minProtein = 10.5;

        List<Recipe> recipeList = Collections.singletonList(recipe);
        when(recipeRepository.findByIngredientsRecipesList_Ingredient_ProteinsGreaterThanEqual(minProtein)).thenReturn(recipeList);
        when(userRepository.getUserById(userId)).thenReturn(user);
        when(recipeRepository.findByCreatedById(userId)).thenReturn(recipeList);

        List<RecipeDtoRegular> result = recipeService.searchRecipesByProteinAndUser(userId, minProtein);

        assertEquals(recipeList.size(), result.size());
    }

    @Test
    void searchRecipesByFatAndUser() {
        int userId = 1;
        double maxFat = 8.5;

        List<Recipe> recipeList = Collections.singletonList(recipe);
        when(recipeRepository.findByIngredientsRecipesList_Ingredient_FatLessThanEqual(maxFat)).thenReturn(recipeList);
        when(userRepository.getUserById(userId)).thenReturn(user);
        when(recipeRepository.findByCreatedById(userId)).thenReturn(recipeList);

        List<RecipeDtoRegular> result = recipeService.searchRecipesByFatAndUser(userId, maxFat);

        assertEquals(recipeList.size(), result.size());
    }

    @Test
    void searchRecipesByCaloriesAndUser() {
        int userId = 1;
        double minCalories = 130.5;

        List<Recipe> recipeList = Collections.singletonList(recipe);
        when(recipeRepository.findByIngredientsRecipesList_Ingredient_CaloriesGreaterThanEqual(minCalories)).thenReturn(recipeList);
        when(userRepository.getUserById(userId)).thenReturn(user);
        when(recipeRepository.findByCreatedById(userId)).thenReturn(recipeList);

        List<RecipeDtoRegular> result = recipeService.searchRecipesByCaloriesAndUser(userId, minCalories);

        assertEquals(recipeList.size(), result.size());
    }

    @Test
    void searchRecipesByCarbohydratesAndUser() {
        int userId = 1;
        double minCarbohydrates = 25.5;

        List<Recipe> recipeList = Collections.singletonList(recipe);
        when(recipeRepository.findByIngredientsRecipesList_Ingredient_CarbohydratesGreaterThanEqual(minCarbohydrates)).thenReturn(recipeList);
        when(userRepository.getUserById(userId)).thenReturn(user);
        when(recipeRepository.findByCreatedById(userId)).thenReturn(recipeList);

        List<RecipeDtoRegular> result = recipeService.searchRecipesByCarbohydratesAndUser(userId, minCarbohydrates);

        assertEquals(recipeList.size(), result.size());
    }

    @Test
    void searchRecipesByCarbohydratesRangeAndUser() {
        reset(recipeRepository);

        Recipe recipe1 = new Recipe();
        recipe1.setRecipeId(1);
        recipe1.setCreatedBy(user);
        recipe1.setRecipeName("Greek Salad");
        recipe1.setTotalCarbohydrates(200.00);
        int userId = user.getId();
        double minCarbohydrates = 1;
        double maxCarbohydrates = 200;
        List<Recipe> expectedRecipes = List.of(recipe1);

        //when(recipeRepository.findByTotalCarbohydratesBetween(minCarbohydrates, maxCarbohydrates)).thenReturn(expectedRecipes);
        when(userRepository.getUserById(userId)).thenReturn(user);
        when(recipeRepository.findByCreatedById(userId)).thenReturn(expectedRecipes);
        List<RecipeDtoRegular> actualRecipes = recipeService.searchRecipesByCarbohydratesRangeAndUser(userId, minCarbohydrates, maxCarbohydrates);


        assertEquals(expectedRecipes.size(), actualRecipes.size());


//        for (int i = 0; i < expectedRecipes.size(); i++) {
//            Recipe expectedRecipe = expectedRecipes.get(i);
//            RecipeDtoRegular actualRecipe = actualRecipes.get(i);
//            assertEquals(expectedRecipe.getRecipeId(), actualRecipe.getRecipeId());
//            assertEquals(expectedRecipe.getRecipeName(), actualRecipe.getRecipeName());
//        }
    }

    @Test
    void searchRecipesByProteinsRangeAndUser() {
    }

    @Test
    void searchRecipesByFatRangeAndUser() {
    }

    @Test
    void searchRecipesByCaloriesRangeAndUser() {
    }

    @Test
    void searchRecipesByNutrientRangesAndUser() {

    }


    @Test
    void getRecipesByUser() {
        int userId = 1;

        when(userRepository.getUserById(userId)).thenReturn(user);
        when(recipeRepository.findByCreatedById(userId)).thenReturn(Collections.singletonList(recipe));

        List<Recipe> result = recipeService.getRecipesByUser(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}