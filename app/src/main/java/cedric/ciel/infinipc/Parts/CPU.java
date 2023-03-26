package cedric.ciel.infinipc.Parts;

public class CPU {

    String buildName, title, brand, model, speed, socket, link, img;
    double price;

    public CPU(String buildName, String title, String brand, String model, String speed, String socket, String link, String img, double price) {
        this.buildName = buildName;
        this.title = title;
        this.brand = brand;
        this.model = model;
        this.speed = speed;
        this.socket = socket;
        this.link = link;
        this.img = img;
        this.price = price;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
