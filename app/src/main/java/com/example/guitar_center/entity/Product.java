package com.example.guitar_center.entity;


public class Product {
    private String id_product;
    private String name_product;
    private int unit;
    private double price;
    private String image;
    private String description;

    public Product()
    {

    }
    public Product(String id_product, String name_product, int unit, double price, String image, String description) {
        this.id_product = id_product;
        this.name_product = name_product;
        this.unit = unit;
        this.price = price;
        this.image = image;
        this.description = description;
    }
    public String getId_product() {
        return id_product;
    }
    public void setId_product(String id_product) {
        this.id_product = id_product;
    }
    public String getName_product() {
        return name_product;
    }
    public void setName_product(String name_product) {
        this.name_product = name_product;
    }
    public int getUnit() {
        return unit;
    }
    public void setUnit(int unit) {
        this.unit = unit;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "Product [id_product=" + id_product + ", name_product=" + name_product + ", unit=" + unit + ", price="
                + price + ", image=" + image + "]";
    }


}
