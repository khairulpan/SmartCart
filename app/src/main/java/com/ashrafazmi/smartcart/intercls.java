package com.ashrafazmi.smartcart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class intercls extends AppCompatActivity {

    public static final String STUDENT_NAME = "com.ashrafazmi.umotion.fullnameet";
    public static final String STUDENT_ID = "com.ashrafazmi.umotion.studidet";

    private Button btnenjoy;
    private TextView usee, full, maill ,time, cartid;
    private FirebaseAuth auth;

    DatabaseReference databaseStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intercls);

        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        String mail = preferences.getString("email", "");
        String fullname = preferences.getString("fullname","");
        String use = preferences.getString("user","");
        String cart = preferences.getString("result1","");

        //inidec

        btnenjoy = (Button) findViewById(R.id.btnenjoy);
        usee = (TextView) findViewById(R.id.username);
        full = (TextView) findViewById(R.id.fullname);
        maill = (TextView) findViewById(R.id.email);
        time =(TextView)findViewById(R.id.endtext);
        cartid= (TextView) findViewById(R.id.cartid);

        //GetDateAndTime
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

       time.setText(currentDateTimeString);
        full.setText(fullname);
        usee.setText(use);
        maill.setText(mail);
        cartid.setText(cart);

        //Retrieve from database
        databaseStudent = FirebaseDatabase.getInstance().getReference("cartuser");

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        //email = (TextView) findViewById(R.id.useremail);

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(intercls.this, LoginActivity.class));
                    finish();
                }
            }
        };

        btnenjoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudent();
            }
        });

    }

    //add_feedback

    private void addStudent() {
        //getting the values to save
        String  namesavee  = usee.getText().toString().trim();
        String  idsavee  = full.getText().toString().trim();
        String  coursesavee  = maill.getText().toString().trim();
        String  kelaslecturesavee  = time.getText().toString().trim();
        String  kelastimesave  = cartid.getText().toString().trim();

        //creating an Artist Object
        String id = databaseStudent.push().getKey();
        Student student = new Student(namesavee, idsavee, coursesavee,  kelaslecturesavee, kelastimesave);

        //Saving the Artist
        databaseStudent.child(id).setValue(student);

        Intent intrcls = new Intent(intercls.this, PayGateway.class);
        intercls.this.startActivity(intrcls);

    }



    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        maill.setText(user.getEmail());


    }

    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(intercls.this, LoginActivity.class));
                finish();
            } else {
                setDataToView(user);

            }
        }


    };

    //sign out method
    public void signOut() {
        auth.signOut();


// this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(intercls.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

   /** @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    } **/

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}







