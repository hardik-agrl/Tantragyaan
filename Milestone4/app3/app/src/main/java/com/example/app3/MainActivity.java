package com.example.app3;

import static android.content.ContentValues.TAG;
//import android.support.v4.app.Fragment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-4623d-default-rtdb.firebaseio.com/");
    public static final String SHARED_PREFS="sharedPrefs";
    private FirebaseAuth firebaseAuth;
    private EditText emailtext, passwordtext;
    private Button signin;
    private TextView signup;
    private CheckBox checkbox;
    private SharedPreferences preferences;
    private  SharedPreferences.Editor editor;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private String username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        emailtext = findViewById(R.id.email);
        passwordtext = findViewById(R.id.password);
        signin = findViewById((R.id.signin));
        signup = findViewById(R.id.signup);
        checkbox = findViewById(R.id.checkBox);
        final String EXTRA_NAME = "com.example.intent.extra.NAME";

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
//        if (saveLogin == true) {
        emailtext.setText(loginPreferences.getString("username", ""));
        passwordtext.setText(loginPreferences.getString("password", ""));
        checkbox.setChecked(true);

//                    preferences =getSharedPreferences("myPreferences",MODE_PRIVATE);
//                    editor= preferences.edit();

        //        if(preferences.contains("savedemail")){
        //            emailtext.setText(savedemail.);
        //        }
        //        else
        {

            /**
             * signup method
             */
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(MainActivity.this, "This is activity 1 to 3", Toast.LENGTH_SHORT).show();

                    Intent intent1 = new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(intent1);
                    finish();
                }
            });
            /**
             * sign in methord
             */
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (view == signin) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(emailtext.getWindowToken(), 0);

                        username = emailtext.getText().toString();
                        password = passwordtext.getText().toString();
                        /**
                         * implementing remember me
                         */
                        if (checkbox.isChecked()) {
                            loginPrefsEditor.putBoolean("saveLogin", true);
                            loginPrefsEditor.putString("username", username);
                            loginPrefsEditor.putString("password", password);
                            loginPrefsEditor.commit();
                        } else {
                            loginPrefsEditor.clear();
                            loginPrefsEditor.commit();
                        }


                        final String email, password;
                        email = String.valueOf(emailtext.getText());
                        password = String.valueOf((passwordtext.getText()));

                        /**
                         * if email field is empty
                         */
                        if (TextUtils.isEmpty(email)) {
                            Toast.makeText(MainActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(password)) {
                            Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //                final EditText username = findViewById(R.id.username);
                        //                final EditText password = findViewById(R.id.password);
                        //                final Button loginbtn = findViewById(R.id.loginbtn);
                        //                final TextView SignUp = findViewById(R.id.signupm);

                        //                loginbtn.setOnClickListener(v -> {
                        //                    final String usernameTxt = username.getText().toString();
                        //                    final String passwordTxt = password.getText().toString();
                        //                    //checking fields are empty or not
                        //                    if(usernameTxt.isEmpty() || passwordTxt.isEmpty()){
                        //                        Toast.makeText(UserLoginActivity.this,"Please enter your username and password",Toast.LENGTH_SHORT).show();
                        //                    }
                        else {

//


                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                String uuid2 = firebaseAuth.getUid();
                                                String uuidtext = firebaseAuth.getCurrentUser().getUid();
//                                                    Intent intent = new Intent(MainActivity4.this,fragment3.class);
//
//                                                    String nametext = uuidtext;
//                                                    intent.putExtra(EXTRA_NAME,nametext);
//                                                    startActivity(intent);
                                                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        //checking if username and password exist in firebase
                                                        if (snapshot.hasChild(uuid2)) {
                                                            //username exists in firebase database
                                                            //now get password of user from the firebase data and math it with user entered password
                                                            final String getPassword = snapshot.child(uuid2).child("password").getValue(String.class);

                                                            //compare
                                                            if (getPassword.equals(password)) {
                                                                Toast.makeText(MainActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
//                                                                    if (checkbox.isChecked()) {
////                                                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
////                                                    SharedPreferences.Editor editor=sharedPreferences.edit();
////                                                                        editor.putString("password", password);
////                                                                        editor.putString("email", email);
////                                                                        editor.apply();
//                                                                    }
                                                                //open next activity
//                                                    startActivity(new Intent(MainActivity.this, MainActivity2.class));
                                                                finish();
                                                            } else {
                                                                Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(MainActivity.this, "Username does not exist !", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                                    FirebaseUser user = firebaseAuth.getCurrentUser();


                                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
//                        if(getPassword.equals(password)){
//
//
//                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//                            SharedPreferences.Editor editor=sharedPreferences.edit();
//                            editor.putString("name","true");
//                            editor.apply();
//
//
//                            Toast.makeText(MainActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();
//
//                            //open next activity
//                            startActivity(new Intent(MainActivity.this,MainActivity3.class));
//                            finish();
//                            }


                            checkBox();


                            if (isOnline() == false) {

                                Alert();
                            }
                            //                if(checkbox.isChecked()){
                            //
                            //                    editor.putString("savedemail", email);
                            //                    editor.putString("savedpass", password);
                            //                    editor.commit();
                            //                }
//                            Intent intent = new Intent(this,MainActivity2.class);
                            String uuid = String.valueOf(firebaseAuth.getCurrentUser());
//                            intent.putExtra(EXTRA_NAME,uuid);
//                            startActivity(intent);
//                            MyFragment myFragment = new MyFragment();
//                            FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
//                            Bundle data = new Bundle();
//                            data.putString("myData",uuid);
//                            myFragment.set


                        }
                    }

                }

            });
        }
//        }
    }

//    public void mailexist(View v){
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Query queryByEmail = db.collection("users").whereEqualTo("email", "user-email@gmail.com");
//        queryByEmail.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        if (document.exists()) {
//                            Log.d(TAG, "User already exists.");
//                        } else {
//                            Log.d(TAG, "User doesn't exist.");
//                        }
//                    }
//                } else {
//                    Log.d(TAG, task.getException().getMessage());
//                }
//            }
//        });
//    }






//    public void checkemail(View v){
//        firebaseAuth.fetchSignInMethodsForEmail(emailtext.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//            @Override
//            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                boolean check =!SignInMethodQueryResult.getSignInMethod.isEmpty();
//
//                if(!check){
//                    Toast.makeText(MainActivity.this, "Email doesn't exist", Toast.LENGTH_SHORT).show();
//                    return ;
//            }
//                else {
//                    Toast.makeText(MainActivity.this, "Email exist", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//        })
//
//        }
//    }




    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    public void Alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this );
        builder.setMessage("No Internet");
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                isOnline();
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
                return;
            }
        });
        builder.create();
        builder.show();
    }
    private void checkBox() {
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String check = sharedPreferences.getString("name","");
        if(check.equals("true")){

//            Toast.makeText(MainActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();

            //open next activity
//            startActivity(new Intent(MainActivity.this,MainActivity3.class));
//            finish();

        }
    }

};