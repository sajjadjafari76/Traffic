package irstit.transport.PhonePay;

public class TransctionModel {

    private String checkImage;
    private String TransctionNumber;
    private String  price;
    private  String date;

    public void setCheckImage(String checkImage) {
        this.checkImage = checkImage;
    }

    public void setTransctionNumber(String transctionNumber) {
        TransctionNumber = transctionNumber;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCheckImage() {
        return checkImage;
    }

    public String getTransctionNumber() {
        return TransctionNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
