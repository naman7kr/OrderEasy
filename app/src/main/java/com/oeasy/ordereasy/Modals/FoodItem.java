package com.oeasy.ordereasy.Modals;

/**
 * Created by Stan on 4/5/2018.
 */

public class FoodItem {

    private String name;
    private String desc;
    private String img;
    private int type;
    private int veg;
    private double price;
    private int qtyType;
    private int qty;

    public void setQtyType(int qtyType) {
        this.qtyType = qtyType;
    }

    public int getQtyType() {

        return qtyType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {

        return price;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {

        return desc;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {

        return img;
    }


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
