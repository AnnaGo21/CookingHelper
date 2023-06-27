CREATE TABLE FOOD_RECIPES (
      recipe_id INT,
      food_id INT,
      FOREIGN KEY (recipe_id) REFERENCES RECIPE(recipeId),
      FOREIGN KEY (food_id) REFERENCES FOOD(foodID)
);