
package com.example.smartcard.Model.Canteen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategory {

    @SerializedName("fid")
    @Expose
    private String fid;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("famount")
    @Expose
    private String famount;
    @SerializedName("fcategory")
    @Expose
    private String fcategory;
    @SerializedName("fquantity")
    @Expose
    private String fquantity;
    @SerializedName("fimage")
    @Expose
    private String fimage;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SubCategory() {
    }

    /**
     * 
     * @param fid
     * @param fname
     * @param fcategory
     * @param famount
     * @param fquantity
     * @param fimage
     */
    public SubCategory(String fid, String fname, String famount, String fcategory, String fquantity, String fimage) {
        super();
        this.fid = fid;
        this.fname = fname;
        this.famount = famount;
        this.fcategory = fcategory;
        this.fquantity = fquantity;
        this.fimage = fimage;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFamount() {
        return famount;
    }

    public void setFamount(String famount) {
        this.famount = famount;
    }

    public String getFcategory() {
        return fcategory;
    }

    public void setFcategory(String fcategory) {
        this.fcategory = fcategory;
    }

    public String getFquantity() {
        return fquantity;
    }

    public void setFquantity(String fquantity) {
        this.fquantity = fquantity;
    }

    public String getFimage() {
        return fimage;
    }

    public void setFimage(String fimage) {
        this.fimage = fimage;
    }

}
