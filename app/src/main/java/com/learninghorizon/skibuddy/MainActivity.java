package com.learninghorizon.skibuddy;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.learninghorizon.skibuddy.AsyncTasks.LoadProfile;
import com.learninghorizon.skibuddy.AsyncTasks.SaveLocation;
import com.learninghorizon.skibuddy.AsyncTasks.ShareLocation;
import com.learninghorizon.skibuddy.AsyncTasks.UpdateTagLine;
import com.learninghorizon.skibuddy.adpaters.HomeViewPagerAdapter;
import com.learninghorizon.skibuddy.interfaces.TagLineObserver;
import com.learninghorizon.skibuddy.model.User;
import com.learninghorizon.skibuddy.tracker.RunActivity;
import com.learninghorizon.skibuddy.utility.DataUtil;
import com.learninghorizon.skibuddy.utility.MyLocation;
import com.learninghorizon.skibuddy.utility.RoundedTransformation;
import com.learninghorizon.skibuddy.widget.TagLineDialog;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,TagLineObserver {

    View drawerHeader;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 100;
    int tabPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showMessageOKCancel("You need to allow access to Location",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return;
            }
            return;
        }
        startApplicationFlow();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private void startApplicationFlow(){
        DataUtil.getInstance().clearData();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        checkUser();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerHeader = navigationView.inflateHeaderView(R.layout.nav_header_main);
        setProfileInfo(drawerHeader);
        Button editTagLine = (Button) drawerHeader.findViewById(R.id.edit_tagline);
        editTagLine.setOnClickListener(this);

        MenuItem menuItem = (MenuItem) navigationView.getMenu().getItem(1);
        if (DataUtil.getInstance().getUser().isShareLocation() && null != menuItem){

            menuItem.setTitle(getResources().getString(R.string.remove_location_share));
        }else if(null != menuItem){
            menuItem.setTitle(getResources().getString(R.string.share_location));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  /*  Snackbar.make(view, "Replace with your own action for tab position "+tabPosition, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/

                if(tabPosition ==0){
                    Intent runActivityIntent = new Intent(getApplicationContext(), RunActivity.class);
                    startActivity(runActivityIntent);
                }
                if(tabPosition ==1){
                    Intent eventActivityIntent = new Intent(getApplicationContext(), CreateEvent.class);
                    startActivity(eventActivityIntent);

                }
            }
        });
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(Location location){
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    Address usersAddress = (geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1)).get(0);
                    String state = usersAddress.getAdminArea();
                    String city = usersAddress.getLocality();
                    String latitude = String.valueOf(location.getLatitude());
                    String longitude = String.valueOf(location.getLongitude());
                    Log.e("Longitude : ", ""+location.getLongitude());
                    new SaveLocation(getApplicationContext()).execute(state, city, latitude, longitude);
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    startApplicationFlow();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "ACCESS LOCATION Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onBackPressed() {
        try {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
            DataUtil.getInstance().clearEvents();
        }catch(Exception exception){
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {
            FacebookSdk.sdkInitialize(this.getApplicationContext());
            LoginManager.getInstance().logOut();
            Intent facebookLogin = new Intent(getApplicationContext(), FacebookLogin.class);
            startActivity(facebookLogin);
            finish();
        } else if (id == R.id.share_location) {
            if(DataUtil.getInstance().getUser().isShareLocation()){
                DataUtil.getInstance().getUser().setShareLocation(false);
                item.setTitle(getResources().getString(R.string.share_location));
            }else{
                DataUtil.getInstance().getUser().setShareLocation(true);
                item.setTitle(getResources().getString(R.string.remove_location_share));
            }
            new ShareLocation(this).execute();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setProfileInfo(View layout){
        try {
            User user = DataUtil.getInstance().getUser();
            Profile userProfile = Profile.getCurrentProfile();
            ImageView profilePic = (ImageView) layout.findViewById(R.id.profile_pic);
            TextView firstNameLastName = (TextView) layout.findViewById(R.id.firstname_lastname);
            TextView tagLine = (TextView) layout.findViewById(R.id.tag_line);
            Picasso.with(getApplicationContext()).load(userProfile.getProfilePictureUri(120, 120))
                    .transform(new RoundedTransformation(50, 4))
                    .error(R.drawable.ic_launcher)
                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                    .centerCrop()
                    .into(profilePic);
            firstNameLastName.setText(user.getFirstName().concat(" ").concat(user.getLastName()));
            tagLine.setText(user.getTagLine());
        } catch(Exception exception){
            //TODO: alert to give error message
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_tagline:{
                TagLineDialog tagLineDialog = new TagLineDialog();
                TextView tagLine = (TextView) drawerHeader.findViewById(R.id.tag_line);
                if(null != tagLine || !tagLine.getText().toString().isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("tagLine", tagLine.getText().toString());
                    tagLineDialog.setArguments(bundle);
                }
                tagLineDialog.show(getFragmentManager(), "TagLineEdit");
                tagLineDialog.registerObserver(this);
                break;
            }
        }
    }

    @Override
    public void updateData(final String newTagLine) {
        if(null != drawerHeader){
            TextView tagLine = (TextView) drawerHeader.findViewById(R.id.tag_line);
            if(null != tagLine){
                tagLine.setText(newTagLine);
                if(null!=DataUtil.getInstance().getUser()){
                    DataUtil.getInstance().getUser().setTagLine(newTagLine);
                }

                new UpdateTagLine(this).execute(DataUtil.getInstance().getUser());
            }
        }
    }

    public void checkUser(){
        if(null == DataUtil.getInstance().getUser()){
            User user = new User();
            user.setFirstName(Profile.getCurrentProfile().getFirstName());
            user.setLastName(Profile.getCurrentProfile().getLastName());
            user.setFacebookId(Profile.getCurrentProfile().getId());
            DataUtil.getInstance().setUser(user);
            try {
                new LoadProfile(this).execute(user).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
