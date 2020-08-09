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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class cambia extends Activity {
   EditText new_log,new_pass,confirm;
   EditText old_log,old_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences prefer=getSharedPreferences("preference",MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        final SharedPreferences.Editor editor=prefer.edit();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_cambia);
        confirm=(EditText)findViewById(R.id.confirm2);
        old_log=(EditText)findViewById(R.id.old_log);
        old_pass=(EditText)findViewById(R.id.old_pass);
        new_log=(EditText)findViewById(R.id.new_log);
        new_pass=(EditText)findViewById(R.id.new_pass);
        Button cambia=(Button)findViewById(R.id.cambia);
        cambia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (old_log.getText().toString().equals(SimpleCrypto.decrypt("AfG.-Yu@#6", prefer.getString("nome", ""))) && old_pass.getText().toString().equals(SimpleCrypto.decrypt("AfG.-Yu@#6", prefer.getString("pass", "")))) {
                        if (new_pass.getText().toString().equals(confirm.getText().toString())) {
                            if (new_log.getText().toString().length() > 0 && new_pass.getText().toString().length() >= 4) {
                                editor.putString("nome", SimpleCrypto.encrypt("AfG.-Yu@#6", new_log.getText().toString()));
                                editor.putString("pass", SimpleCrypto.encrypt("AfG.-Yu@#6", new_pass.getText().toString()));
                                editor.commit();
                                startLog();
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "La password deve essere composta da almeno 4 caratteri", Toast.LENGTH_LONG);
                                toast.show();

                                new_pass.setText("");
                                confirm.setText("");
                                new_pass.requestFocus();
                            }
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Nome utente o password errati", Toast.LENGTH_SHORT);
                            toast.show();
                            old_log.setText("");
                            old_pass.setText("");
                            old_log.requestFocus();

                        }
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Reinserisci la nuova password", Toast.LENGTH_LONG);
                        toast.show();
                        confirm.setText("");
                        new_pass.setText("");
                        new_pass.requestFocus();


                    }


                }catch(Exception e){}
            }
        });




    }
    public void onBackPressed() {

        finish();
    }

    public void startLog(){

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cambia, menu);
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
