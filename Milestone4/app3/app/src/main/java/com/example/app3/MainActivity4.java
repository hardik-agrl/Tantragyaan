package com.example.app3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity4 extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-4623d-default-rtdb.firebaseio.com/");
    private FirebaseAuth firebaseAuth;
    private EditText emailtext, passwordtext, nametext, agetext, phonetext, repasswordtext;
    private Button signup;
    private Button signin;
    //    private String UUID ;
    private RadioGroup radioGroup;
    private  RadioButton radioButton;
    private TextView gender;
    private String gendertext;
    private boolean flag=false;
    public static final String EXTRA_NAME = "com.example.intent.extra.NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        firebaseAuth=FirebaseAuth.getInstance();

        final EditText emailtext = findViewById(R.id.email2);
        final EditText  passwordtext = findViewById(R.id.password2);
        EditText nametext = findViewById(R.id.name2);
        EditText agetext = findViewById(R.id.age2);
        EditText phonetext = findViewById(R.id.phone2);
        TextInputLayout repassword4 = (TextInputLayout) findViewById(R.id.repassword4);
        EditText repasswordtext = findViewById(R.id.password3);
        RadioGroup radioGroup = findViewById(R.id.radiogroup);
        TextInputLayout name4 = (TextInputLayout) findViewById(R.id.name4);
        TextInputLayout age4 = (TextInputLayout) findViewById(R.id.age4);
        TextInputLayout phone4 = (TextInputLayout) findViewById(R.id.phone4);
        TextInputLayout email4 = (TextInputLayout) findViewById(R.id.email4);
        TextInputLayout password4 = (TextInputLayout) findViewById(R.id.password4);




        signup = findViewById((R.id.login2));
        signin = findViewById(R.id.cancel2);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity4.this, "This is activity 4 to 1", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity4.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email, password,name,age,phone,repassword,UUID;
                email = String.valueOf(emailtext.getText());
                password = String.valueOf((passwordtext.getText()));
                name = String.valueOf(nametext.getText());
                age = String.valueOf(agetext.getText());
                phone = String.valueOf(phonetext.getText());
                repassword = String.valueOf(repasswordtext.getText());
                Boolean flag2=true;

                gendertext= checkbutton()   ;
                if(!String.valueOf(repassword).equals(String.valueOf(password))){
                    repasswordtext.setError("Password didn't match");
//                    Toast.makeText(MainActivity4.this, "Password didn't match", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity4.this, repassword+" "+password, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(email.indexOf('@')==-1){
                    email4.setError("Invalid Email id");
                    flag2=false;
//                    Toast.makeText(MainActivity4.this, "Invalid Email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phone.length()>10){
                    phone4.setError("Invalid Phone no");
                    flag2=false;
//                    Toast.makeText(MainActivity4.this, "Invalid Phone no", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.valueOf(age)>100){
                    age4.setError("Invalid Age");
                    flag2=false;
//                    Toast.makeText(MainActivity4.this, "Invalid Age", Toast.LENGTH_SHORT).show();
                }


                if (TextUtils.isEmpty(email)) {
                    email4.setError("Enter Email");
                    flag2=false;
//                    Toast.makeText(MainActivity4.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    password4.setError("Enter Password");
                    flag2=false;
//                    Toast.makeText(MainActivity4.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                } if(flag2==true) {
//




                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                checkEmailExistsOrNot();
//                                System.out.println(flag);
//                                if(flag==false){
//                                    Toast.makeText(MainActivity4.this, "Email already exist", Toast.LENGTH_SHORT).show();
////                                    finish();
//                                }
                                    if (task.isSuccessful()) {
                                        String uuid = firebaseAuth.getUid();
                                        senduuid(uuid);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("uuid",uuid);
//                                    fragment3 fragobj = new fragment3();
//                                    fragobj.setArguments(bundle);

//                                    Intent intent = new Intent(MainActivity4.this,fragment3.class);
//
//                                    String nametext = uuid;
//                                    intent.putExtra(EXTRA_NAME,nametext);
//                                    startActivity(intent);
                                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                                            if (flag == true)
                                                //check if email is already registered
//                                                if (flag==false) {
//                                                    Toast.makeText(MainActivity4.this, "Email already exists !", Toast.LENGTH_SHORT).show();
//                                                } else
                                                {
                                                    //sending data to firebase realtime database
                                                    //using email as unique identifier..other with come under it
                                                    databaseReference.child("users").child(uuid).child("email").setValue(email);
                                                    databaseReference.child("users").child(uuid).child("name").setValue(name);
                                                    databaseReference.child("users").child(uuid).child("gender").setValue(gendertext);
                                                    databaseReference.child("users").child(uuid).child("age").setValue(age);
                                                    databaseReference.child("users").child(uuid).child("phone").setValue(phone);
//                                                databaseReference.child("users").child(uuid).child("uuid").setValue(uuid);

                                                    databaseReference.child("users").child(uuid).child("password").setValue(password);
                                                    Intent intent2 = new Intent(MainActivity4.this, MainActivity.class);
                                                    startActivity(intent2);

                                                    //show a sucess message then finish the activity
                                                    Toast.makeText(MainActivity4.this, "User registered Sucessfully", Toast.LENGTH_SHORT).show();
//                                                finish();

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }

                                        });
                                        Toast.makeText(MainActivity4.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
//                                    updateUI(user);
//                                    Intent intent2 = new Intent(MainActivity4.this, MainActivity.class);
//                                    startActivity(intent2);
//                                    finish();
                                    } else {

                                        Toast.makeText(MainActivity4.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                                    updateUI(null);

                                    }
                                }
                            });


                }

            }
            public String checkbutton(){
                int radioid = radioGroup.getCheckedRadioButtonId();

                radioButton = findViewById(radioid);
                String gendertext = radioButton.getText().toString();
                return gendertext;
            }
        });


    }

    public String senduuid(String uuid) {
        return uuid;
    }

    void checkEmailExistsOrNot(){
        firebaseAuth.fetchSignInMethodsForEmail(emailtext.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                Log.d(TAG,""+task.getResult().getSignInMethods().size());
                if (task.getResult().getSignInMethods().size() == 0){
                    flag=true;
                    // email not existed
                }else {
                    flag=false;
                    Toast.makeText(MainActivity4.this, "Email Already exist", Toast.LENGTH_SHORT).show();
                    // email existed
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

}

