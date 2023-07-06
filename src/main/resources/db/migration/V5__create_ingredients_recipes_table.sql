CREATE TABLE INGREDIENTS_RECIPES (
     id INT PRIMARY KEY AUTO_INCREMENT,
     quantity DOUBLE,
     recipe_id INT,
     ingredient_id INT,
     food_id INT,
     FOREIGN KEY (recipe_id) REFERENCES RECIPE(recipe_id),
     FOREIGN KEY (ingredient_id) REFERENCES INGREDIENTS(ingredient_id),
     FOREIGN KEY (food_id) REFERENCES FOOD(food_id)
);