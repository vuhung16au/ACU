package com.acu.sugargrid.data

import com.acu.sugargrid.model.Recipe

object MockRecipeDataSource {
    val recipes = listOf(
        Recipe(
            id = "1",
            title = "Appetizer",
            imageUrl = "https://images.unsplash.com/photo-1541014741259-df529411b96a?auto=format&fit=crop&w=400&q=80",
            ingredients = listOf("Various snacks"),
            instructions = listOf("Arrange on a plate")
        ),
        Recipe(
            id = "2",
            title = "Baking",
            imageUrl = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?auto=format&fit=crop&w=400&q=80",
            ingredients = listOf("Flour", "Sugar", "Eggs"),
            instructions = listOf("Bake at 350°F")
        ),
        Recipe(
            id = "3",
            title = "Barbecue",
            imageUrl = "https://images.unsplash.com/photo-1529193591184-b1d58069ecdd?auto=format&fit=crop&w=400&q=80",
            ingredients = listOf("Meat", "BBQ Sauce"),
            instructions = listOf("Grill until done")
        ),
        Recipe(
            id = "4",
            title = "Beverage",
            imageUrl = "https://images.unsplash.com/photo-1513558161293-cdaf765877f1?auto=format&fit=crop&w=400&q=80",
            ingredients = listOf("Juice", "Ice"),
            instructions = listOf("Mix and serve cold")
        ),
        Recipe(
            id = "5",
            title = "Breakfast",
            imageUrl = "https://images.unsplash.com/photo-1525351484163-7529414344d8?auto=format&fit=crop&w=400&q=80",
            ingredients = listOf("Eggs", "Bacon", "Toast"),
            instructions = listOf("Cook eggs to preference")
        ),
        Recipe(
            id = "6",
            title = "Dessert",
            imageUrl = "https://images.unsplash.com/photo-1551024601-bec78aea704b?auto=format&fit=crop&w=400&q=80",
            ingredients = listOf("Chocolate", "Cream"),
            instructions = listOf("Mix and chill")
        ),
        Recipe(
            id = "7",
            title = "Lunch",
            imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=400&q=80",
            ingredients = listOf("Salad", "Protein"),
            instructions = listOf("Toss together")
        ),
        Recipe(
            id = "8",
            title = "Main Dish",
            imageUrl = "https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=400&q=80",
            ingredients = listOf("Main ingredients"),
            instructions = listOf("Cook thoroughly")
        ),
        Recipe(
            id = "9",
            title = "Side Dish",
            imageUrl = "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?auto=format&fit=crop&w=400&q=80",
            ingredients = listOf("Vegetables"),
            instructions = listOf("Saute with garlic")
        ),
        Recipe(
            id = "10",
            title = "Snack",
            imageUrl = "https://images.unsplash.com/photo-1599490659223-e1595fb1639d?auto=format&fit=crop&w=400&q=80",
            ingredients = listOf("Nuts", "Fruit"),
            instructions = listOf("Ready to eat")
        ),
        Recipe(
            id = "11",
            title = "Starter",
            imageUrl = "https://images.unsplash.com/photo-1541529086526-db283c563270?auto=format&fit=crop&w=400&q=80",
            ingredients = listOf("Small bites"),
            instructions = listOf("Serve before main")
        )
    )
}
