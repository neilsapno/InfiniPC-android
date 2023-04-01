package cedric.ciel.infinipc.Parts;

public class RAM {

    String buildName, title, model, size, quantity, type, link, img;
    double price;

    public RAM(String buildName, String title, String model, String size, String quantity, String type, String link, String img, double price) {
        this.buildName = buildName;
        this.title = title;
        this.model = model;
        this.size = size;
        this.quantity = quantity;
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

    public String getSize() {
        return size;
    }

    public void setSize(String brand) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String speed) {
        this.quantity = quantity;
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
