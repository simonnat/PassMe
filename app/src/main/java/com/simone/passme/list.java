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
import android.widget.TextView;



public class list extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_list);
        try {
            final SharedPreferences src = this.getSharedPreferences("Ps", MODE_PRIVATE);
            TextView log = (TextView) findViewById(R.id.showlog);
            TextView pass = (TextView) findViewById(R.id.showpass);
            TextView sitelist = (TextView) findViewById(R.id.sitelist);
            Intent intent = getIntent();
            int id = intent.getIntExtra(getPackageName() + ".id", 0);
            String site = intent.getStringExtra(getPackageName() + ".site");
            sitelist.setText(SimpleCrypto.decrypt("XdBkIl_.N@", site));
            log.setText(SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("user" + id, "null")));
            pass.setText(SimpleCrypto.decrypt("XdBkIl_.N@", src.getString("pass" + id, "null")));


            Button back = (Button) findViewById(R.id.list_back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finish();
                }
            });
        }catch (Exception e){}
    }
    public void onBackPressed() {

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
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
