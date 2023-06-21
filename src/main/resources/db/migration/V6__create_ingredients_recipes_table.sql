CREATE TABLE INGREDIENTS_RECIPES (
     RecipeID INT,
     IngredientID INT,
     Quantity DECIMAL(8, 2),
     FOREIGN KEY (RecipeID) REFERENCES Recipe(RecipeID),
     FOREIGN KEY (IngredientID) REFERENCES Ingredients(IngredientID)
);
