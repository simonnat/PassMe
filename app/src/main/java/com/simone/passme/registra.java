package com.simone.passme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class registra extends Activity {

    EditText nome,pass,confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefer=getSharedPreferences("preference", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=prefer.edit();
        editor.putBoolean("firstVisit",true);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_registra);
        confirm=(EditText)findViewById(R.id.confirm);
        nome=(EditText) findViewById(R.id.editText4);
        pass=(EditText) findViewById(R.id.editText5);


    Button crea = (Button) findViewById(R.id.registrazione);
    crea.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                if(pass.getText().toString().equals(confirm.getText().toString())) {
                    if (nome.getText().toString().length() > 0 && pass.getText().toString().length() >= 4) {
                        editor.putBoolean("access", true);
                        editor.putString("nome", SimpleCrypto.encrypt("AfG.-Yu@#6", nome.getText().toString()));
                        editor.putString("pass", SimpleCrypto.encrypt("AfG.-Yu@#6", pass.getText().toString()));
                        editor.commit();
                        startLog();

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "La password deve essere composta da almeno quattro caratteri", Toast.LENGTH_LONG);
                        toast.show();
                        pass.setText("");
                        pass.requestFocus();

                    }
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Le password non corrispondono", Toast.LENGTH_LONG);
                    toast.show();
                    pass.setText("");
                    confirm.setText("");
                    pass.requestFocus();
                }
            } catch (Exception e) {}
        }
            });

        }


    public void startLog(){
        Intent intent=new Intent(this,Login.class);
        finish();
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registra, menu);
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
