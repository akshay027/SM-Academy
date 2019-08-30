package com.sm_academy.Activity.DefaultActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sm_academy.R;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {

    final String public_key = "rzp_test_TXRkg9eEhhx3Ew"; //Enter key here
    private EditText etamountfees;
    double d, a;
    private String finalamount, amtcon;
    private String sppay, feeid;
    String batch_id, money;

    public PaymentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // payment button created by you in xml layoutpay_btn
        View button = (View) findViewById(R.id.pay_btn);
        Intent Intent = this.getIntent();
        Intent intent = getIntent();
        batch_id = intent.getStringExtra("id");
        money = intent.getStringExtra("money");
        Log.e("money", "---" + money);
        //d = Intent.getDoubleExtra("amountconvertedtorupee", 0.0);
        d = Double.parseDouble(money);
        d = d * 100;
        sppay = Intent.getStringExtra("paymenttype");
        feeid = Intent.getStringExtra("feeid");
        // you need to pass current activity in order to let razorpay create CheckoutActivity
        final Activity activity = this;

        final CheckoutFragment co = new CheckoutFragment(batch_id, d, sppay, feeid);

        co.setPublicKey(public_key);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    JSONObject options = new JSONObject("{" +
                            "description: 'Fees'," +
                            "image: 'https://rzp-mobile.s3.amazonaws.com/images/rzp.png'," +
                            "currency: 'INR'}"
                    );
                    options.put("amount", d);
                    options.put("name", "SM Academy");
                    co.open(activity, options);

                } catch (Exception e) {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            ;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        Intent intent = new Intent(getApplicationContext(), MainLandingActivity.class);
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finishAffinity();
            Intent intent = new Intent(getApplicationContext(), MainLandingActivity.class);
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
            startActivity(intent);

        }
        return true;
    }

}
