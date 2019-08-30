package com.sm_academy.Activity.LearnerActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.PteTestStartActivity;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.PayementResponse;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SubscriptionCheckoutFragment extends Checkout {

    private double pay;
    private String paymentype, feeid, batch_id,id;
    String paymentid;

    public SubscriptionCheckoutFragment(String batch_id,String id, double d, String sppay, String feeid) {
        this.pay = d;
        this.paymentype = sppay;
        this.feeid = feeid;
        this.batch_id = batch_id;
        this.id=id;
        pay = pay / 100;
    }

    // override onSuccess method to capture razorpay_payment_id
    public void onSuccess(String razorpay_payment_id) {
        paymentid = razorpay_payment_id;
        Toast.makeText(getActivity(), "Payment Successful: " + razorpay_payment_id, Toast.LENGTH_SHORT).show();
        sendpaymentdetails();

        getActivity().finish();
        Intent intent = new Intent(getActivity(), MySubscriptionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // post razorpay_payment_id to your server or something.
    }

    private void sendpaymentdetails() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
                final JsonObject jsonObject = new JsonObject();
                final JsonObject jsonObject1 = new JsonObject();
                jsonObject.addProperty("batch_user_id", batch_id);
                jsonObject.addProperty("id", id);
                jsonObject.addProperty("payment_id", paymentid);
                RetrofitAPI.getInstance(getActivity()).getApi().sendSubscriptionpayment(jsonObject, new Callback<PayementResponse>() {
                    @Override
                    public void success(PayementResponse object, Response response) {
                        UIUtil.stopProgressDialog(getActivity());
                        // Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Json ", "Hhd --- " + object.toString());
                        Log.e("Json ", "Response --- " + response.getBody());
                  /*  if (object.get("status").getAsInt() == Constants.SUCCESS) {
                        Toast.makeText(getActivity(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), object.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                    }*/
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getActivity());
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    // onError will be invoked in following cases:
    // 1. back or close button pressed i.e. user cancels payment form (code = Activity.RESULT_CANCELED)
    // 2. network error while loading checkout form (code = 2)
    //
    // onError isn't invoked in case of payment authentication failure, rather error is displayed on checkout form and customer can reattempt payment.

    public void onError(int code, String response) {
        Toast.makeText(getActivity(), "Payment Error Please Try Again" + Integer.toString(code) + ": " + response, Toast.LENGTH_SHORT).show();
    }
};
