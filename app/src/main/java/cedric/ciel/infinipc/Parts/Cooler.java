package cedric.ciel.infinipc.Parts;

public class Cooler {

    String buildName, title, brand, model, rpm, noiseLvl, link, img;
    double price;

    public Cooler(String buildName, String title, String brand, String model, String rpm, String noiseLvl, String link, String img, double price) {
        this.buildName = buildName;
        this.title = title;
        this.brand = brand;
        this.model = model;
        this.rpm = rpm;
        this.noiseLvl = noiseLvl;
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

    public String getrpm() {
        return rpm;
    }

    public void setrpm(String rpm) {
        this.rpm = rpm;
    }

    public String getnoiseLvl() {
        return noiseLvl;
    }

    public void setnoiseLvl(String noiseLvl) {
        this.noiseLvl = noiseLvl;
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
