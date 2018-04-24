package com.oeasy.ordereasy.Modals;

import org.json.JSONObject;

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
    private String cost;
    private int qtyType;
    private String qty;
    private int fid;
    private float rating;
    private int tag;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

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

    public JSONObject getJSONObject(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("food_id",getFid());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
