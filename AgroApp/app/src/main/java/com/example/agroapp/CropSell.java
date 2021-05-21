package com.example.agroapp;

public class CropSell {

    private String cropname;
    private String croptype;
    private String totalquantity;
    private String availablequantity;
    private String price;
    private String farmername;
    private String farmermobile;
    private String farmerid;
    private String selldate;

    public CropSell(String cropname, String croptype, String totalquantity, String availablequantity, String price, String farmername, String farmermobile, String farmerid, String selldate) {
        this.cropname = cropname;
        this.croptype = croptype;
        this.totalquantity = totalquantity;
        this.availablequantity = availablequantity;
        this.price = price;
        this.farmername = farmername;
        this.farmermobile = farmermobile;
        this.farmerid = farmerid;
        this.selldate = selldate;
    }

    public String getCropname() {
        return cropname;
    }

    public String getCroptype() {
        return croptype;
    }

    public String getTotalquantity() {
        return totalquantity;
    }

    public String getAvailablequantity() {
        return availablequantity;
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

    public String getSelldate() {
        return selldate;
    }
}
