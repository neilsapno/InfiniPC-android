package cedric.ciel.infinipc.Parts;

import org.json.JSONException;
import org.json.JSONObject;

public class CPU {

    private String imgUrl, title, brand, model, speed, socket, amazonLink;
    private double price;

    public CPU() {
    }

    public CPU(String imgUrl, String title, String brand, String model, String speed, String socket, double price) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.brand = brand;
        this.model = model;
        this.speed = speed;
        this.socket = socket;
        this.amazonLink = amazonLink;
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getAmazonLink() {
        return amazonLink;
    }

    public void setAmazonLink(String amazonLink) {
        this.amazonLink = amazonLink;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setJSONResponse(JSONObject jsonObject){
        try
        {
            setImgUrl(jsonObject.getString("img"));
            setTitle(jsonObject.getString("title"));
            setBrand(jsonObject.getString("brand"));
            setModel(jsonObject.getString("model"));
            setSpeed(jsonObject.getString("speed"));
            setSocket(jsonObject.getString("socketType"));
            setAmazonLink(jsonObject.getString("link"));
            setPrice(jsonObject.getDouble("price"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
