package com.labtasks.task;

import java.io.File;

public class Product {

    private String name;
    private double price;
    private String imagePath;

    public Product(String name, double price, File file) {
        this.name = name;
        this.price = price;
        this.imagePath = (file != null) ? file.getPath() : null;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public File getImageFile() {
        return imagePath != null ? new File(imagePath) : null;
    }
}