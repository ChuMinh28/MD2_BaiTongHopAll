package ra.bussiness.entity;

import java.io.Serializable;

public class Catalog implements Serializable {
    private int catalogID;
    private String catalogName;
    private boolean catalogStatus;

    public Catalog() {
    }

    public Catalog(int catalogID, String catalogName, boolean catalogStatus) {
        this.catalogID = catalogID;
        this.catalogName = catalogName;
        this.catalogStatus = catalogStatus;
    }

    public int getCatalogID() {
        return catalogID;
    }

    public void setCatalogID(int catalogID) {
        this.catalogID = catalogID;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public boolean isCatalogStatus() {
        return catalogStatus;
    }

    public void setCatalogStatus(boolean catalogStatus) {
        this.catalogStatus = catalogStatus;
    }
}
