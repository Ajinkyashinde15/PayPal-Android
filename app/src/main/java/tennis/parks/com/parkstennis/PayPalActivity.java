package tennis.parks.com.parkstennis;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class PayPalActivity extends AppCompatActivity {

    PayPalConfiguration payPalConfiguration;
    String paypalclientid="Af-FSE0CtafEhRDu7dAsd-VbjPm27HriMyUs_yUQeTQs-MAOtQR0pIUrkyb3uoJMNN9fEPPAX1a4-Fqw";
    Intent mservices;
    int mpaypalrequestcode=999;
    DataBase dbhelp;
    SQLiteDatabase database;
    AutoCompleteTextView txtName,txtAge,txtAddress,txtDate,txtPhone,txtEmail,txtMedCond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);

        payPalConfiguration=new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(paypalclientid);

        mservices=new Intent(this, PayPalService.class);
        mservices.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration);
        startService(mservices);

        dbhelp=new DataBase(getApplicationContext());  //store in to local database
        database=dbhelp.getWritableDatabase();

        txtName=(AutoCompleteTextView) findViewById(R.id.txtName);
        txtAge=(AutoCompleteTextView) findViewById(R.id.txtAge);
        txtAddress=(AutoCompleteTextView) findViewById(R.id.txtAddress);
        txtDate=(AutoCompleteTextView) findViewById(R.id.txtDate);
        txtPhone=(AutoCompleteTextView) findViewById(R.id.txtPhone);
        txtEmail=(AutoCompleteTextView) findViewById(R.id.txtEmail);
        txtMedCond=(AutoCompleteTextView) findViewById(R.id.txtMedCond);
    }

    void pay(View view)
    {
        PayPalPayment payment=new PayPalPayment(new BigDecimal(10),"EUR","Test Payment with Paypal",PayPalPayment.PAYMENT_INTENT_SALE);
//default activity for making payments
        Intent intent=new Intent(this,PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
        startActivityForResult(intent,mpaypalrequestcode);

        dbhelp.insertUser(txtName.getText().toString(),txtAge.getText().toString(),txtAddress.getText().toString(),txtDate.getText().toString(),txtPhone.getText().toString(),txtEmail.getText().toString(),txtMedCond.getText().toString());
        txtName.setText("");
        txtAge.setText("");
        txtAddress.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtMedCond.setText("");

    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==mpaypalrequestcode)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                Toast.makeText(this,"Payment Approved",Toast.LENGTH_SHORT).show();

                if(confirmation!=null)
                {
                    String state=confirmation.getProofOfPayment().getState();

                    if(state.equals("approved"))
                    {
                        Toast.makeText(this,"Payment Approved",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(this,"Error in the payment",Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(this,"Confirmation is null",Toast.LENGTH_SHORT).show();

            }
        }
    }
}
