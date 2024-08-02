package com.jtdev.shape_shift.model;

public class model_Achievement {

    String achievement;
    int achievementIcon;

    public model_Achievement(String achievement, int achievementIcon) {
        this.achievement = achievement;
        this.achievementIcon = achievementIcon;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public int getAchievementIcon() {
        return achievementIcon;
    }

    public void setAchievementIcon(int achievementIcon) {
        this.achievementIcon = achievementIcon;
    }
}
