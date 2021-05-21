package com.example.agroapp;

public class CropBuy {

    private String cropname;
    private String quantity;
    private String price;
    private String cropid;
    private String farmername;
    private String farmermobile;
    private String farmerid;
    private String selldate;

    public CropBuy(String cropname, String quantity, String price, String cropid, String farmername, String farmermobile, String farmerid, String selldate) {
        this.cropname = cropname;
        this.quantity = quantity;
        this.price = price;
        this.cropid = cropid;
        this.farmername = farmername;
        this.farmermobile = farmermobile;
        this.farmerid = farmerid;
        this.selldate = selldate;
    }

    public String getCropname() {
        return cropname;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getFarmername() {
        return farmername;
    }

    public String getFarmermobile() {
        return farmermobile;
    }

    public String getFarmerid() {
        return farmerid;
    }

    public String getCropid() {
        return cropid;
    }

    public String getSelldate() {
        return selldate;
    }
}
