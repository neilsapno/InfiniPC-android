package cedric.ciel.infinipc.Lists;

public class StoreParts {

    private String img_url, tv_partsTitle, tv_partsInfo1, tv_partsInfo2, tv_partsInfo3, tv_partsInfo4, partsID, link;
    private double tv_partsPrice;

    public StoreParts() {
    }

    public StoreParts(String img_url, String tv_partsTitle, String tv_partsInfo1, String tv_partsInfo2, String tv_partsInfo3, String tv_partsInfo4, String partsID, String link, double tv_partsPrice) {
        this.img_url = img_url;
        this.tv_partsTitle = tv_partsTitle;
        this.tv_partsInfo1 = tv_partsInfo1;
        this.tv_partsInfo2 = tv_partsInfo2;
        this.tv_partsInfo3 = tv_partsInfo3;
        this.tv_partsInfo4 = tv_partsInfo4;
        this.tv_partsPrice = tv_partsPrice;
        this.partsID = partsID;
        this.link = link;
    }

    public String getTv_partsTitle() {
        return tv_partsTitle;
    }

    public void setTv_partsTitle(String tv_partsTitle) {
        this.tv_partsTitle = tv_partsTitle;
    }

    public String getTv_partsInfo1() {
        return tv_partsInfo1;
    }

    public void setTv_partsInfo1(String tv_partsInfo1) {
        this.tv_partsInfo1 = tv_partsInfo1;
    }

    public String getTv_partsInfo2() {
        return tv_partsInfo2;
    }

    public void setTv_partsInfo2(String tv_partsInfo2) {
        this.tv_partsInfo2 = tv_partsInfo2;
    }

    public String getTv_partsInfo3() {
        return tv_partsInfo3;
    }

    public void setTv_partsInfo3(String tv_partsInfo3) {
        this.tv_partsInfo3 = tv_partsInfo3;
    }

    public String getTv_partsInfo4() {
        return tv_partsInfo4;
    }

    public void setTv_partsInfo4(String tv_partsInfo4) {
        this.tv_partsInfo4 = tv_partsInfo4;
    }

    public double getTv_partsPrice() {
        return tv_partsPrice;
    }

    public void setTv_partsPrice(double tv_partsPrice) {
        this.tv_partsPrice = tv_partsPrice;
    }

    public String getPartsID() {
        return partsID;
    }

    public void setPartsID(String partsID) {
        this.partsID = partsID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}


