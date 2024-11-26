
package com.example.smartcard.Model.Canteen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category_items {

    @SerializedName("canteen_cat_id")
    @Expose
    private String canteenCatId;
    @SerializedName("canteen_cat_name")
    @Expose
    private String canteenCatName;
    @SerializedName("canteen_cat_image")
    @Expose
    private String canteenCatImage;
    @SerializedName("canteen_cat_desc")
    @Expose
    private String canteenCatDesc;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Category_items() {
    }

    /**
     * 
     * @param canteenCatDesc
     * @param canteenCatId
     * @param canteenCatName
     * @param canteenCatImage
     */
    public Category_items(String canteenCatId, String canteenCatName, String canteenCatImage, String canteenCatDesc) {
        super();
        this.canteenCatId = canteenCatId;
        this.canteenCatName = canteenCatName;
        this.canteenCatImage = canteenCatImage;
        this.canteenCatDesc = canteenCatDesc;
    }

    public String getCanteenCatId() {
        return canteenCatId;
    }

    public void setCanteenCatId(String canteenCatId) {
        this.canteenCatId = canteenCatId;
    }

    public String getCanteenCatName() {
        return canteenCatName;
    }

    public void setCanteenCatName(String canteenCatName) {
        this.canteenCatName = canteenCatName;
    }

    public String getCanteenCatImage() {
        return canteenCatImage;
    }

    public void setCanteenCatImage(String canteenCatImage) {
        this.canteenCatImage = canteenCatImage;
    }

    public String getCanteenCatDesc() {
        return canteenCatDesc;
    }

    public void setCanteenCatDesc(String canteenCatDesc) {
        this.canteenCatDesc = canteenCatDesc;
    }

}
