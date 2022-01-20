package com.jerryzhang0227.whattoeattoday.model;

public class Food {
    private final String foodName;
    private final int weight;

    public Food(String foodName, int weight) {
        this.foodName = foodName;
        this.weight = weight;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getWeight() {
        return weight;
    }

}
