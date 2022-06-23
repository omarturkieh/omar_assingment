package com.example.myassignment.model;

public class Qutlay {
    private Integer material_id;
    private Integer owner_id;
    private Integer price;
    private String date;
    private String description;

    public Qutlay(Integer material_id, Integer owner_id, Integer price, String date, String description) {
        this.material_id = material_id;
        this.owner_id = owner_id;
        this.price = price;
        this.date = date;
        this.description = description;
    }

    public Integer getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(Integer material_id) {
        this.material_id = material_id;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
