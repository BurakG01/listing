package com.listing.listingservice.domain;


import java.util.UUID;

public class Listing {
    private String id;
    private String merchantId;
    private double price;
    private int stock;
    private String sku;

    public Listing() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void decreaseStock(int quantity) {

        if (stock < quantity || quantity <= 0) {
            throw new IllegalArgumentException("Quantity  cannot be greater then stock and quantity cannot be less then or equal to zero");
        }
        int remainingStock = stock - quantity;
        this.setStock(remainingStock);
    }
}
