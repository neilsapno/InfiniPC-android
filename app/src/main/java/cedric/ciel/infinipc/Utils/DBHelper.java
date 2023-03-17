package cedric.ciel.infinipc.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Builds.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table RecommendedBuilds(bname TEXT primary key, processor TEXT, cpu_cooler TEXT, motherboard TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists RecommendedBuilds");


    }
    public Boolean insertBuildData(String bname,String processor,String cpu_cooler, String motherboard){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("bname", bname);
        contentValues.put("processor",processor);
        contentValues.put("cpu_cooler",cpu_cooler);
        contentValues.put("motherboard", motherboard);

        long result=db.insert("RecommendedBuilds", null,contentValues);
        if (result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from RecommendedBuilds",null);
        return cursor;
    }




}
