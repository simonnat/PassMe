package com.simone.passme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.simone.passme.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class show2 extends Activity {
    private ViewGroup mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show2);
        mContainerView = (ViewGroup) findViewById(R.id.container);
        final SharedPreferences src = getSharedPreferences("Ps", MODE_PRIVATE);

        if(src.getInt("last",0)!=0)
            findViewById(android.R.id.empty).setVisibility(View.GONE);
        showItem();
        Button bt=(Button) findViewById(R.id.back);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startPass();
            }
        });

        Button exp=(Button)findViewById(R.id.export);
        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExternalStorageWritable()){

                    try{
                        // File outputDir = getApplicationContext().getCacheDir(); // context being the Activity pointer
                        File my = File.createTempFile("export"+((int)System.currentTimeMillis()/1000), ".txt", Environment.getExternalStorageDirectory());

                        Edit :
                    /*File createDir=new File("/sdcard/PassMe/");
                        createDir.mkdirs();
                    File my=new File("/sdcard/PassMe/export"+((int)System.currentTimeMillis()/1000)+".txt");*/

                        my.createNewFile();
                        FileOutputStream fout=new FileOutputStream(my);
                        int i=0;
                        OutputStreamWriter myoutwriter = new OutputStreamWriter(fout);
                        String row;
                        myoutwriter.write("Export PassMe"+'\n');
                        while (i != src.getInt("last", 0)) {
                            row="Sito: "+ SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("site" + i, "nf"))+"       Login: "+ SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("user" + i, "nf"))+"       Password: "+ SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("pass" + i, "nf"))+'\n';
                            myoutwriter.write(row);
                            i++;
                        }
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        // share.addCategory(file.class);
                        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(my));

                        startActivityForResult(Intent.createChooser(share, "Esporta su..."), 1);
                        Toast.makeText(getApplicationContext(), "File export" + ((int) System.currentTimeMillis() / 1000) + ".txt creato", Toast.LENGTH_LONG).show();
                        myoutwriter.close();
                        fout.close();

                    }catch(IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                    catch (Exception a){}


                }
            }
        });
    }



    private void showItem() {
        TelephonyManager t=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        final SharedPreferences src = getSharedPreferences("Ps", MODE_PRIVATE);
        if(src.getString("Ident"," ").equals(t.getDeviceId())||t.getDeviceId().equals(" ")) {
            for (int i = 0; i < src.getInt("last", 0); i++) {
                final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
                        R.layout.list_item_show, mContainerView, false);

                // Set the text in the new row to a random country.
                try {
                    ((TextView) newView.findViewById(android.R.id.text1)).setText(
                            !(SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("site" + i, "|")).equals("|") || SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("site" + i, "|")).equals("")) ? ("Sito:" + " " + SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("site" + i, "null"))) : ("Descrizione:" + " " + SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("descr" + i, "null"))));
                }catch(Exception e){};
                    final int z = i;
                // Set a click listener for the "X" button in the row that will remove the row.
                newView.findViewById(R.id.show_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startShow(z, src.getString("site" + z, "null"));

                    }
                });

                // Because mContainerView has android:animateLayoutChanges set to true,
                // adding this view is automatically animated.
                mContainerView.addView(newView, 0);
            }
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    public void onBackPressed() {
        finish();
    }

    public void startPass(){

        finish();

    }



    public void startShow(int i,String site){
        Intent intent=new Intent(this,list.class);
        intent.putExtra(getPackageName()+".id",i);
        intent.putExtra(getPackageName()+".site",site);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show2, menu);
        menu.add("Contattaci").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"simon.n@hotmail.it"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PassMe");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Messaggio...");
                startActivity(Intent.createChooser(emailIntent, "Invia email..."));
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
