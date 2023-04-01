package cedric.ciel.infinipc.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import cedric.ciel.infinipc.Lists.BuildData;
import cedric.ciel.infinipc.Parts.CPU;
import cedric.ciel.infinipc.Parts.Cooler;
import cedric.ciel.infinipc.Parts.Motherboard;
import cedric.ciel.infinipc.Parts.RAM;
import cedric.ciel.infinipc.Parts.Storage;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "builds";
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "mybuilds";
    private static final String TBL_CPU = "cpu";
    private static final String TBL_COOLER = "cooler";
    private static final String TBL_MOBO = "mobo";
    private static final String TBL_RAM = "ram";
    private static final String TBL_STORAGE = "storage";
    private static final String TBL_GPU = "gpu";

    private static final String ID_COL = "id";

    private static final String BUILD_NAME_COL = "build_name";

    private static final String PROCESSOR_COL = "cpu";

    private static final String COOLER_COL = "cpu_cooler";

    private static final String MOTHERBOARD_COL = "motherboard";
    private static final String MEMORY_COL = "ram";
    private static final String STORAGE_COL = "storage";
    private static final String VIDEOCARD_COL = "gpu";
    private static final String CASE_COL = "pc_case";
    private static final String POWERSUPPLY_COL = "psu";
    private static final String CASEFAN_COL = "casefan";
    private static final String MONITOR_COL = "monitor";
    private static final String RAMSPEED_COL = "ramspeed";
    private static final String WATTS_COL = "watts";
    private static final String PRICE_COL = "price";
    private static final String BUILDIMG_COL = "build_image";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BUILD_NAME_COL + " TEXT,"
                + PROCESSOR_COL + " TEXT,"
                + COOLER_COL + " TEXT,"
                + MOTHERBOARD_COL + " TEXT,"
                + MEMORY_COL + " TEXT,"
                + STORAGE_COL + " TEXT,"
                + VIDEOCARD_COL + " TEXT,"
                + CASE_COL + " TEXT,"
                + POWERSUPPLY_COL + " TEXT,"
                + CASEFAN_COL + " TEXT,"
                + RAMSPEED_COL + " INTEGER,"
                + WATTS_COL + " INTEGER,"
                + PRICE_COL + " REAL,"
                + BUILDIMG_COL + " TEXT)";

        String query2 = "CREATE TABLE IF NOT EXISTS " + TBL_CPU + " ("
                + BUILD_NAME_COL + " TEXT PRIMARY KEY,"
                + "title TEXT,"
                + "brand TEXT,"
                + "model TEXT,"
                + "speed TEXT,"
                + "socket TEXT,"
                + "link TEXT,"
                + "img TEXT,"
                + PRICE_COL + " REAL)";

        String query3 = "CREATE TABLE IF NOT EXISTS " + TBL_COOLER + " ("
                + BUILD_NAME_COL + " TEXT PRIMARY KEY,"
                + "title TEXT,"
                + "brand TEXT,"
                + "model TEXT,"
                + "rpm TEXT,"
                + "noiselvl TEXT,"
                + "link TEXT,"
                + "img TEXT,"
                + PRICE_COL + " REAL)";

        String query4 = "CREATE TABLE IF NOT EXISTS " + TBL_MOBO + " ("
                + BUILD_NAME_COL + " TEXT PRIMARY KEY,"
                + "title TEXT,"
                + "model TEXT,"
                + "form TEXT,"
                + "ramslot TEXT,"
                + "socket TEXT,"
                + "link TEXT,"
                + "img TEXT,"
                + PRICE_COL + " REAL)";

        String query5 = "CREATE TABLE IF NOT EXISTS " + TBL_RAM + " ("
                + BUILD_NAME_COL + " TEXT PRIMARY KEY,"
                + "title TEXT,"
                + "model TEXT,"
                + "size TEXT,"
                + "quantity TEXT,"
                + "type TEXT,"
                + "link TEXT,"
                + "img TEXT,"
                + PRICE_COL + " REAL)";

        String query6 = "CREATE TABLE IF NOT EXISTS " + TBL_STORAGE + " ("
                + BUILD_NAME_COL + " TEXT PRIMARY KEY,"
                + "title TEXT,"
                + "model TEXT,"
                + "cachesize TEXT,"
                + "interface TEXT,"
                + "type TEXT,"
                + "link TEXT,"
                + "img TEXT,"
                + PRICE_COL + " REAL)";

        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);
        db.execSQL(query6);
    }

    public void addNewBuild(String buildName, String CPU, String Cooler, String Mobo, String RAM, String Storage,
                            String GPU, String Case, String PSU, String CaseFan, int RAMSpeed, int Watts, double Price, String BuildImageUrl) {
        SQLiteDatabase db = this.getWritableDatabase();

        BuildData buildData = new BuildData(buildName, CPU, Cooler, Mobo, RAM, Storage, GPU, Case, PSU, CaseFan, RAMSpeed, Watts, Price, BuildImageUrl);

        ContentValues values = new ContentValues();

        values.put(BUILD_NAME_COL, buildData.getBuild_name());
        values.put(PROCESSOR_COL, buildData.getCpu_name());
        values.put(COOLER_COL, buildData.getCpu_cooler());
        values.put(MOTHERBOARD_COL, buildData.getMotherboard());
        values.put(MEMORY_COL, buildData.getMemory());
        values.put(STORAGE_COL, buildData.getStorage());
        values.put(VIDEOCARD_COL, buildData.getVideo_card());
        values.put(CASE_COL, buildData.getPc_case());
        values.put(POWERSUPPLY_COL, buildData.getPower_supply());
        values.put(CASEFAN_COL, buildData.getCase_fan());
        values.put(RAMSPEED_COL, buildData.getRam_count());
        values.put(WATTS_COL, buildData.getWatts());
        values.put(PRICE_COL, buildData.getEst_price());
        values.put(BUILDIMG_COL, buildData.getBuild_img());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void editBuild(String originalBuildName, String buildName, String CPU, String Cooler, String Mobo, String RAM, String Storage,
                            String GPU, String Case, String PSU, String CaseFan, int RAMSpeed, int Watts, double Price, String BuildImageUrl) {
        SQLiteDatabase db = this.getWritableDatabase();

        BuildData buildData = new BuildData(buildName, CPU, Cooler, Mobo, RAM, Storage, GPU, Case, PSU, CaseFan, RAMSpeed, Watts, Price, BuildImageUrl);

        ContentValues values = new ContentValues();

        values.put(BUILD_NAME_COL, buildData.getBuild_name());
        values.put(PROCESSOR_COL, buildData.getCpu_name());
        values.put(COOLER_COL, buildData.getCpu_cooler());
        values.put(MOTHERBOARD_COL, buildData.getMotherboard());
        values.put(MEMORY_COL, buildData.getMemory());
        values.put(STORAGE_COL, buildData.getStorage());
        values.put(VIDEOCARD_COL, buildData.getVideo_card());
        values.put(CASE_COL, buildData.getPc_case());
        values.put(POWERSUPPLY_COL, buildData.getPower_supply());
        values.put(CASEFAN_COL, buildData.getCase_fan());
        values.put(RAMSPEED_COL, buildData.getRam_count());
        values.put(WATTS_COL, buildData.getWatts());
        values.put(PRICE_COL, buildData.getEst_price());
        values.put(BUILDIMG_COL, buildData.getBuild_img());

        db.update(TABLE_NAME, values, "build_name=?",new String[]{originalBuildName});
        db.close();
    }

    public void deleteBuild(String buildName){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "build_name=?", new String[]{buildName});
        db.close();
    }

    //get all builds as list
    public ArrayList<BuildData> getBuilds(ArrayList<BuildData> buildData)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorBuilds
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

