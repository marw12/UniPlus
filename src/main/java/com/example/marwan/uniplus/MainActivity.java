package com.example.marwan.uniplus;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<String> countryList;
    ListView listView;

    ListView simpleList;
    String[] countryNames={"United States", "Canada", "United Kingdom", "Australia"};
    int[] flags={R.drawable.us, R.drawable.ca, R.drawable.gb, R.drawable.au};



    public void getCountryJson() {
        String jsonData;
        try {
            InputStream is = getAssets().open("countrylist.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonData = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                countryList.add(obj.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);


        countryList= new ArrayList<>();
        getCountryJson();
        Log.i("Size of country list: ", Integer.toString(countryList.size()));

        AutoCompleteTextView autoCompleteTextView= findViewById((R.id.autoCompleteTextView));
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countryList);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1);


        simpleList = (ListView) findViewById(R.id.simpleListView);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), countryNames, flags);
        simpleList.setAdapter(customAdapter);



    }
}
