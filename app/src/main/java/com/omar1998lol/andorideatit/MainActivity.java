package com.omar1998lol.andorideatit;


import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Button btnSignIn ;
    Button btnSignUp ;
    TextView txtSlogan ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn =findViewById(R.id.btnSignIn);
        btnSignUp =findViewById(R.id.btnSignUp);


        txtSlogan =findViewById(R.id.txtSlogan);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/germaniaone-regular.ttf");
        txtSlogan.setTypeface(typeface);



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignActivity.class);
                startActivity(intent);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