//        ArrayList<BuildData> buildDataArrayList
//                = new ArrayList<>();
        if (cursorBuilds.moveToFirst()) {
            do {
                buildData.add(new BuildData(
                        cursorBuilds.getString(1),
                        cursorBuilds.getString(2),
                        cursorBuilds.getInt(11),
                        cursorBuilds.getInt(12),
                        cursorBuilds.getDouble(13)));
            } while (cursorBuilds.moveToNext());
        }
        cursorBuilds.close();
        return buildData;
    }

    //add selected cpu
    public void addCPU(String buildname, String title, String brand, String model, String speed, String socket, String link, String img, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        CPU cpu = new CPU(buildname, title, brand, model, speed, socket, link, img, price);

        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BUILD_NAME_COL, cpu.getBuildName());
        values.put("title", cpu.getTitle());
        values.put("brand", cpu.getBrand());
        values.put("model", cpu.getModel());
        values.put("speed", cpu.getSpeed());
        values.put("socket", cpu.getSocket());
        values.put("link", cpu.getLink());
        values.put("img", cpu.getImg());
        values.put(PRICE_COL, cpu.getPrice());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TBL_CPU, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    //add blank cpu
    public void addCPU(String buildname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BUILD_NAME_COL, buildname);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TBL_CPU, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void updateCPU(String originalBuildName, String buildname, String title, String brand, String model, String speed, String socket, String link, String img, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        CPU cpu = new CPU(buildname, title, brand, model, speed, socket, link, img, price);

        ContentValues values = new ContentValues();

        values.put(BUILD_NAME_COL, cpu.getBuildName());
        values.put("title", cpu.getTitle());
        values.put("brand", cpu.getBrand());
        values.put("model", cpu.getModel());
        values.put("speed", cpu.getSpeed());
        values.put("socket", cpu.getSocket());
        values.put("link", cpu.getLink());
        values.put("img", cpu.getImg());
        values.put(PRICE_COL, cpu.getPrice());

        db.update(TBL_CPU, values, "build_name=?", new String[]{originalBuildName});

        db.close();
    }

    public void updateCPU(String originalBuildName, String buildname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(BUILD_NAME_COL, buildname);

        db.update(TBL_CPU, values, "build_name=?", new String[]{originalBuildName});

        db.close();
    }

    //add selected cooler
    public void addCooler(String buildname, String title, String brand, String model, String speed, String socket, String link, String img, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        Cooler cooler = new Cooler(buildname, title, brand, model, speed, socket, link, img, price);
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BUILD_NAME_COL, cooler.getBuildName());
        values.put("title", cooler.getTitle());
        values.put("brand", cooler.getBrand());
        values.put("model", cooler.getModel());
        values.put("rpm", cooler.getrpm());
        values.put("noiselvl", cooler.getnoiseLvl());
        values.put("link", cooler.getLink());
        values.put("img", cooler.getImg());
        values.put(PRICE_COL, cooler.getPrice());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TBL_COOLER, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    //add blank cooler
    public void addCooler(String buildname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BUILD_NAME_COL, buildname);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TBL_COOLER, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void updateCooler(String originalBuildName, String buildname, String title, String brand, String model, String speed, String socket, String link, String img, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        Cooler cooler = new Cooler(buildname, title, brand, model, speed, socket, link, img, price);

        ContentValues values = new ContentValues();

        values.put(BUILD_NAME_COL, cooler.getBuildName());
        values.put("title", cooler.getTitle());
        values.put("brand", cooler.getBrand());
        values.put("model", cooler.getModel());
        values.put("rpm", cooler.getrpm());
        values.put("noiselvl", cooler.getnoiseLvl());
        values.put("link", cooler.getLink());
        values.put("img", cooler.getImg());
        values.put(PRICE_COL, cooler.getPrice());

        db.update(TBL_COOLER, values, "build_name=?", new String[]{originalBuildName});

        db.close();
    }

    public void updateCooler(String originalBuildName, String buildname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(BUILD_NAME_COL, buildname);

        db.update(TBL_COOLER, values, "build_name=?", new String[]{originalBuildName});

        db.close();
    }


    //add selected mobo
    public void addMotherboard(String buildname, String title, String model, String form, String ramslot, String socket, String link, String img, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        Motherboard mobo = new Motherboard(buildname, title, model, form, ramslot, socket, link, img, price);
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BUILD_NAME_COL, mobo.getBuildName());
        values.put("title", mobo.getTitle());
        values.put("model", mobo.getModel());
        values.put("form", mobo.getForm());
        values.put("ramslot", mobo.getRamslot());
        values.put("socket", mobo.getSocket());
        values.put("link", mobo.getLink());
        values.put("img", mobo.getImg());
        values.put(PRICE_COL, mobo.getPrice());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TBL_MOBO, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    //add blank mobo
    public void addMotherboard(String buildname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BUILD_NAME_COL, buildname);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TBL_MOBO, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void updateMotherboard(String originalBuildName, String buildname, String title, String model, String form, String ramslot, String socket, String link, String img, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        Motherboard mobo = new Motherboard(buildname, title, model, form, ramslot, socket, link, img, price);

        ContentValues values = new ContentValues();

        values.put(BUILD_NAME_COL, mobo.getBuildName());
        values.put("title", mobo.getTitle());
        values.put("model", mobo.getModel());
        values.put("form", mobo.getForm());
        values.put("ramslot", mobo.getRamslot());
        values.put("socket", mobo.getSocket());
        values.put("link", mobo.getLink());
        values.put("img", mobo.getImg());
        values.put(PRICE_COL, mobo.getPrice());

        db.update(TBL_MOBO, values, "build_name=?", new String[]{originalBuildName});

        db.close();
    }

    public void updateMotherboard(String originalBuildName, String buildname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(BUILD_NAME_COL, buildname);

        db.update(TBL_MOBO, values, "build_name=?", new String[]{originalBuildName});

        db.close();
    }

    //add selected ram
    public void addRAM(String buildname, String title, String model, String size, String quantity, String type, String link, String img, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        RAM ram = new RAM(buildname, title, model, size, quantity, type, link, img, price);
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BUILD_NAME_COL, ram.getBuildName());
        values.put("title", ram.getTitle());
        values.put("model", ram.getModel());
        values.put("size", ram.getSize());
        values.put("quantity", ram.getQuantity());
        values.put("type", ram.getType());
        values.put("link", ram.getLink());
        values.put("img", ram.getImg());
        values.put(PRICE_COL, ram.getPrice());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TBL_RAM, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    //add blank ram
    public void addRAM(String buildname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BUILD_NAME_COL, buildname);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TBL_RAM, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void updateRAM(String originalBuildName, String buildname, String title, String model, String size, String quantity, String type, String link, String img, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        RAM ram = new RAM(buildname, title, model, size, quantity, type, link, img, price);

        ContentValues values = new ContentValues();

        values.put(BUILD_NAME_COL, ram.getBuildName());
        values.put("title", ram.getTitle());
        values.put("model", ram.getModel());
        values.put("size", ram.getSize());
        values.put("quantity", ram.getQuantity());
        values.put("type", ram.getType());
        values.put("link", ram.getLink());
        values.put("img", ram.getImg());
        values.put(PRICE_COL, ram.getPrice());

        db.update(TBL_RAM, values, "build_name=?", new String[]{originalBuildName});

        db.close();
    }

    public void updateRAM(String originalBuildName, String buildname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(BUILD_NAME_COL, buildname);

        db.update(TBL_RAM, values, "build_name=?", new String[]{originalBuildName});

        db.close();
    }

    //add selected ram
    public void addStorage(String buildname, String title, String model, String cacheSize, String Interface, String type, String link, String img, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        Storage storage = new Storage(buildname, title, model, cacheSize, Interface, type, link, img, price);
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BUILD_NAME_COL, storage.getBuildName());
        values.put("title", storage.getTitle());
        values.put("model", storage.getModel());
        values.put("cachesize", storage.getCacheSize());
        values.put("interface", storage.getInterface());
        values.put("type", storage.getType());
        values.put("link", storage.getLink());
        values.put("img", storage.getImg());
        values.put(PRICE_COL, storage.getPrice());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TBL_STORAGE, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    //add blank ram
    public void addStorage(String buildname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BUILD_NAME_COL, buildname);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TBL_STORAGE, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void updateStorage(String originalBuildName, String buildname, String title, String model, String cacheSize, String Interface, String type, String link, String img, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        Storage storage = new Storage(buildname, title, model, cacheSize, Interface, type, link, img, price);
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BUILD_NAME_COL, storage.getBuildName());
        values.put("title", storage.getTitle());
        values.put("model", storage.getModel());
        values.put("cachesize", storage.getCacheSize());
        values.put("interface", storage.getInterface());
        values.put("type", storage.getType());
        values.put("link", storage.getLink());
        values.put("img", storage.getImg());
        values.put(PRICE_COL, storage.getPrice());

        db.update(TBL_STORAGE, values, "build_name=?", new String[]{originalBuildName});

        db.close();
    }

    public void updateStorage(String originalBuildName, String buildname){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(BUILD_NAME_COL, buildname);

        db.update(TBL_STORAGE, values, "build_name=?", new String[]{originalBuildName});

        db.close();
    }

    public ArrayList<CPU> getCPU(ArrayList<CPU> cpu, String buildname)
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorCPU
                = db.rawQuery("SELECT * FROM " + TBL_CPU + " WHERE build_name='" + buildname + "'" , null);

        // on below line we are creating a new array list.
