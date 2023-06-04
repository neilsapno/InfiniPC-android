package cedric.ciel.infinipc.Parts;

public class GPU {

    String buildName, title, model, speed, Interface, vram, link, img;
    double price;

    public GPU(String buildName, String title, String model, String speed, String Interface, String vram, String link, String img, double price) {
        this.buildName = buildName;
        this.title = title;
        this.model = model;
        this.speed = speed;
        this.Interface = Interface;
        this.vram = vram;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpeed() {
        return speed;
    }
    public void setSpeed(String brand) {
        this.speed = speed;
    }
    public String getInterface() {
        return Interface;
    }

    public void setInterface(String speed) { this.Interface = speed; }

    public String getVram() {
        return vram;
    }

    public void setVram(String socket) {
        this.vram = socket;
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
