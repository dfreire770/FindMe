package com.dkmm.findme.findme;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
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
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;
    String usuario;
    private GoogleApiClient mGoogleApiClient;

    private String frameAcual;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connectWebSocket();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frameAcual == "MapFragment"){
                    //lo q hace el boton en el mapa
                }else if(frameAcual == "ContactoFragment"){
                    new DialogContacto().show(getSupportFragmentManager(), "createSimpleDialog");
                }
            }
        });

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Alerta", "onAuthStateChanged:signed_in:" + user.getUid());

                    usuario=user.getDisplayName();
                    View headerLayout = navigationView.getHeaderView(0);
                    TextView name = (TextView) headerLayout.findViewById(R.id.usernametextView);
                    name.setText(user.getDisplayName());
                    TextView email = (TextView) headerLayout.findViewById(R.id.emailtextView);
                    email.setText(user.getEmail());

                    mWebSocketClient.send(user.getDisplayName());

                } else {
                    // User is signed out
                    Log.d("Alerta: ", "no hay usuario");
                }
            }
        };


        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Fragment fragment = new MapsFragment();

        manejoFragments(new MapFragment());
        frameAcual = "MapFragment";
      /* if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        }
        else
            Log.d("Error","no existe el fragment");*/

    }

    private void signOut() {
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //updateUI(null);
                        finish();
                        Log.d("Alerta","Desconectado");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            frameAcual ="ContactoFragment";

            manejoFragments(new FragmentContacto());


        } else if (id == R.id.nav_slideshow) {

        }
        else if (id == R.id.nav_send) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //Enviar Mensaje

    private WebSocketClient mWebSocketClient;

    private void connectWebSocket() {
        boolean bool;
        URI uri;
        try {
            //uri = new URI("ws://findme.zapto.org:1337");
            uri = new URI("ws://192.168.0.2:1337");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                //mWebSocketClient.send(user.getDisplayName());
            }

            @Override
            public void onMessage(String s) {
                final String message = s;

              runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TextView textView = (TextView)findViewById(R.id.messages);
                        //textView.setText(textView.getText() + "\n" + message);
                        Log.d("datos: ",message);
                        try {

                            JSONObject jsonObject = new JSONObject(message);
                            String autor=jsonObject.getString("author");
                            //if(!autor.equals(usuario)) {
                                String text = jsonObject.getString("author") + ": " + jsonObject.getString("text");


                                Vibrator v = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                                // Vibrate for 500 milliseconds
                                v.vibrate(400);

                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0 /* Request code */, intent,
                                        PendingIntent.FLAG_ONE_SHOT);

                                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MainActivity.this)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle("FindMe")
                                        .setContentText(text)
                                        .setAutoCancel(true)
                                        .setSound(defaultSoundUri)
                                        //.setContentIntent(pendingIntent)
                                        .setPriority(Notification.PRIORITY_MAX);

                            NotificationCompat.InboxStyle inboxStyle =
                                    new NotificationCompat.InboxStyle();
                            String[] events = new String[6];
// Sets a title for the Inbox in expanded layout
                            inboxStyle.setBigContentTitle("Detalles:");

// Moves events into the expanded layout
                            for (int i=0; i < events.length; i++) {

                                inboxStyle.addLine(events[i]);
                            }
// Moves the expanded layout object into the notification object.
                            notificationBuilder.setStyle(inboxStyle);

                                NotificationManager notificationManager =
                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                notificationManager.notify(0 , notificationBuilder.build());
                            //}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }


            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();

    }

    public void sendMessage(LatLng Position){
        mWebSocketClient.send("Hola, soy "+usuario+". Sali de la posicion. Ayudame!");
        //mWebSocketClient.send("Hola, sali de la posicion inicial");

    }



    /*--------CAMBIO FRAGMENTS-------------*/

    public void manejoFragments(Fragment fragment){

        if(fragment != null){
            Fragment fragmentActual = fragment;
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace((R.id.content_main, fragmentActual);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

}
