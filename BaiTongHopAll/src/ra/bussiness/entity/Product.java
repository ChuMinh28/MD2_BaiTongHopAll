package ra.bussiness.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private String productID;
    private String productName;
    private float price;
    private Catalog catalog;
    private boolean productStatus;

    public Product() {
    }

    public Product(String productID, String productName, float price, Catalog catalog, boolean productStatus) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.catalog = catalog;
        this.productStatus = productStatus;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }
}