//        ArrayList<BuildData> buildDataArrayList
//                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCPU.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                //CPU(String buildName, String title, String brand, String model, String speed, String socket, String link, String img, double price)
                cpu.add(new CPU(
                        cursorCPU.getString(0),
                        cursorCPU.getString(1),
                        cursorCPU.getString(2),
                        cursorCPU.getString(3),
                        cursorCPU.getString(4),
                        cursorCPU.getString(5),
                        cursorCPU.getString(6),
                        cursorCPU.getString(7),
                        cursorCPU.getDouble(8)));
            } while (cursorCPU.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCPU.close();
        return cpu;
    }

    public ArrayList<Cooler> getCooler(ArrayList<Cooler> cooler, String buildname)
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorCooler
                = db.rawQuery("SELECT * FROM " + TBL_COOLER + " WHERE build_name='" + buildname + "'" , null);

        // on below line we are creating a new array list.
//        ArrayList<BuildData> buildDataArrayList
//                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCooler.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                //CPU(String buildName, String title, String brand, String model, String speed, String socket, String link, String img, double price)
                cooler.add(new Cooler(
                        cursorCooler.getString(0),
                        cursorCooler.getString(1),
                        cursorCooler.getString(2),
                        cursorCooler.getString(3),
                        cursorCooler.getString(4),
                        cursorCooler.getString(5),
                        cursorCooler.getString(6),
                        cursorCooler.getString(7),
                        cursorCooler.getDouble(8)));
            } while (cursorCooler.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCooler.close();
        return cooler;
    }

    public ArrayList<Motherboard> getMobo(ArrayList<Motherboard> mobo, String buildname)
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorMobo
                = db.rawQuery("SELECT * FROM " + TBL_MOBO + " WHERE build_name='" + buildname + "'" , null);

        // on below line we are creating a new array list.
