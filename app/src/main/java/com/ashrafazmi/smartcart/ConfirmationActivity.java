package com.ashrafazmi.smartcart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        //Getting Intent
        Intent intent = getIntent();


        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }


    /////---------------------------break----------------------------------------------------------------------------------------------------

    private void showDetails(JSONObject jsonDetails, final String paymentAmount) throws JSONException {
        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        String mail = preferences.getString("email", "");
        String code = preferences.getString("code","");
        String fullname = preferences.getString("fullname","");
        String user = preferences.getString("user","");
        //Views
        final TextView textViewId = (TextView) findViewById(R.id.paymentId);
        final TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        final TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);
        TextView usrnme = (TextView) findViewById(R.id.usernamepalpal2);
        TextView fllnme = (TextView) findViewById(R.id.fullnamepalpal);
        TextView maili = (TextView) findViewById(R.id.mailpalpalpal);
        //EditText phne = (EditText) findViewById(R.id.phonepalpal);
        //EditText icrd = (EditText) findViewById(R.id.idcardpalpal);
        //EditText identity = (EditText) findViewById(R.id.paypalidentity);
        //EditText accbalancepay = (EditText) findViewById(R.id.accountbalancepay);
       // EditText newaccbalancepay = (EditText) findViewById(R.id.newaccountbalancepay);
        //EditText resultaccbalancepay = (EditText) findViewById(R.id.resultaccountbalancepay);
        FloatingActionButton paypaltocheckout= (FloatingActionButton) findViewById(R.id.btnpaypaltohome);

      /**  int calculate1 =0;
        int calculate2 =0;
        calculate1= Integer.parseInt(accountbalancepaypal);
        calculate2= Integer.parseInt(paymentAmount);

        calculate1 = calculate1 + calculate2; **/

        //Showing the details from json object
        usrnme.setText(user);
        fllnme.setText(fullname);
        maili.setText(mail);
        //balanceintable.setText("MYR "+ String.valueOf(calculate1));
        //phne.setText(phonedisplay);
        //icrd.setText(passportdisplay);
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText("MYR "+ paymentAmount);
        //resultaccbalancepay.setText(String.valueOf(calculate1));
        //accbalancepay.setText(accountbalancepaypal);
        //newaccbalancepay.setText("RM"+paymentAmount);

        //changebacktheinttostring
        //final String paymentstatus = textViewStatus.getText().toString();

        paypaltocheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String paymentid = textViewId.getText().toString();
                String paymentstatus = textViewStatus.getText().toString();
                String paymentamount = textViewAmount.getText().toString();

                /**share new preference for paypal
                SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("paymentid",paymentid);
                editor.putString("paymentstatus",paymentstatus);
                editor.putString("paymentamount",paymentamount);
                editor.commit();**/

                Intent mainIntent = new Intent(ConfirmationActivity.this, Checkout.class);
                ConfirmationActivity.this.startActivity(mainIntent);


            }
        });



    }

    //------------------------------------------------paypaldesplay-----------------------------------------------------------------

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
