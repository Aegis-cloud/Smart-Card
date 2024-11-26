
package com.example.smartcard.Model.Stationary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store_items {

    @SerializedName("store_cat_id")
    @Expose
    private String storeCatId;
    @SerializedName("store_cat_name")
    @Expose
    private String storeCatName;
    @SerializedName("store_cat_image")
    @Expose
    private String storeCatImage;
    @SerializedName("store_cat_desc")
    @Expose
    private String storeCatDesc;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Store_items() {
    }

    /**
     * 
     * @param storeCatId
     * @param storeCatName
     * @param storeCatDesc
     * @param storeCatImage
     */
    public Store_items(String storeCatId, String storeCatName, String storeCatImage, String storeCatDesc) {
        super();
        this.storeCatId = storeCatId;
        this.storeCatName = storeCatName;
        this.storeCatImage = storeCatImage;
        this.storeCatDesc = storeCatDesc;
    }

    public String getStoreCatId() {
        return storeCatId;
    }

    public void setStoreCatId(String storeCatId) {
        this.storeCatId = storeCatId;
    }

    public String getStoreCatName() {
        return storeCatName;
    }

    public void setStoreCatName(String storeCatName) {
        this.storeCatName = storeCatName;
    }

    public String getStoreCatImage() {
        return storeCatImage;
    }

    public void setStoreCatImage(String storeCatImage) {
        this.storeCatImage = storeCatImage;
    }

    public String getStoreCatDesc() {
        return storeCatDesc;
    }

    public void setStoreCatDesc(String storeCatDesc) {
        this.storeCatDesc = storeCatDesc;
    }

}
