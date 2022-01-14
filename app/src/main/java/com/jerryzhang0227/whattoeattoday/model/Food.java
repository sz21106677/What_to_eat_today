package com.jerryzhang0227.whattoeattoday.model;

public class Food {
    private String foodName;
    private int weight;

    public Food(String foodName, int weight) {
        this.foodName = foodName;
        this.weight = weight;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
