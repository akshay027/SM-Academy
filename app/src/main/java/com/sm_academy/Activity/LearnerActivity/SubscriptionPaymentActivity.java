package com.sm_academy.Activity.LearnerActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sm_academy.Activity.DefaultActivity.CheckoutFragment;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.R;

import org.json.JSONObject;

public class SubscriptionPaymentActivity extends AppCompatActivity {
    final String public_key = "rzp_test_TXRkg9eEhhx3Ew"; //Enter key here
    private EditText etamountfees;
    double d, a;
    private String finalamount, amtcon;
    private String sppay, feeid;
    String batch_id,id, money;

    public SubscriptionPaymentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_payement);

        // payment button created by you in xml layoutpay_btn
        View button = (View) findViewById(R.id.pay_btn);
        Intent Intent = this.getIntent();
        Intent intent = getIntent();
        batch_id = intent.getStringExtra("batch_id");
        money = intent.getStringExtra("money");
        id= intent.getStringExtra("id");
        Log.e("money", "---" + money);
        Log.e("batch_id", "---" + batch_id);
        Log.e("id", "---" + id);
        //d = Intent.getDoubleExtra("amountconvertedtorupee", 0.0);
        d = Double.parseDouble(money);
        d = d * 100;
        sppay = Intent.getStringExtra("paymenttype");
        feeid = Intent.getStringExtra("feeid");
        // you need to pass current activity in order to let razorpay create CheckoutActivity
        final Activity activity = this;

        final SubscriptionCheckoutFragment co = new SubscriptionCheckoutFragment(batch_id, id,d, sppay, feeid);

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
        finish();
        //Intent intent = new Intent(getApplicationContext(), MySubscriptionActivity.class);
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
       // startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
           // Intent intent = new Intent(getApplicationContext(), MySubscriptionActivity.class);
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
           // startActivity(intent);
        }
        return true;
    }

}
