package hadj.tn.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import hadj.tn.test.fragment.HomeFragment;
import hadj.tn.test.fragment.leftMenuFragments.MyPublicationsFragment;
import hadj.tn.test.fragment.leftMenuFragments.MyRequestsFragment;
import hadj.tn.test.fragment.bottomMenuFragments.DonateBloodFragment;
import hadj.tn.test.fragment.bottomMenuFragments.EducateFragment;
import hadj.tn.test.fragment.bottomMenuFragments.FindDonorFragment;
import hadj.tn.test.fragment.leftMenuFragments.InfoSettingsFragment;
import hadj.tn.test.fragment.leftMenuFragments.InvitationFragment;
import hadj.tn.test.fragment.leftMenuFragments.ProfileFragment;
import hadj.tn.test.menu.DrawerAdapter;
import hadj.tn.test.menu.DrawerItem;
import hadj.tn.test.menu.SimpleItem;
import hadj.tn.test.model.FCMToken;
import hadj.tn.test.model.User;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_HOME = 0;
    private static final int POS_PROFILE = 1;
    private static final int POS_NOTIF = 2;
    private static final int POS_POSTS = 3;
    private static final int POS_REQUESTS = 4;
    private static final int POS_INFOS = 5;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    public static Fragment fragment;

    SlidingRootNav slidingRootNav;
    String title = "", authToken,username;
    ChipNavigationBar chipNavigationBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);

        SharedPreferences preferences = getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        username  = preferences.getString("USERNAME",null); //second parameter default value.
        String retrievedToken  = preferences.getString("TOKEN",null); //second parameter default value.
        authToken = "Bearer "+retrievedToken;

        FirebaseApp.initializeApp(getApplicationContext());

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        FCMToken newToken = new FCMToken();
                        newToken.setToken(token);
                        newToken.setUsername(username);
                        Call<ResponseBody> call = userApi.addRegistrationToken(authToken,newToken);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                System.out.println("success");
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                        System.out.println("TOKEN"+token);


                    }
                });


        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textViewToolbar = findViewById(R.id.textToolbar);
        CircleImageView imageView = findViewById(R.id.profileToolbar);
        setSupportActionBar(toolbar);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    textViewToolbar.setText("My profile");
                    ProfileFragment fragmentProfile = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentProfile).commit();
                }

        });

        Call<User> call = userApi.findUser(authToken, username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User getUser = response.body();
                //assert getUser!=null;
                String personImage = getUser.getImage();

                if (personImage != null) {
                    byte[] bytes = Base64.decode(personImage, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
            }
        });


        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();
       TextView logout =  slidingRootNav.getLayout().findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME),
                createItemFor(POS_PROFILE),
                createItemFor(POS_NOTIF),
                createItemFor(POS_POSTS),
                createItemFor(POS_REQUESTS),
                createItemFor(POS_INFOS)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        chipNavigationBar = findViewById(R.id.chipNavigation);
        chipNavigationBar.setItemSelected(R.id.ic_home, true);

        HomeFragment fragmentHome = new HomeFragment();
        showFragment(fragmentHome);

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.ic_home:
                        fragment = new HomeFragment();
                        textViewToolbar.setText("Home");
                        break;
                    case R.id.ic_finddonor:
                        fragment = new FindDonorFragment();
                        textViewToolbar.setText("Find a Donor");
                        break;
                    case R.id.ic_db:
                        fragment = new DonateBloodFragment();
                        textViewToolbar.setText("Donate Blood");
                        break;
                    case R.id.ic_educ:
                        fragment = new EducateFragment();
                        textViewToolbar.setText("Blood donation");
                        break;
                }
                if (fragment != null) {
                    showFragment(fragment);
                }
            }
        });
    }


    @Override
    public void onItemSelected(int position) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textViewtoolbar = findViewById(R.id.textToolbar);
        setSupportActionBar(toolbar);

        if (position == POS_HOME) {
            fragment = new HomeFragment();
            textViewtoolbar.setText("Home");
        }
        if (position == POS_PROFILE) {
            fragment = new ProfileFragment();
            textViewtoolbar.setText("My Profile");
        }
        if (position == POS_NOTIF) {
            fragment = new InvitationFragment();
            textViewtoolbar.setText("Invitations");
        }
        if (position == POS_POSTS) {
          /*  */

            fragment = new MyPublicationsFragment();
            textViewtoolbar.setText("My posts");
        }
        if (position == POS_REQUESTS) {
            fragment = new MyRequestsFragment();
            textViewtoolbar.setText("My requests");
        }
        if (position == POS_INFOS) {
            fragment = new InfoSettingsFragment();
            textViewtoolbar.setText("Account informations");
        }
        slidingRootNav.closeMenu();
        showFragment(fragment);
    }

    private void showFragment(Fragment fragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            getSupportActionBar().setTitle(title);
        }


    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.teal_700))
                .withSelectedTextTint(color(R.color.teal_700));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }
}
