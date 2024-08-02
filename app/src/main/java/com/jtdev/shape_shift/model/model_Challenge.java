
package com.jtdev.shape_shift.model;
public class model_Challenge {

    public static final int EASY = 5;
    public static final int MEDIUM = 10;
    public static final int HARD = 15;

    public int difficulty;
    public String name;
    public int image;

    public model_Challenge(int difficulty, String name, int image) {
        this.difficulty = difficulty;
        this.name = name;
        this.image = image;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
