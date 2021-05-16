package com.example.agroapp;

public class CropTrade {
    private String cropname;
    private String quantity;
    private String amount;
    private String farmername;
    private String farmermobile;
    private String farmerid;
    private String suppliername;
    private String suppliermobile;
    private String supplierid;
    private String tradedate;

    public CropTrade(String cropname, String quantity, String amount, String farmername, String farmermobile, String farmerid, String suppliername, String suppliermobile, String supplierid, String tradedate) {
        this.cropname = cropname;
        this.quantity = quantity;
        this.amount = amount;
        this.farmername = farmername;
        this.farmermobile = farmermobile;
        this.farmerid = farmerid;
        this.suppliername = suppliername;
        this.suppliermobile = suppliermobile;
        this.supplierid = supplierid;
        this.tradedate = tradedate;
    }

    public String getCropname() {
        return cropname;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAmount() {
        return amount;
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

    public String getSuppliername() {
        return suppliername;
    }

    public String getSuppliermobile() {
        return suppliermobile;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public String getTradedate() {
        return tradedate;
    }
}
