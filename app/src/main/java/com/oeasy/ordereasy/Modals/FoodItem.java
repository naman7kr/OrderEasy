package com.oeasy.ordereasy.Modals;

/**
 * Created by Stan on 4/5/2018.
 */

public class FoodItem {

    private String name;

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {

        return img;
    }

    private String img;
    private int type;
    private int veg;

    public void setVeg(int veg) {
        this.veg = veg;
    }

    public int getVeg() {

        return veg;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


}
