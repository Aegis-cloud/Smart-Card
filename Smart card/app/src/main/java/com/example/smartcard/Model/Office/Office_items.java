
package com.example.smartcard.Model.Office;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Office_items {

    @SerializedName("fees_id")
    @Expose
    private String feesId;
    @SerializedName("fees_name")
    @Expose
    private String feesName;
    @SerializedName("fees_desc")
    @Expose
    private String feesDesc;
    @SerializedName("fees_amount")
    @Expose
    private String feesAmount;
    @SerializedName("fees_date")
    @Expose
    private String feesDate;
    @SerializedName("fees_last_date")
    @Expose
    private String feesLastDate;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Office_items() {
    }

    /**
     * 
     * @param feesAmount
     * @param feesDate
     * @param feesLastDate
     * @param feesId
     * @param feesDesc
     * @param feesName
     * @param status
     */
    public Office_items(String feesId, String feesName, String feesDesc, String feesAmount, String feesDate, String feesLastDate, String status) {
        super();
        this.feesId = feesId;
        this.feesName = feesName;
        this.feesDesc = feesDesc;
        this.feesAmount = feesAmount;
        this.feesDate = feesDate;
        this.feesLastDate = feesLastDate;
        this.status = status;
    }

    public String getFeesId() {
        return feesId;
    }

    public void setFeesId(String feesId) {
        this.feesId = feesId;
    }

    public String getFeesName() {
        return feesName;
    }

    public void setFeesName(String feesName) {
        this.feesName = feesName;
    }

    public String getFeesDesc() {
        return feesDesc;
    }

    public void setFeesDesc(String feesDesc) {
        this.feesDesc = feesDesc;
    }

    public String getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(String feesAmount) {
        this.feesAmount = feesAmount;
    }

    public String getFeesDate() {
        return feesDate;
    }

    public void setFeesDate(String feesDate) {
        this.feesDate = feesDate;
    }

    public String getFeesLastDate() {
        return feesLastDate;
    }

    public void setFeesLastDate(String feesLastDate) {
        this.feesLastDate = feesLastDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
