CREATE TABLE INGREDIENTS_RECIPES (
     id INT PRIMARY KEY AUTO_INCREMENT,
     quantity DOUBLE,
     recipe_id INT,
     ingredient_id INT,
     FOREIGN KEY (recipe_id) REFERENCES RECIPE(recipeId),
     FOREIGN KEY (ingredient_id) REFERENCES INGREDIENTS(ingredientID)
);