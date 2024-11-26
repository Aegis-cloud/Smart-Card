
package com.example.smartcard.Model.Stationary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product_item {

    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_amount")
    @Expose
    private String itemAmount;
    @SerializedName("item_category")
    @Expose
    private String itemCategory;
    @SerializedName("item_quantity")
    @Expose
    private String itemQuantity;
    @SerializedName("item_image")
    @Expose
    private String itemImage;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Product_item() {
    }

    /**
     * 
     * @param itemId
     * @param itemName
     * @param itemQuantity
     * @param itemAmount
     * @param itemCategory
     * @param itemImage
     */
    public Product_item(String itemId, String itemName, String itemAmount, String itemCategory, String itemQuantity, String itemImage) {
        super();
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.itemCategory = itemCategory;
        this.itemQuantity = itemQuantity;
        this.itemImage = itemImage;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

}
