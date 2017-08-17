package tennis.parks.com.parkstennis;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.LoginActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvPayment = (TextView) findViewById(R.id.tvPayment);
        TextView tvFindVenue = (TextView) findViewById(R.id.tvFindVenue);
        TextView tvPaidUser = (TextView) findViewById(R.id.tvPaidUser);


        ImageView ivParksLogo = (ImageView) findViewById(R.id.ivParkslogo);

        if(haveNetworkConnection())
        {
        }else
        {
            Toast.makeText(getApplicationContext(),"Please turn your internet connection on...!!!",Toast.LENGTH_LONG).show();
        }

        tvPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PaymentLinkIntent = new Intent(MainActivity.this, PayPalActivity.class);
                MainActivity.this.startActivity(PaymentLinkIntent);

            }
        });

        tvPaidUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PaymentLinkIntent = new Intent(MainActivity.this, UserList.class);
                MainActivity.this.startActivity(PaymentLinkIntent);

            }
        });

        tvFindVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FindVenueLinkIntent = new Intent(MainActivity.this, FindVenue.class);
                MainActivity.this.startActivity(FindVenueLinkIntent);

            }
        });

    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