//        ArrayList<BuildData> buildDataArrayList
//                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorMobo.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                //CPU(String buildName, String title, String brand, String model, String speed, String socket, String link, String img, double price)
                mobo.add(new Motherboard(
                        cursorMobo.getString(0),
                        cursorMobo.getString(1),
                        cursorMobo.getString(2),
                        cursorMobo.getString(3),
                        cursorMobo.getString(4),
                        cursorMobo.getString(5),
                        cursorMobo.getString(6),
                        cursorMobo.getString(7),
                        cursorMobo.getDouble(8)));
            } while (cursorMobo.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorMobo.close();
        return mobo;
    }

    public ArrayList<RAM> getRAM(ArrayList<RAM> ram, String buildname)
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorRAM
                = db.rawQuery("SELECT * FROM " + TBL_RAM + " WHERE build_name='" + buildname + "'" , null);

        // on below line we are creating a new array list.
//        ArrayList<BuildData> buildDataArrayList
//                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorRAM.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                //CPU(String buildName, String title, String brand, String model, String speed, String socket, String link, String img, double price)
                ram.add(new RAM(
                        cursorRAM.getString(0),
                        cursorRAM.getString(1),
                        cursorRAM.getString(2),
                        cursorRAM.getString(3),
                        cursorRAM.getString(4),
                        cursorRAM.getString(5),
                        cursorRAM.getString(6),
                        cursorRAM.getString(7),
                        cursorRAM.getDouble(8)));
            } while (cursorRAM.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorRAM.close();
        return ram;
    }

    public ArrayList<Storage> getStorage(ArrayList<Storage> storage, String buildname)
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorStorage
                = db.rawQuery("SELECT * FROM " + TBL_STORAGE + " WHERE build_name='" + buildname + "'" , null);

        // on below line we are creating a new array list.
//        ArrayList<BuildData> buildDataArrayList
//                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorStorage.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                //CPU(String buildName, String title, String brand, String model, String speed, String socket, String link, String img, double price)
                storage.add(new Storage(
                        cursorStorage.getString(0),
                        cursorStorage.getString(1),
                        cursorStorage.getString(2),
                        cursorStorage.getString(3),
                        cursorStorage.getString(4),
                        cursorStorage.getString(5),
                        cursorStorage.getString(6),
                        cursorStorage.getString(7),
                        cursorStorage.getDouble(8)));
            } while (cursorStorage.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorStorage.close();
        return storage;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}