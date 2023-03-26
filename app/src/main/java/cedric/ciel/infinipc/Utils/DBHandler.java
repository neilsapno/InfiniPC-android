package cedric.ciel.infinipc.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import cedric.ciel.infinipc.Lists.BuildData;
import cedric.ciel.infinipc.Parts.CPU;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "builds";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "mybuilds";
    private static final String TBL_CPU = "cpu";
    private static final String TBL_COOLER = "cooler";
    private static final String TBL_MOBO = "mobo";

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

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
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
                + "title" + " TEXT,"
                + "brand" + " TEXT,"
                + "model" + " TEXT,"
                + "speed" + " TEXT,"
                + "socket" + " TEXT,"
                + "link" + " TEXT,"
                + "img" + "TEXT,"
                + PRICE_COL + " REAL)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
        db.execSQL(query2);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewBuild(String buildName, String CPU, String Cooler, String Mobo, String RAM, String Storage,
                            String GPU, String Case, String PSU, String CaseFan, int RAMSpeed, int Watts, double Price, String BuildImageUrl) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        BuildData buildData = new BuildData(buildName, CPU, Cooler, Mobo, RAM, Storage, GPU, Case, PSU, CaseFan, RAMSpeed, Watts, Price, BuildImageUrl);
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
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

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);
        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void editBuild(String buildName, String CPU, String Cooler, String Mobo, String RAM, String Storage,
                            String GPU, String Case, String PSU, String CaseFan, int RAMSpeed, int Watts, double Price, String BuildImageUrl) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        BuildData buildData = new BuildData(buildName, CPU, Cooler, Mobo, RAM, Storage, GPU, Case, PSU, CaseFan, RAMSpeed, Watts, Price, BuildImageUrl);
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
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

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);
        // at last we are closing our
        // database after adding database.
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // we have created a new method for reading all the courses.
    public ArrayList<BuildData> getBuilds(ArrayList<BuildData> buildData)
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorBuilds
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
//        ArrayList<BuildData> buildDataArrayList
//                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorBuilds.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                buildData.add(new BuildData(
                        cursorBuilds.getString(1),
                        cursorBuilds.getString(2),
                        cursorBuilds.getInt(11),
                        cursorBuilds.getInt(12),
                        cursorBuilds.getDouble(13)));
            } while (cursorBuilds.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorBuilds.close();
        return buildData;
    }

    public void addCPU(String buildname, String title, String brand, String model, String speed, String socket, String link, String img, double price){
        SQLiteDatabase db = this.getWritableDatabase();

        CPU cpu = new CPU(buildname, title, brand, model, speed, socket, link, img, price);
        // on below line we are creating a
        // variable for content values.
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

    public ArrayList<CPU> getCPU(ArrayList<CPU> cpu, String buildname)
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursorCPU
                = db.rawQuery("SELECT * FROM " + TBL_CPU + " WHERE buildname='" + buildname + "'" , null);

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

}