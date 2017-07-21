package com.gunghi.tgwing.lolock.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.gunghi.tgwing.lolock.R;
import com.tsengvn.typekit.TypekitContextWrapper;

public class Register2Activity extends AppCompatActivity {

    EditText nameEditText;
    EditText addressEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        nameEditText = (EditText)findViewById(R.id.editTextName);
        addressEditText = (EditText) findViewById(R.id.editTextAddress);

        // TODO: 2017. 7. 21. GPS 를 켜야되나 ...?

    }




    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


}
