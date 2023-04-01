package cedric.ciel.infinipc.Parts;

public class Motherboard {

    String buildName, title, model, form, ramslot,socket, link, img;
    double price;

    public Motherboard(String buildName, String title, String model, String form, String ramslot, String socket,String link, String img, double price) {
        this.buildName = buildName;
        this.title = title;
        this.model = model;
        this.form = form;
        this.ramslot = ramslot;
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

    public String getForm() {
        return form;
    }

    public void setForm(String brand) {
        this.form = form;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getRamslot() {
        return ramslot;
    }

    public void setRamslot(String speed) {
        this.ramslot = ramslot;
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
