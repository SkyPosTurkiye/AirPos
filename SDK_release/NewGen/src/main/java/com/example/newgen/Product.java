/*

Project     : NewGen Retail Sales Automation
Owner       : SkyPos Technology CO.
Platform    : Android
Language    : Java

Module      : Product Class
Description : Definition of product class

Extension History:


*/

package com.example.newgen;

public class Product {

    private String name;
    private String barcode;
    private Double price;
    private Integer quantity;
    private Integer itemId;

    public Product() {
        super();
    }

    public Product(String name, String barcode, Double price, Integer quantity, Integer itemId) {
        super();
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.quantity = quantity;
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "Store [name=" + name + ", barcode=" + barcode + ", price=" + price
                + ", quantity=" + quantity + ", itemId=" + itemId + "]\n";
    }
}
