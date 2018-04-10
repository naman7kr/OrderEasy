package com.oeasy.ordereasy.Modals;

/**
 * Created by Stan on 4/5/2018.
 */

public class FoodItem {

    private String name;
    private String desc;
    private String img;
    private int type;
    private int category;
    private float price;
    private int qtyType;
    private int qty;

    public void setQtyType(int qtyType) {
        this.qtyType = qtyType;
    }

    public int getQtyType() {

        return qtyType;
    }

    public int getQty() {
        return qty;
    }

    public void setPrice(float price) {

        this.price = price;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getPrice() {

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


    public void setcategory(int category) {
        this.category = category;
    }

    public int getcategory() {

        return category;
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
