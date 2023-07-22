package com.example.app3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class fragment3 extends Fragment {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-4623d-default-rtdb.firebaseio.com/");

    private TextView latitude,longitude;
    private Button button1,button2;
    FusedLocationProviderClient client;
    private FirebaseAuth firebaseAuth;


    public String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/tantragyaanfile";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        View view =inflater.inflate(R.layout.fragment_fragment3, container, false);
        button1 = view.findViewById(R.id.button);
        button2=view.findViewById(R.id.button2);
        latitude=view.findViewById(R.id.latitude);
        longitude=view.findViewById(R.id.longitude);

        client = LocationServices.getFusedLocationProviderClient(getActivity());




        File dir = new File(path);
        dir.mkdirs();










        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }else{
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
                }

            }
        });




        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long epoch = System.currentTimeMillis()/1000;
                String time ="/"+String.valueOf(epoch)+
                        ".txt";

                String  message = "Longitute "+(longitude.getText().toString())+"\n Latitude "+(latitude.getText().toString());
//                Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                System.out.println(message);


                databaseReference.child("location").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Bundle data = getArguments();
                        String uuid2= data.getString("myDATA");
//                        Intent intent =getActivity().getIntent();
//                        String name = intent.getStringExtra(MainActivity.EXTRA_NAME);

//                        String uuid = firebaseAuth.getCurrentUser();
//                        System.out.println(uuid);


//                        String strtext = getArguments().getString("uuid");
//                        Intent intent = requireActivity().getIntent();
//                        String name = intent.getStringExtra(MainActivity4.EXTRA_NAME);

//                        String uuid = activity.senduuid;
//                        System.out.println(strtext);

//                                            if (flag == true)
                        //check if email is already registered
//                if (flag==false) {
//                    Toast.makeText(MainActivity4.this, "Email already exists !", Toast.LENGTH_SHORT).show();
//                } else
                        {
                            //sending data to firebase realtime database
                            //using email as unique identifier..other with come under it
                            databaseReference.child("location").child(String.valueOf("uuid")).child(String.valueOf(epoch) ).child("latitude").setValue(String.valueOf(latitude.getText()));
                            databaseReference.child("location").child(String.valueOf("uuid")).child(String.valueOf(epoch)).child("longitude").setValue(String.valueOf(longitude.getText()));

//                                                databaseReference.child("users").child(uuid).child("uuid").setValue(uuid);

//                            databaseReference.child("users").child(uuid).child("password").setValue(password);
//                            Intent intent2 = new Intent(MainActivity4.this, MainActivity.class);
//                            startActivity(intent2);
//
//                            //show a sucess message then finish the activity
//                            Toast.makeText(MainActivity4.this, "User registered Sucessfully", Toast.LENGTH_SHORT).show();
//                                                finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }



                });





                File file = new File (path+time);
                String savetext =String.valueOf(latitude.getText());
                Save (file,message);



//                FileWriter file ;
//                try {
//                    try {
//                        file = new FileWriter("/storage/emulated/0/Download/browser/file.txt");
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    try {
//                        file.write("cse");
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    try {
//                        file.flush();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    try {
//                        file.close();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                } finally {
//                    System.out.println("File saved in "+"/sdcard/text.txt");
//                }







//                FileOutputStream writer = null;
//                try {
//                    writer = getActivity().openFileOutput(time,Context.MODE_PRIVATE);
//
//
//                    writer.write(message.getBytes());
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                 System.out.println("saved in "+ getActivity().getFilesDir());
//                Toast.makeText(getContext(),getActivity().getExternalStorageDir(), Toast.LENGTH_SHORT).show();


//                FileOutputStream fileOutputStream
//                try {
//                    writeFile(message);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
            }
        });

        return view;
    }

    public static void Save(File file, String data)
    {

        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();

        }
        catch (IOException e) {e.printStackTrace();}

    }



    //    private Boolean isExternalWritable(){
//        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
//            Log.i("State","yes it is Writable");
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
//
//
//
//    public void writeFile(String message) throws IOException {
//        if (isExternalWritable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//            File textfile =new File(Environment.getExternalStorageDirectory(),time);
//            FileOutputStream fos = new FileOutputStream(textfile);
//            fos.write(message.getBytes());
//            fos.close();
//            Toast.makeText(getContext(), "File saved", Toast.LENGTH_SHORT).show();
//            Log.i("saved","file is saved in"+ requireActivity().getExternalFilesDir(time) );
//
//        }
//        else{
//            Toast.makeText(getContext(), "File not saved", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public boolean checkPermission(String permission){
//        int check = ContextCompat.checkSelfPermission(requireContext(), permission);
//        return (check==PackageManager.PERMISSION_GRANTED);
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && (grantResults.length>0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation();
        }else{
            Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locationManager.isProviderEnabled((LocationManager.NETWORK_PROVIDER)) ){
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if ((location != null)) {

                        latitude.setText(String.valueOf(location.getLatitude()));
                        longitude.setText(String.valueOf(location.getLongitude()));

                    } else {
                        LocationRequest locationRequest = new LocationRequest().setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                latitude.setText(String.valueOf(location.getLatitude()));
                                longitude.setText(String.valueOf(location.getLongitude()));
                            }
                        };
                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());


                    }
                }
            });
        }
        else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }
    }




}