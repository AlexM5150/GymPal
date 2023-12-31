package com.example.project1;


import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;



// This class is used to keep a recording of all data and use it to send
public class UserProfile implements Serializable {

    public String userID;
    public String displayName;
    public String feetNum;
    public String inchNum;
    public String curWeight;
    public String goalWeight;
    public String aLevel;
    public String gender;

    public String style;
    public String caloriesLeft;
    public String age;
    public String bio;
    public String location;
    public String profilePic;
    public String currentFoodName;
    public String currentFoodCalories;
    public String currentBrandName;
    public String prevCalories;
    public String squatNum;
    public String benchNum;
    public String deadliftNum;
    public List<String> goalList;

    public UserProfile(){}

    // Constructor method for all values of UserProfile



    public UserProfile(String userID, String displayName, String feetNum, String inchNum, String curWeight, String gWeight, String aLevel, String gender, String style, String caloriesLeft, String age, String bio, String location, String profilePic,
                       String currentFoodName, String currentFoodCalories, String currentBrandName, String prevCalories, String squatNum, String benchNum, String deadliftNum, List<String> goalList){


        this.userID = userID;
        this.displayName = displayName;

        this.feetNum = feetNum;
        this.inchNum = inchNum;
        this.curWeight = curWeight;
        this.aLevel = aLevel;
        this.gender = gender;
        this.style = style;
        this.goalWeight = gWeight;
        this.caloriesLeft = caloriesLeft;
        this.age = age;
        this.bio = bio;
        this.location = location;
        this.profilePic = profilePic;
        this.currentFoodName = currentFoodName;
        this.currentFoodCalories = currentFoodCalories;
        this.currentBrandName = currentBrandName;
        this.prevCalories = prevCalories;
        this.squatNum = squatNum;
        this.benchNum = benchNum;
        this.deadliftNum = deadliftNum;
        this.goalList = goalList;
    }

    // Below is all of the get and set methods, created if needed
    public String getUserID(){return userID;}

    public void setUserID(String userID){this.userID = userID;}

    public String getDisplayName(){return displayName;}

    public void setDisplayName(String displayName){this.displayName = displayName;}

    public String getPrevCalories(){return this.prevCalories; }

    public void setPrevCalories(String prevCalories){ this.prevCalories = prevCalories; }

    public String getCurrentFoodName(){ return currentFoodName; }

    public void setCurrentFoodName(String currentFoodName) { this.currentFoodName = currentFoodName; }

    public String getCurrentFoodCalories(){ return currentFoodCalories; }

    public void setCurrentFoodCalories(String currentFoodCalories) { this.currentFoodCalories = currentFoodCalories; }

    public String getCurrentBrandName(){ return currentBrandName; }

    public void setCurrentBrandName(String currentBrandName) { this.currentBrandName = currentBrandName; }

    public String getProfilePic(){ return profilePic; }

    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public String  getAge(){ return age; }

    public void setAge(String age) { this.age = age;}

    public String  getBio(){ return bio; }

    public void setBio(String bio) { this.bio = bio;}

    public String  getLocation(){ return location; }

    public void setLocation(String location) { this.location = location;}

    public String getCaloriesLeft(){ return caloriesLeft;}

    public void setCaloriesLeft(String caloriesLeft){ this.caloriesLeft = caloriesLeft; }

    public String getFeetNum() {
        return feetNum;
    }

    public void setFeetNum(String feetNum) {
        this.feetNum = feetNum;
    }

    public String getInchNum() {
        return inchNum;
    }

    public void setInchNum(String inchNum) {
        this.inchNum = inchNum;
    }

    public String getCurWeight() {
        return curWeight;
    }

    public void setCurWeight(String curWeight) {
        this.curWeight = curWeight;
    }

    public String getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(String goalWeight) {
        this.goalWeight = goalWeight;
    }

    public String getaLevel() {
        return aLevel;
    }

    public void setaLevel(String aLevel) {
        this.aLevel = aLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSquatNum() {
        return squatNum;
    }

    public void setSquatNum(String squatNum) {
        this.squatNum = squatNum;
    }

    public String getBenchNum() {
        return benchNum;
    }

    public void setBenchNum(String benchNum) {
        this.benchNum = benchNum;
    }

    public String getDeadliftNum() {
        return deadliftNum;
    }

    public void setDeadliftNum(String deadliftNum) {
        this.deadliftNum = deadliftNum;
    }

    public List<String> getGoalList() {return goalList;}

    public void setGoalList(List<String> goalList) {this.goalList = goalList;}
}
