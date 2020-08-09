package com.simone.passme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class PassMe extends Activity {
    /** The view to show the ad. */
    private AdView adView;

    /* Your ad unit id. Replace with your actual ad unit id. */
    private static final String AD_UNIT_ID = "ca-app-pub-3716566814810367/6767813334";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences src=getSharedPreferences("preference",MODE_PRIVATE);










        final SharedPreferences.Editor editor=src.edit();
    if(src.getBoolean("firstVisit",true)){

            setContentView(R.layout.activity_passme_first);

        editor.putBoolean("firstVisit",false);
        editor.commit();
                    //startActivity(new Intent(getApplicationContext(),PassMe.class));




    }else{

            setContentView(R.layout.activity_passme);

       /* Switch layout=(Switch)findViewById(R.id.tablet_layout);
        layout.setChecked(src.getBoolean("tablet_layout",false));
        layout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){

                    editor.putBoolean("tablet_layout",b);
                    editor.commit();
                    finish();
                    startActivity(new Intent(getApplicationContext(),PassMe.class));

                }else {
                    editor.putBoolean("tablet_layout", b);
                    editor.commit();
                    finish();
                    startActivity(new Intent(getApplicationContext(),PassMe.class));

                }
            }
        });*/
        //startActivity(new Intent(getApplicationContext(),PassMe.class));
    }


       adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AD_UNIT_ID);
        LinearLayout l=(LinearLayout)findViewById(R.id.ad);
        l.addView(adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("0A091378DED33469C178D37873ADA7AC")
                .addTestDevice("6B81EEE6060C8A31C4844E7197C6602F")
                .build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);


        ImageButton add=(ImageButton) findViewById(R.id.aggiungi);
        ImageButton del=(ImageButton) findViewById(R.id.elimina);
        ImageButton show=(ImageButton) findViewById(R.id.visualizza);
        ImageButton exit=(ImageButton) findViewById(R.id.esci);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAdd();

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDel();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startShow();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLog();
            }
        });

    }
    public void onBackPressed() {
        SharedPreferences src=getSharedPreferences("preference",MODE_PRIVATE);
        final SharedPreferences.Editor editor=src.edit();
        editor.putBoolean("firstVisit",false);
        editor.commit();
        startActivity(new Intent(getApplicationContext(),Login.class));

        finish();


    }



    public void startLog(){
        SharedPreferences src=getSharedPreferences("preference",MODE_PRIVATE);
        final SharedPreferences.Editor editor=src.edit();
        editor.putBoolean("firstVisit",false);
        editor.commit();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();


    }


    public void startAdd(){
        SharedPreferences src=getSharedPreferences("preference",MODE_PRIVATE);
        final SharedPreferences.Editor editor=src.edit();
        editor.putBoolean("firstVisit",false);
        editor.commit();
        Intent intent=new Intent(this,add.class);
        startActivity(intent);
    }

    public void startDel(){
        SharedPreferences src=getSharedPreferences("preference",MODE_PRIVATE);
        final SharedPreferences.Editor editor=src.edit();
        editor.putBoolean("firstVisit",false);
        editor.commit();
        Intent intent=new Intent(this,delete.class);

        startActivity(intent);
    }


    public void startShow(){
        SharedPreferences src=getSharedPreferences("preference",MODE_PRIVATE);
        final SharedPreferences.Editor editor=src.edit();
        editor.putBoolean("firstVisit",false);
        editor.commit();
        Intent intent=new Intent(this,show2.class);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        menu.add("Contattaci").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"simon.n@hotmail.it"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PassMe");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Messaggio...");


                startActivity(Intent.createChooser(emailIntent, "Invia email..."));;
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called before the activity is destroyed. */
    @Override
    public void onDestroy() {
        // Destroy the AdView.
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

}
