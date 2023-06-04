package cedric.ciel.infinipc.Parts;

public class CaseFan {

    String buildName, title, model, rpm, noiseLvl, airFlow, link, img;
    double price;

    public CaseFan(String buildName, String title, String model, String rpm, String noiseLvl, String airFlow, String link, String img, double price) {
        this.buildName = buildName;
        this.title = title;
        this.model = model;
        this.rpm = rpm;
        this.noiseLvl = noiseLvl;
        this.airFlow = airFlow;
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

    public String getRpm() {
        return rpm;
    }

    public void setRpm(String rpm) {
        this.rpm = rpm;
    }

    public String getNoiseLvl() {
        return noiseLvl;
    }

    public void setNoiseLvl(String noiseLvl) {
        this.noiseLvl = noiseLvl;
    }

    public String getAirFlow() {
        return airFlow;
    }

    public void setAirFlow(String airFlow) {
        this.airFlow = airFlow;
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
