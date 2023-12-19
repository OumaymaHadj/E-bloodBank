package hadj.tn.test;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hadj.tn.test.model.Alert;
import hadj.tn.test.model.FCMToken;
import hadj.tn.test.model.NotificationResquest;
import hadj.tn.test.model.User;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soup.neumorphism.NeumorphButton;

public class MapsActivity extends AppCompatActivity {

    private static final int LOCATION_MIN_UPDATE_TIME = 10;
    private static final int LOCATION_MIN_UPDATE_DISTANCE = 1000;

    private MapView mapView;
    private GoogleMap googleMap;
    private Location location = null;
    private TextView name, type,phone;
    CircleImageView picture;
    String nom, username, token, gps_coordinates;
    FCMToken fcmToken = new FCMToken();
   Alert alert = new Alert();

   // private static String serverKey = "AAAA29tVGBw:APA91bFI84th9FVxKV2bb4MQcSvr2cNpx4Y_X4w1EPL2uRhQOGimC5khd8eyosLD6ftUjqtp0G53C3kf2iGdPDY7tJxgg5-HMsH2rNRbNomcMdiH20l1HA038nKq9ZGDyoLDxrgpTaHZ";


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            drawMarker(location, getText(R.string.i_am_here).toString());
            locationManager.removeUpdates(locationListener);
        }


        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SharedPreferences preferences = getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String retrievedToken = preferences.getString("TOKEN", null); //second parameter default value.
        username = preferences.getString("USERNAME",null);
        token = "Bearer " + retrievedToken;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        name = findViewById(R.id.name);
        type = findViewById(R.id.typesang);
        phone = findViewById(R.id.contact);
        picture = findViewById((R.id.donor_pic));
        Intent intent = getIntent();
        nom = intent.getStringExtra("username");
        name.setText(nom);
        initView(savedInstanceState);
        String blood = intent.getStringExtra("blood_type");
        type.setText(blood);
        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);
        userApi.findUser(token,nom).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                phone.setText(user.getPhone());
                gps_coordinates=user.getGps();
                if (user.getImage() != null) {
                    byte[] bytes = Base64.decode(user.getImage(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    picture.setImageBitmap(bitmap);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        NeumorphButton btn = findViewById(R.id.btnRequest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient retrofitClient = new RetrofitClient();
                API userApi = retrofitClient.getRetrofit().create(API.class);
                userApi.findTokensByUsername(token,nom).enqueue(new Callback<List<FCMToken>>() {
                    @Override
                    public void onResponse(Call<List<FCMToken>> call, Response<List<FCMToken>> response) {
                        if(response.code()==200){
                            List<FCMToken> list1 = response.body();
                            List<Integer> list2 = new ArrayList<>();
                            for (int i=0; i< list1.size();i++ ){
                                list2.add(list1.get(i).getToken_id());
                            }
                            int max_id = Collections.max(list2);
                            System.out.println(max_id);
                            for(int j=0; j<list1.size();j++){
                                if(list1.get(j).getToken_id()==max_id){
                                    fcmToken.setToken_id(max_id);
                                    fcmToken.setToken(list1.get(j).getToken());
                                    fcmToken.setUsername(list1.get(j).getUsername());
                                }
                            }
                        }else{
                            System.out.println("erreuuuuuuuuuuuuuuur");
                        }

                    }

                    @Override
                    public void onFailure(Call<List<FCMToken>> call, Throwable t) {

                    }
                });
                AlertDialog.Builder dialogEditPhone = new AlertDialog.Builder(MapsActivity.this);

                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.info_dialog,
                                null);
                dialogEditPhone.setView(customLayout);
                AlertDialog dialog = dialogEditPhone.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Button send = customLayout.findViewById(R.id.okRequest);
                Button cancel = customLayout.findViewById(R.id.cancelRequest);
                EditText name = customLayout.findViewById(R.id.name_beneficiary);
                EditText type = customLayout.findViewById(R.id.type_needed);
                EditText numero = customLayout.findViewById(R.id.phone_number);
                EditText place = customLayout.findViewById(R.id.where_to_donate);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = " Urgent donation needed";
                        String message = name.getText().toString() + " needs your " + type.getText().toString() + " blood donation at " + place.getText().toString() + " you can contact him on " + numero.getText().toString();
                        NotificationResquest notif = new NotificationResquest();
                        notif.setTitle(title);
                        notif.setMessage(message);
                        notif.setToken(fcmToken.getToken());
                        RetrofitClient retrofitClient = new RetrofitClient();
                        API userApi = retrofitClient.getRetrofit().create(API.class);
                        Call<ResponseBody> call = userApi.sendNotification(token, notif);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code()==200){
                                    dialog.dismiss();
                                    Toast.makeText(MapsActivity.this, "Request sent successfully", Toast.LENGTH_LONG).show();


                                    alert.setDescription(notif.getMessage());
                                    alert.setReceiver(nom);
                                    alert.setSender(username);
                                    userApi.saveAlert(alert).enqueue(new Callback<Alert>() {
                                        @Override
                                        public void onResponse(Call<Alert> call, Response<Alert> response) {
                                            if (response.code()==200)
                                                System.out.println("alert added");
                                        }

                                        @Override
                                        public void onFailure(Call<Alert> call, Throwable t) {

                                        }
                                    });

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(MapsActivity.this, "failed to send this notification", Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });
            }
        });

    }

    private void initView(Bundle savedInstanceState) {
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapView_onMapReady(googleMap);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        getCurrentLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void initMap() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (googleMap != null) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setAllGesturesEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 13);
            }
        }
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                Toast.makeText(getApplicationContext(), getText(R.string.provider_failed), Toast.LENGTH_LONG).show();
            } else {
                location = null;
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_MIN_UPDATE_TIME, LOCATION_MIN_UPDATE_DISTANCE, locationListener);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_MIN_UPDATE_TIME, LOCATION_MIN_UPDATE_DISTANCE, locationListener);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                if (location != null) {
                    drawMarker(location, getText(R.string.i_am_here).toString());
                }
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 13);
            }
        }
    }

    private void mapView_onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        initMap();
        getCurrentLocation();
    }

    private void drawMarker(Location location, String title) {

        if (this.googleMap != null) {
            googleMap.clear();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            String loc = latLng.latitude + "," + latLng.longitude;

            RetrofitClient retrofitClient = new RetrofitClient();
            API userApi = retrofitClient.getRetrofit().create(API.class);
            userApi.findUser(token,username).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    User user = response.body();
                    user.setGps(loc);
                    userApi.updateUser(token,user).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200) {
                                Toast.makeText(getApplication(), "updated", Toast.LENGTH_LONG).show();
                            }
                            else Toast.makeText(getApplication(), "fail", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplication(), "something's wrong 1", Toast.LENGTH_LONG).show();

                        }
                    });
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplication(), "something's wrong", Toast.LENGTH_LONG).show();
                }
            });

            userApi.findUser(token,nom).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    String donor_name= user.getUsername();
                    gps_coordinates=user.getGps();
                    String[] result = gps_coordinates.split(",");
                    LatLng donors = new LatLng(Double.parseDouble(result[0]) ,	Double.parseDouble(result[1]) );
                    googleMap.addMarker(new MarkerOptions().position(donors).title(donor_name+"'s position").snippet(""));
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(title);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            googleMap.addMarker(markerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(7));
        }

    }

  /*  private Boolean validationInfo(String beneficiaryName, String hospital, String bloodType, String phone) {

        if (beneficiaryName.length() == 0) {
            name.requestFocus();
            name.setError("Username cannot be empty");
            return false;

        } else if (!beneficiaryName.matches("([a-zA-Z]+(\\s)*(\\s[a-zA-Z]+)*)+")) {
            editTextUsername.requestFocus();
            editTextUsername.setError("Enter only alphabetic characters and spaces");
            return false;
        } else if (hospital.length() == 0) {
            editTextEmail.requestFocus();
            editTextEmail.setError("Email cannot be empty");
            return false;
        } else if (phone.length() == 0) {
            editTextPhone.requestFocus();
            editTextPhone.setError("Phone cannot be empty");
            return false;
        } else if (!phone.matches("[0-9]{8}$")) {
            editTextPhone.requestFocus();
            editTextPhone.setError("Phone number has 8 digits");
            return false;
        }else return true;
    }*/


}