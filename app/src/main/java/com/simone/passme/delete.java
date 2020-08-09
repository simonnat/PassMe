package com.simone.passme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;



public class delete extends Activity {
    private ViewGroup mContainerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_delete);
        mContainerView = (ViewGroup) findViewById(R.id.container2);
        final SharedPreferences src = getSharedPreferences("Ps", MODE_PRIVATE);

        Button bt = (Button) findViewById(R.id.back2);

        bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                startPass();
            }
        });

        if(src.getInt("last",0)!=0)
            findViewById(android.R.id.empty).setVisibility(View.GONE);
            printtoDelete();

    }


    public void printtoDelete(){
        final SharedPreferences src = getSharedPreferences("Ps", MODE_PRIVATE);
        TelephonyManager t = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (src.getString("Ident", " ").equals(t.getDeviceId()) || t.getDeviceId().equals(" ")) {


            for (int i = 0; i < src.getInt("last", 0); i++) {
               // try {
                    final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
                            R.layout.list_item_delete, mContainerView, false);

                    // Set the text in the new row to a random country.
                    try {
                        ((TextView) newView.findViewById(android.R.id.text1)).setText(
                                !(SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("site" + i, "|")).equals("|") || SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("site" + i, "|")).equals("")) ? ("Sito:" + " " + SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("site" + i, "null"))) : ("Descrizione:" + " " + SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("descr" + i, "null"))));
                    }catch(Exception e){};
                    final int z = i;
                    // Set a click listener for the "X" button in the row that will remove the row.
                    newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final SharedPreferences src = getSharedPreferences("Ps", MODE_PRIVATE);
                            final SharedPreferences.Editor editor = src.edit();
                            int id = z;
                            for (int i = id; i < ((src.getInt("last", 0)) - 1); i++) {
                                editor.putString("site" + i, src.getString("site" + (i + 1), "null"));
                                editor.putString("user" + i, src.getString("user" + (i + 1), "null"));
                                editor.putString("descr" + i, src.getString("descr" + (i + 1), "null"));
                                editor.putString("pass" + i, src.getString("pass" + (i + 1), "null"));
                                editor.commit();

                                }
                            mContainerView.removeView(newView);

                            // If there are no rows remaining, show the empty view.
                            if (mContainerView.getChildCount() == 0) {
                                findViewById(android.R.id.empty).setVisibility(View.VISIBLE);


                            }
                            Toast toast = Toast.makeText(getApplicationContext(), "Voce eliminata", Toast.LENGTH_SHORT);
                            toast.show();
                            editor.remove("site" + (src.getInt("last", 1)-1));
                            editor.remove("user" + (src.getInt("last", 1)-1));
                            editor.remove("descr" + (src.getInt("last", 1)-1));
                            editor.remove("pass" + (src.getInt("last", 1)-1));
                            editor.putInt("last", (src.getInt("last", 1)-1) );
                            editor.commit();
                            // Because mContainerView has android:animateLayoutChanges set to true,
                            // adding this view is automatically animated.
                        }
                    });

                    mContainerView.addView(newView, 0);



        }





    }

    }


    public void onBackPressed() {

        finish();


    }
        public void startPass() {

            finish();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.delete, menu);
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
    }

