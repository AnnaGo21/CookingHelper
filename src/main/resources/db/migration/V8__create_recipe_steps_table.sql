CREATE TABLE RECIPE_STEPS (
      recipe_id INT,
      step VARCHAR(255),
      FOREIGN KEY (recipe_id) REFERENCES RECIPE(recipeId)
);