package com.omar1998lol.andorideatit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.signin.SignIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar1998lol.andorideatit.Model.User;
import com.omar1998lol.andorideatit.common.Common;

public class SignActivity extends AppCompatActivity {

    EditText edtPhone ,edtPassword;
    Button btnSignIn ;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        edtPhone = findViewById(R.id.editPhone);
        edtPassword = findViewById(R.id.editPassword);
        btnSignIn =findViewById(R.id.btnSignIn);

        database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");



         btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
          public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignActivity.this);
                mDialog.setMessage("please waiting ...");
                mDialog.show();


            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // check if user exist or not in database
                    if (dataSnapshot.child(edtPhone.getText().toString()).exists())
                    {
                        // Get User Information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                            if (user.getPassword().equals(edtPassword.getText().toString()))
                            {
                                Intent intent = new Intent(SignActivity.this,Home.class);
                                Common.currentUser = user;
                                startActivity(intent);

                                finish();


                            } else {
                                Toast.makeText(SignActivity.this, "sign in failed !!", Toast.LENGTH_SHORT).show();
                            }
                    }

                    else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignActivity.this, "user not exist in database", Toast.LENGTH_SHORT).show();
                        }

                    }

                  @Override
                  public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
    }
}
