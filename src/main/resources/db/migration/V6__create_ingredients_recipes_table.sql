CREATE TABLE Ingredients_Recipes (
     RecipeID INT,
     IngredientID INT,
     Quantity DECIMAL(8, 2),
     FOREIGN KEY (RecipeID) REFERENCES Recipes(RecipeID),
     FOREIGN KEY (IngredientID) REFERENCES Ingredients(IngredientID)
);