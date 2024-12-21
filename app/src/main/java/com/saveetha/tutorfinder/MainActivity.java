package com.saveetha.tutorfinder;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.saveetha.tutorfinder.student.ListPackagesFragment;
import com.saveetha.tutorfinder.student.MyBookingsFragment;
import com.saveetha.tutorfinder.student.StudentHomeFragment;
import com.saveetha.tutorfinder.student.StudentProfileFragment;
import com.saveetha.tutorfinder.student.SubscriptionFragment;
import com.saveetha.tutorfinder.student.TransactionFragment;
import com.saveetha.tutorfinder.tutor.LocationFragment;
import com.saveetha.tutorfinder.tutor.TutorHomeFragment;

public class MainActivity extends AppCompatActivity   {

    SharedPreferences sf;
    private long backPressedTime = 0;
    Context context;
//    private static final int IMMEDIATE_UPDATE = 1;
//    private static final int FLEXIBLE_UPDATE = AppUpdateType.FLEXIBLE;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int version = pInfo.versionCode;
            String packageName = pInfo.packageName;
            Log.d("versionCode", String.valueOf(version));
            Log.d("packageName", packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sf = getSharedPreferences(AppConstants.SHARED_PREF_NAME, MODE_PRIVATE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().clear();

        // The callback can be enabled or disabled here or in handleOnBackPressed()
//        EditText editText=new EditText(this);
//        editText.append();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toolbar.setTitleTextColor(Color.WHITE);

        toggle.syncState();

        String user_type = sf.getString(AppConstants.KEY_TYPE, null);
        String is_user_logged = sf.getString(AppConstants.KEY_ID, null);
        String is_user_email = sf.getString(AppConstants.KEY_EMAIL, null);
        String userName = sf.getString(AppConstants.KEY_NAME, null);
        String profile = sf.getString(AppConstants.KEY_IMAGE, null);
        View view= navigationView.inflateHeaderView(R.layout.header_main);
        ShapeableImageView siv=null;
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        if(is_user_logged != null)
        {
            TextView text=view.findViewById(R.id.textView123);

            text.setText(userName);
             siv= view.findViewById(R.id.imageView2);
             siv.setOnClickListener(v -> {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,new StudentProfileFragment(view));
                transaction.commit();
                getSupportActionBar().setTitle("Profile");
                 drawer.postDelayed(new Runnable() {
                     @Override public void run() {
                         // ... navigate away, e.g. startActivity(...)
                         drawer.closeDrawers();
                     }
                 }, 500);
            });
            Glide.with(this).load(profile).apply(new RequestOptions().placeholder(R.drawable.error2) // Placeholder image
                            .error(R.drawable.error1))// Error image in case of loading failure
                    .into(siv);
            if(user_type.equals(AppConstants.Student))
            {
                navigationView.inflateMenu(R.menu.student_menu);
                navigationView.getMenu().findItem(R.id.student_profile).setVisible(true);
                navigationView.getMenu().findItem(R.id.student_bookings).setVisible(true);
                navigationView.getMenu().findItem(R.id.student_packages).setVisible(true);
                navigationView.getMenu().findItem(R.id.subscription).setVisible(true);
                navigationView.getMenu().findItem(R.id.transaction).setVisible(true);
                tx.replace(R.id.frameLayout, new StudentHomeFragment());
                tx.commit();

            }else if(user_type.equals(AppConstants.Tutor)){
                navigationView.inflateMenu(R.menu.tutor_menu);
                navigationView.getMenu().findItem(R.id.transaction).setVisible(true);
                navigationView.getMenu().findItem(R.id.student_bookings).setVisible(true);
                navigationView.getMenu().findItem(R.id.tutor_profile).setVisible(true);
                navigationView.getMenu().findItem(R.id.location).setVisible(true);
                tx.replace(R.id.frameLayout, new TutorHomeFragment());
                tx.commit();
            }
            navigationView.getMenu().findItem(R.id.logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
            navigationView.getMenu().findItem(R.id.signup).setVisible(false);

        }else{
            navigationView.inflateMenu(R.menu.student_menu);
            navigationView.getMenu().findItem(R.id.logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.login).setVisible(true);
            navigationView.getMenu().findItem(R.id.signup).setVisible(true);

            tx.replace(R.id.frameLayout, new StudentHomeFragment());
            tx.commit();
        }
        checkAppUpdate();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                int id = item.getItemId();
                FragmentManager fm = getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                if(id == R.id.student_home){
                    getSupportActionBar().setTitle("Home");
                    transaction.replace(R.id.frameLayout,new StudentHomeFragment());
                    transaction.commit();
                }else if(id == R.id.tutor_home){
                    transaction.replace(R.id.frameLayout,new TutorHomeFragment());
                    transaction.commit();
                    getSupportActionBar().setTitle("Home");
                }else if(id == R.id.tutor_profile){
                    transaction.replace(R.id.frameLayout,new StudentProfileFragment(view));
                    transaction.commit();
                    getSupportActionBar().setTitle("Profile");
                }else if(id == R.id.location){
                    transaction.replace(R.id.frameLayout,new LocationFragment());
                    transaction.commit();
                    getSupportActionBar().setTitle("Location");
                }else if(id == R.id.student_packages){
                    transaction.replace(R.id.frameLayout,new ListPackagesFragment());
                    transaction.commit();
                    getSupportActionBar().setTitle("Packages");
                }else if(id == R.id.student_profile){
                    transaction.replace(R.id.frameLayout,new StudentProfileFragment(view));
                    transaction.commit();
                    getSupportActionBar().setTitle("Profile");
                }else if(id == R.id.student_bookings){
                    transaction.replace(R.id.frameLayout,new MyBookingsFragment());
                    transaction.commit();
                    getSupportActionBar().setTitle("Bookings");
                }else if(id == R.id.subscription){
                    transaction.replace(R.id.frameLayout,new SubscriptionFragment());
                    transaction.commit();
                    getSupportActionBar().setTitle("Subscription");
                }else if(id == R.id.transaction){
                    transaction.replace(R.id.frameLayout,new TransactionFragment());
                    transaction.commit();
                    getSupportActionBar().setTitle("Transaction");
                }else if(id == R.id.login){
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.putExtra("fromMainActivity","ma");
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                    finish();
                }else if(id == R.id.signup){
                    Intent i = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                    finish();
                }else if(id == R.id.logout){
                    SharedPreferences.Editor editor= sf.edit();
                    editor.clear();
                    editor.commit();
                    Intent mIntent = getIntent();
                    finish();
                    startActivity(mIntent);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                }
                drawer.postDelayed(new Runnable() {
                    @Override public void run() {
                        // ... navigate away, e.g. startActivity(...)
                        drawer.closeDrawers();
                    }
                }, 500);
                return true;
            }
        });
    }

    private void checkAppUpdate() {

        ActivityResultLauncher<IntentSenderRequest> activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // handle callback
                        if (result.getResultCode() != RESULT_OK) {
                            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
                            // If the update is canceled or fails,
                            // you can request to start the update again.
                        } else {
                            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(context);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                // Request the update.
                Toast.makeText(context, "Update Available", Toast.LENGTH_SHORT).show();
                appUpdateManager.startUpdateFlowForResult(
                        // Pass the intent that is returned by 'getAppUpdateInfo()'.
                        appUpdateInfo,
                        // an activity result launcher registered via registerForActivityResult
                        activityResultLauncher,
                        // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                        // flexible updates.
                        AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build());
            } else {
                Toast.makeText(context, "No Update Available", Toast.LENGTH_SHORT).show();
            }
        });
        appUpdateInfoTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "update Exception", Toast.LENGTH_SHORT).show();
            }
        });

    }

}