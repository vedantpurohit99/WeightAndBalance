package com.company;

import javafx.beans.property.SimpleStringProperty;

public class Data {
    private final SimpleStringProperty subTitle;
    private final SimpleStringProperty weight;
    private final SimpleStringProperty arm;
    private final SimpleStringProperty moment;

    Data (String fTitle, String fWeight, String fArm, String fMoment){
        this.subTitle = new SimpleStringProperty(fTitle);
        this.weight = new SimpleStringProperty(fWeight);
        this.arm = new SimpleStringProperty(fArm);
        this.moment = new SimpleStringProperty(fMoment);
    }

    public String getSubTitle(){
        return subTitle.get();
    }

    public void setSubTitle(String newTitle){
        subTitle.set(newTitle);
    }

    public String getWeight(){
        return weight.get();
    }

    public void setWeight(String newWeight){
        weight.set(newWeight);
    }

    public String getArm(){
        return arm.get();
    }

    public void setArm(String newArm){
        arm.set(newArm);
    }

    public String getMoment(){
        return moment.get();
    }

    public void setMoment(String newMoment){
        moment.set(newMoment);
    }
}
