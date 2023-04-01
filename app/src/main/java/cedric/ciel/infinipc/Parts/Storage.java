package cedric.ciel.infinipc.Parts;

public class Storage {

    String buildName, title, model, cacheSize, Interface, type, link, img;
    double price;

    public Storage(String buildName, String title, String model, String cacheSize, String Interface, String type, String link, String img, double price) {
        this.buildName = buildName;
        this.title = title;
        this.model = model;
        this.cacheSize = cacheSize;
        this.Interface = Interface;
        this.type = type;
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

    public String getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(String brand) {
        this.cacheSize = cacheSize;
    }

    public String getInterface() {
        return Interface;
    }

    public void setInterface(String speed) {
        this.Interface = Interface;
    }

    public String getType() {
        return type;
    }

    public void setType(String socket) {
        this.type = type;
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
