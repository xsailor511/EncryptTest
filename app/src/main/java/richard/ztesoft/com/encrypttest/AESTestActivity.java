package richard.ztesoft.com.encrypttest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import richard.ztesoft.com.encrypttest.utils.AESEncryptor;
import richard.ztesoft.com.encrypttest.utils.ExternalStorage;
import richard.ztesoft.com.encrypttest.utils.FileUtils;

public class AESTestActivity extends AppCompatActivity {

    private static final String TAG = "AESTestActivity123";
    private static int WRITE_STORAGE_REQUEST = 0;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static List<Map<String,String>> list1;
    private static List<Map<String,String>> list2;
    private static List<Map<String,String>> list3;

    private static final String KEY_ACTION = "ACTION";
    private static final String KEY_RESULT = "RESULT";
    private static String password = "12345678";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aestest);
        initListData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Log.i("xsailor","shouldShowRequestPermissionRationale");
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_STORAGE_REQUEST);
            }
            //申请WRITE_EXTERNAL_STORAGE权限

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aestest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";



        public PlaceholderFragment() {
        }



        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);return fragment;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_aestest, container, false);
            ListView listView = (ListView)rootView.findViewById(R.id.fragment_list_view);

            SimpleAdapter simpleAdapter = null;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                     simpleAdapter = new SimpleAdapter(getContext(),list1,
                             android.R.layout.simple_list_item_2,
                             new String[]{KEY_ACTION,KEY_RESULT},
                             new int[]{android.R.id.text1,android.R.id.text2});
                    break;
                case 2:
                    simpleAdapter = new SimpleAdapter(getContext(),list2,
                            android.R.layout.simple_list_item_2,
                            new String[]{KEY_ACTION,KEY_RESULT},
                            new int[]{android.R.id.text1,android.R.id.text2});
                    break;
                case 3:
                    simpleAdapter = new SimpleAdapter(getContext(),list3,
                            android.R.layout.simple_list_item_2,
                            new String[]{KEY_ACTION,KEY_RESULT},
                            new int[]{android.R.id.text1,android.R.id.text2});
                    break;
                default:
                    break;
            }
            listView.setAdapter(simpleAdapter);
            FragmentListOnItemClickListener listOnItemClickListener =
                    new FragmentListOnItemClickListener(getArguments().getInt(ARG_SECTION_NUMBER),simpleAdapter,getContext());
            listView.setOnItemClickListener(listOnItemClickListener);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "AES";
                case 1:
                    return "AES文件";
                case 2:
                    return "JNI";
            }
            return null;
        }
    }

    public static class FragmentListOnItemClickListener implements AdapterView.OnItemClickListener{

        int section;
        SimpleAdapter simpleAdapter;
        Context context;
        public FragmentListOnItemClickListener(int section, SimpleAdapter simpleAdapter, Context context){
            this.section = section;
            this.simpleAdapter = simpleAdapter;
            this.context = context;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.i(TAG,"section = "+ section + " index = "+i);
            if (section==1){
                switch (i){
                    case 1:
                        try {
                            Map<String,String> map = list1.get(0);
                            Map<String,String> map1 = list1.get(1);
                            Map<String,String> map2 = list1.get(2);
                            Map<String,String> map3 = list1.get(3);
                            String cipherState = map1.get(KEY_RESULT);
                            if (cipherState.equals("NO")){
                                String content = map.get(KEY_RESULT);
                                String password = map3.get(KEY_RESULT);

                                content = AESEncryptor.encrypt(password,content);
                                map.put(KEY_RESULT,content);
                                map1.put(KEY_RESULT,"YES");
                                map2.put(KEY_RESULT,"NO");
                                if (simpleAdapter!=null){
                                    simpleAdapter.notifyDataSetChanged();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            Map<String,String> map = list1.get(0);
                            Map<String,String> map1 = list1.get(1);
                            Map<String,String> map2 = list1.get(2);
                            Map<String,String> map3 = list1.get(3);
                            String cipherState = map2.get(KEY_RESULT);
                            if (cipherState.equals("NO")){
                                String content = map.get(KEY_RESULT);
                                String password = map3.get(KEY_RESULT);

                                content = AESEncryptor.decrypt(password,content);
                                map.put(KEY_RESULT,content);
                                map1.put(KEY_RESULT,"NO");
                                map2.put(KEY_RESULT,"YES");
                                if (simpleAdapter!=null){
                                    simpleAdapter.notifyDataSetChanged();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }else if(section==2){
                if(ExternalStorage.isWritable()){
                    String sdCardPath = ExternalStorage.getSdCardPath();
                    File encryptDir = new File(sdCardPath+ "/MIUI");
                    if(!encryptDir.exists()||!encryptDir.isDirectory()){
                        encryptDir.mkdir();
                    }
                }else {
                    Toast.makeText(context,"无法读写磁盘",Toast.LENGTH_SHORT).show();
                }
                switch (i){
                    case 0:
                        if(ExternalStorage.isWritable()){
                            String sdCardPath = ExternalStorage.getSdCardPath();
                            File normalFile = new File(sdCardPath+ "/MIUI/normal");
                            if(!normalFile.exists()){
                                FileUtils.writeTxtFile("hello123",normalFile.getPath());
                            }
                        }else {
                            Toast.makeText(context,"无法读写磁盘",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        Map<String,String> map = list2.get(0);
                        Map<String,String> map1 = list2.get(1);
                        Map<String,String> map2 = list2.get(2);
                        Map<String,String> map3 = list2.get(3);
                        String cipherState = map1.get(KEY_RESULT);
                        if (cipherState.equals("NO")){

                        }
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
            }else if(section==3){
                switch (i){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
            }
        }
    }


    void initListData(){
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        Map<String,String> map11 = new HashMap<>();
        map11.put(KEY_ACTION,"加密前");
        map11.put(KEY_RESULT,"helloworld");
        Map<String,String> map12 = new HashMap<>();
        map12.put(KEY_ACTION,"加密");
        map12.put(KEY_RESULT,"NO");
        Map<String,String> map13 = new HashMap<>();
        map13.put(KEY_ACTION,"解密");
        map13.put(KEY_RESULT,"YES");
        Map<String,String> map14 = new HashMap<>();
        map14.put(KEY_ACTION,"密匙");
        map14.put(KEY_RESULT,"12345678");
        list1.add(map11);
        list1.add(map12);
        list1.add(map13);
        list1.add(map14);

        Map<String,String> map21 = new HashMap<>();
        map21.put(KEY_ACTION,"加密前文件内容");
        map21.put(KEY_RESULT,"helloworld");
        Map<String,String> map22 = new HashMap<>();
        map22.put(KEY_ACTION,"加密文件");
        map22.put(KEY_RESULT,"NO");
        Map<String,String> map23 = new HashMap<>();
        map23.put(KEY_ACTION,"解密文件");
        map23.put(KEY_RESULT,"YES");
        Map<String,String> map24 = new HashMap<>();
        map24.put(KEY_ACTION,"密匙");
        map24.put(KEY_RESULT,"12345678");
        list2.add(map21);
        list2.add(map22);
        list2.add(map23);
        list2.add(map24);

        Map<String,String> map31 = new HashMap<>();
        map31.put(KEY_ACTION,"jni加密前");
        map31.put(KEY_RESULT,"helloworld");
        Map<String,String> map32 = new HashMap<>();
        map32.put(KEY_ACTION,"jni加密文件");
        map32.put(KEY_RESULT,"NO");
        Map<String,String> map33 = new HashMap<>();
        map33.put(KEY_ACTION,"jni解密文件");
        map33.put(KEY_RESULT,"YES");
        Map<String,String> map34 = new HashMap<>();
        map34.put(KEY_ACTION,"jni提供的密匙");
        map34.put(KEY_RESULT,"");
        list3.add(map31);
        list3.add(map32);
        list3.add(map33);
        list3.add(map34);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //doNext(requestCode,grantResults);
        for (int i=0;i<permissions.length;i++){
            Log.i("xsailor","permission "+i + " = "+permissions[i]);
        }

        for (int i=0;i<grantResults.length;i++){
            Log.i("xsailor","grantResults "+i + " = "+grantResults[i]);
        }


        File sd = Environment.getExternalStorageDirectory();
        boolean can_write = sd.canWrite();
        boolean can_read = sd.canRead();
        android.util.Log.i("xsailor","onRequestPermissionsResult can_write==============================="+can_write);
        android.util.Log.i("xsailor","onRequestPermissionsResult can_read==============================="+can_read);
    }
}
