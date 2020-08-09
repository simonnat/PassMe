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


public class Login extends Activity {
    EditText nome,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences  prefer=getSharedPreferences("preference", Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        nome=(EditText) findViewById(R.id.editText);
        pass=(EditText) findViewById(R.id.editText2);
        Button invia=(Button)findViewById(R.id.list_back);
        invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (nome.getText().toString().equals(SimpleCrypto.decrypt("AfG.-Yu@#6", prefer.getString("nome", " "))) && pass.getText().toString().equals(SimpleCrypto.decrypt("AfG.-Yu@#6", prefer.getString("pass", " ")))) {
                        startPass();

                    }
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Nome utente o password errati", Toast.LENGTH_LONG);
                        toast.show();
                    }

                }catch(Exception e){
                    e.printStackTrace();

                }
            }
        });
       if(!prefer.getBoolean("access",false))
                  startReg();
        Button cambia=(Button) findViewById(R.id.change);
        cambia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startmod();
            }
        });
    }
    public void startmod(){
        Intent intent=new Intent(this,cambia.class);
        startActivity(intent);

    }

    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void startReg(){
        Intent intent=new Intent(this,registra.class);
        finish();
        startActivity(intent);


    }


    public void startPass(){
    Intent intent=new Intent(this,PassMe.class);
    finish();
    startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_activityw, menu);
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