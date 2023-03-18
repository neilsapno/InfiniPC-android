package cedric.ciel.infinipc.Lists;

import java.util.ArrayList;

import cedric.ciel.infinipc.Parts.CPU;

public class PartsData {
    private String imgUrl, t_specs1, t_specs2, t_specs3, t_specs4, t_specs5, AmazonLink;
    private double t_price;

    CPU cpu = new CPU();

    public PartsData() {
    }

    public PartsData(String imgUrl, String t_specs1, String t_specs2, String t_specs3, String t_specs4, String t_specs5, String amazonLink, double t_price) {
        this.imgUrl = imgUrl;
        this.t_specs1 = t_specs1;
        this.t_specs2 = t_specs2;
        this.t_specs3 = t_specs3;
        this.t_specs4 = t_specs4;
        this.t_specs5 = t_specs5;
        AmazonLink = amazonLink;
        this.t_price = t_price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getT_specs1() {
        return t_specs1;
    }

    public void setT_specs1(String t_specs1) {
        this.t_specs1 = t_specs1;
    }

    public String getT_specs2() {
        return t_specs2;
    }

    public void setT_specs2(String t_specs2) {
        this.t_specs2 = t_specs2;
    }

    public String getT_specs3() {
        return t_specs3;
    }

    public void setT_specs3(String t_specs3) {
        this.t_specs3 = t_specs3;
    }

    public String getT_specs4() {
        return t_specs4;
    }

    public void setT_specs4(String t_specs4) {
        this.t_specs4 = t_specs4;
    }

    public String getT_specs5() {
        return t_specs5;
    }

    public void setT_specs5(String t_specs5) {
        this.t_specs5 = t_specs5;
    }

    public String getAmazonLink() {
        return AmazonLink;
    }

    public void setAmazonLink(String amazonLink) {
        AmazonLink = amazonLink;
    }

    public double getT_price() {
        return t_price;
    }

    public void setT_price(double t_price) {
        this.t_price = t_price;
    }

    private static int lastPartsId = 0;

    public static ArrayList<PartsData> createPartsList(int numParts) {
        ArrayList<PartsData> contacts = new ArrayList<PartsData>();

        for (int i = 1; i <= numParts; i++) {
            contacts.add(new PartsData());
        }

        return contacts;
    }
}



