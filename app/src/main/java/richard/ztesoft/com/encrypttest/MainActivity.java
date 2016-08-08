package richard.ztesoft.com.encrypttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import javax.crypto.SecretKey;

import richard.ztesoft.com.encrypttest.utils.AESEncryptor;
import richard.ztesoft.com.encrypttest.utils.SecretUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    String content = "test";
    String password = "12345678";
    byte[] encryptResult;
    String hexString;

    TextView textView;
    ListView listView;
    ArrayAdapter arrayAdapter;
    SecretKey secretKey = SecretUtils.generateSessionKey(password);
    private static String arrayTests[] = {
            "AES加密解密","DES加密解密"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView)findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayTests);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG,"item = "+i);
                switch (i){
                    case 0:
                        Intent intent = new Intent(MainActivity.this,AESTestActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this,DESTestActivity.class);
                        startActivity(intent1);
                        break;
                }
            }
        });

        textView = (TextView)findViewById(R.id.text);

        textView.setText(content);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_encrypt) {

            //加密

            try {
                //hexString = AESEncryptor.encrypt(password,content);
                byte[] encryptBytes = SecretUtils.encrypt(content,secretKey);
                hexString = SecretUtils.parseByte2HexStr(encryptBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
            textView.setText("加密后的16进制码："+hexString);

            return true;
        }else if(id == R.id.action_decrypt){
            String result = "";
            try {
                byte[] decryptedBytes = SecretUtils.decrypt(SecretUtils.parseHexStr2Byte(hexString),secretKey);
                result = new String(decryptedBytes,"UTF-8");
                //result = AESEncryptor.decrypt(password,hexString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            textView.setText("解密后：" + result);

            return true;
        }else if(id == R.id.action_settings){
            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
