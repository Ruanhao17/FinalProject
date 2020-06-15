package com.swufe.finalproject;

import cn.bmob.v3.BmobObject;

public class Question extends BmobObject {
    private String Tag1;
    private String Tag2;
    private String abstract_content;
    private int reputationValue;
    private int heat;
    private float price;
    private boolean is_anwser;

    public boolean isIs_anwser() {
        return is_anwser;
    }

    public void setIs_anwser(boolean is_anwser) {
        this.is_anwser = is_anwser;
    }

    public String getTag1() {
        return Tag1;
    }

    public void setTag1(String Tag1) {
        this.Tag1 = Tag1;
    }

    public String getTag2() {
        return Tag2;
    }

    public void setTag2(String Tag2) {
        this.Tag2 = Tag2;
    }

    public String getAbstract_content() {
        return abstract_content;
    }

    public void setAbstract_content(String abstract_content) {
        this.abstract_content = abstract_content;
    }

    public int getReputationValue() {
        return reputationValue;
    }

    public void setReputationValue(int reputation) {
        this.reputationValue = reputation;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
