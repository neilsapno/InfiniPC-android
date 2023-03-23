package cedric.ciel.infinipc.Lists;

public class BuildData {
    private String build_name, cpu_name, cpu_cooler, motherboard, memory, storage, video_card, pc_case, power_supply, case_fan, build_img;
    private int ram_count, watts, productId;
    double est_price;

    public BuildData(String build_name, int ram_count, int watts, double est_price) {
        this.productId = productId;
        this.build_name = build_name;
        this.watts = watts;
        this.ram_count = ram_count;
        this.est_price = est_price;
    }

    public BuildData(String build_name, String cpu_name, String cpu_cooler, String motherboard, String memory, String storage, String video_card,
                     String pc_case, String power_supply, String case_fan, int ram_count, int watts, double est_price, String build_img) {
        this.productId = productId;
        this.build_name = build_name;
        this.cpu_name = cpu_name;
        this.cpu_cooler = cpu_cooler;
        this.motherboard = motherboard;
        this.memory = memory;
        this.storage = storage;
        this.video_card = video_card;
        this.pc_case = pc_case;
        this.power_supply = power_supply;
        this.case_fan = case_fan;
        this.ram_count = ram_count;
        this.watts = watts;
        this.build_img = build_img;
        this.est_price = est_price;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    public String getCpu_name() {
        return cpu_name;
    }

    public void setCpu_name(String cpu_name) {
        this.cpu_name = cpu_name;
    }

    public String getCpu_cooler() {
        return cpu_cooler;
    }

    public void setCpu_cooler(String cpu_cooler) {
        this.cpu_cooler = cpu_cooler;
    }

    public String getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getVideo_card() {
        return video_card;
    }

    public void setVideo_card(String video_card) {
        this.video_card = video_card;
    }

    public String getPc_case() {
        return pc_case;
    }

    public void setPc_case(String pc_case) {
        this.pc_case = pc_case;
    }

    public String getPower_supply() {
        return power_supply;
    }

    public void setPower_supply(String power_supply) {
        this.power_supply = power_supply;
    }

    public String getCase_fan() {
        return case_fan;
    }

    public void setCase_fan(String case_fan) {
        this.case_fan = case_fan;
    }

    public int getRam_count() {
        return ram_count;
    }

    public void setRam_count(int ram_count) {
        this.ram_count = ram_count;
    }

    public String getBuild_img() {
        return build_img;
    }

    public void setBuild_img(String build_img) {
        this.build_img = build_img;
    }

    public double getEst_price() {
        return est_price;
    }

    public void setEst_price(double est_price) {
        this.est_price = est_price;
    }

    public int getWatts() {
        return watts;
    }

    public void setWatts(int watts) {
        this.watts = watts;
    }
}


