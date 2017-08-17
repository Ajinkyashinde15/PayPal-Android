package tennis.parks.com.parkstennis;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class UserList extends AppCompatActivity {

    DataBase dbhelp;
    SQLiteDatabase database;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlistusers);

        try {

            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            displayList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void displayList()
    {
        dbhelp=new DataBase(getApplicationContext());  //store in to local database
        database=dbhelp.getWritableDatabase();
        int count = 0;
        try {

        Cursor c1=database.rawQuery("select * from users", null);;
        while (c1.moveToNext()) {
            String a = c1.getString(0);
            String b = c1.getString(1);
            String c = c1.getString(2);
            String f = c1.getString(3);
            String g = c1.getString(4);
            String gg = c1.getString(5);
            String fg = c1.getString(6);
            count++;

//            Toast.makeText(UserList.this, " rows " + a, Toast.LENGTH_SHORT).show();
        }

        final String[] namelist = new String[count - 1];


            int i = 0;
            Cursor userscrs1 = dbhelp.getUser();
            while (userscrs1.moveToNext()) {
                namelist[i] = userscrs1.getString(1);
                i++;
            }

            CustomList adapter = new CustomList(UserList.this, namelist);
            list=(ListView)findViewById(R.id.mainListViewmessage);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(UserList.this, "You Clicked at " + namelist[+ position], Toast.LENGTH_SHORT).show();
                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
