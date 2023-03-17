package cedric.ciel.infinipc.Lists;

public class BuildData {
    private String build_name, cpu_name, cpu_cooler, motherboard, memory, storage, video_card, pc_case, power_supply, case_fan, monitor;
    private int ram_count;
    private byte[] build_img, cpu_img, cpu_cooler_img, motherboard_img, memory_img, storage_img, video_card_img, pc_case_img, power_supply_img, case_fan_img, monitor_img;
    double est_price;

    public BuildData(String build_name, String cpu_name, String memory, double est_price) {
        this.build_name = build_name;
        this.cpu_name = cpu_name;
        this.memory = memory;
        this.est_price = est_price;
    }

    public BuildData(String build_name, String cpu_name, String cpu_cooler, String motherboard, String memory, String storage, String video_card, String pc_case, String power_supply, String case_fan, String monitor, int ram_count, byte[] build_img, byte[] cpu_img, byte[] cpu_cooler_img, byte[] motherboard_img, byte[] memory_img, byte[] storage_img, byte[] video_card_img, byte[] pc_case_img, byte[] power_supply_img, byte[] case_fan_img, byte[] monitor_img, double est_price) {
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
        this.monitor = monitor;
        this.ram_count = ram_count;
        this.build_img = build_img;
        this.cpu_img = cpu_img;
        this.cpu_cooler_img = cpu_cooler_img;
        this.motherboard_img = motherboard_img;
        this.memory_img = memory_img;
        this.storage_img = storage_img;
        this.video_card_img = video_card_img;
        this.pc_case_img = pc_case_img;
        this.power_supply_img = power_supply_img;
        this.case_fan_img = case_fan_img;
        this.monitor_img = monitor_img;
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

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public int getRam_count() {
        return ram_count;
    }

    public void setRam_count(int ram_count) {
        this.ram_count = ram_count;
    }

    public byte[] getBuild_img() {
        return build_img;
    }

    public void setBuild_img(byte[] build_img) {
        this.build_img = build_img;
    }

    public byte[] getCpu_img() {
        return cpu_img;
    }

    public void setCpu_img(byte[] cpu_img) {
        this.cpu_img = cpu_img;
    }

    public byte[] getCpu_cooler_img() {
        return cpu_cooler_img;
    }

    public void setCpu_cooler_img(byte[] cpu_cooler_img) {
        this.cpu_cooler_img = cpu_cooler_img;
    }

    public byte[] getMotherboard_img() {
        return motherboard_img;
    }

    public void setMotherboard_img(byte[] motherboard_img) {
        this.motherboard_img = motherboard_img;
    }

    public byte[] getMemory_img() {
        return memory_img;
    }

    public void setMemory_img(byte[] memory_img) {
        this.memory_img = memory_img;
    }

    public byte[] getStorage_img() {
        return storage_img;
    }

    public void setStorage_img(byte[] storage_img) {
        this.storage_img = storage_img;
    }

    public byte[] getVideo_card_img() {
        return video_card_img;
    }

    public void setVideo_card_img(byte[] video_card_img) {
        this.video_card_img = video_card_img;
    }

    public byte[] getPc_case_img() {
        return pc_case_img;
    }

    public void setPc_case_img(byte[] pc_case_img) {
        this.pc_case_img = pc_case_img;
    }

    public byte[] getPower_supply_img() {
        return power_supply_img;
    }

    public void setPower_supply_img(byte[] power_supply_img) {
        this.power_supply_img = power_supply_img;
    }

    public byte[] getCase_fan_img() {
        return case_fan_img;
    }

    public void setCase_fan_img(byte[] case_fan_img) {
        this.case_fan_img = case_fan_img;
    }

    public byte[] getMonitor_img() {
        return monitor_img;
    }

    public void setMonitor_img(byte[] monitor_img) {
        this.monitor_img = monitor_img;
    }

    public double getEst_price() {
        return est_price;
    }

    public void setEst_price(double est_price) {
        this.est_price = est_price;
    }
}


