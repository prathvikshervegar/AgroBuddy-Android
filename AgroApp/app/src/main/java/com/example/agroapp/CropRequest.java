package com.example.agroapp;

public class CropRequest {

    private String cropname;
    private String croptype;
    private String quantity;
    private String buyername;
    private String buyermobile;
    private String buyeraddress;
    private String buyerid;
    private String requestdate;
    private String acceptstatus;

    public CropRequest(String cropname, String croptype, String quantity, String buyername, String buyermobile, String buyeraddress, String buyerid, String requestdate, String acceptstatus) {
        this.cropname = cropname;
        this.croptype = croptype;
        this.quantity = quantity;
        this.buyername = buyername;
        this.buyermobile = buyermobile;
        this.buyeraddress = buyeraddress;
        this.buyerid = buyerid;
        this.requestdate = requestdate;
        this.acceptstatus = acceptstatus;
    }

    public String getCropname() {
        return cropname;
    }

    public String getCroptype() {
        return croptype;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getBuyername() {
        return buyername;
    }

    public String getBuyermobile() {
        return buyermobile;
    }

    public String getBuyeraddress() {
        return buyeraddress;
    }

    public String getBuyerid() {
        return buyerid;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public String getAcceptstatus() {
        return acceptstatus;
    }
}
