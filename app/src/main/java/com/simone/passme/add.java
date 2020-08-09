package com.simone.passme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.SecureRandom;


public class add extends Activity {



    EditText site,nome,pass,descri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add);


        final SharedPreferences src=this.getSharedPreferences("Ps", MODE_PRIVATE);
        final SharedPreferences.Editor editor=src.edit();
    final TelephonyManager t=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        site=(EditText)findViewById(R.id.sito);
        nome=(EditText)findViewById(R.id.nome_utente);
        pass=(EditText)findViewById(R.id.password);
        descri=(EditText)findViewById(R.id.description);
        final Button inv=(Button)findViewById(R.id.send);
        editor.putInt("last",(src.getInt("last",0)));
        editor.putString("Ident",t.getDeviceId());
        editor.commit();
        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    editor.putString("pass" + (src.getInt("last", 0)), SimpleCrypto.encrypt("XdBkIl_.N@", pass.getText().toString()));
                    editor.putString("site" + (src.getInt("last", 0)), SimpleCrypto.encrypt("XdBkIl_.N@", site.getText().toString()));
                    editor.putString("descr" + (src.getInt("last", 0)), SimpleCrypto.encrypt("XdBkIl_.N@", descri.getText().toString()));
                    editor.putString("user" + (src.getInt("last", 0)), SimpleCrypto.encrypt("XdBkIl_.N@", nome.getText().toString()));
                    editor.putInt("last", ((src.getInt("last", 0)) + 1));
                    editor.commit();
                    site.setText("");
                    nome.setText("");
                    pass.setText("");
                    descri.setText("");
                    site.requestFocus();

                    Toast toast = Toast.makeText(getApplicationContext(), "Voce aggiunta", Toast.LENGTH_SHORT);
                    toast.show();
                }catch(Exception a){}
            }
        });

        ImageButton create=(ImageButton)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SecureRandom random = new SecureRandom();
               String rnd= new BigInteger(130, random).toString(20);
               pass.setText(rnd);
            }
        });

        Button back=(Button)findViewById(R.id.fine);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPass();
            }
        });
    }
















    public void onBackPressed() {
        finish();

    }








    public void startPass(){
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
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
