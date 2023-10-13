CREATE TABLE RECIPE_STEPS (
      recipe_id INT,
      step VARCHAR(255),
      food_id int,
      FOREIGN KEY (recipe_id) REFERENCES RECIPE(recipe_id),
      FOREIGN KEY (food_id) references food(food_id)
);