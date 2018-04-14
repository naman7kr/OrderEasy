package com.oeasy.ordereasy.Modals;

/**
 * Created by Stan on 4/5/2018.
 */

public class FoodItem {
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    private int id;
    private String name;
    private String desc;
    private String img;
    private int type;
    private int category;
    private float price;
    private int qtyType;
    private String qty;
    private int fid;

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getFid() {

        return fid;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory() {

        return category;
    }
    public void setQtyType(int qtyType) {
        this.qtyType = qtyType;
    }

    public int getQtyType() {

        return qtyType;
    }

    public String getQty() {
        return qty;
    }

    public void setPrice(float price) {

        this.price = price;
    }

    public void setQty(String qty) {
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
