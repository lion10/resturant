package com.omar1998lol.andorideatit;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar1998lol.andorideatit.Model.User;

public class SignUpActivity extends AppCompatActivity {
    EditText edtPhone ,editName,edtPassword;
    Button btnSignUp ;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtPhone = findViewById(R.id.editPhone);
        editName = findViewById(R.id.editName);
        edtPassword = findViewById(R.id.editPassword);

        btnSignUp =findViewById(R.id.btnSignUp);

        database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");


            btnSignUp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
                    mDialog.setMessage("please waiting ...");
                    mDialog.show();
                    table_user.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check if aleardy user phone
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists())
                            {
                                mDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "Phone Number already register", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                mDialog.dismiss();
                                User user = new User(editName.getText().toString(),edtPassword.getText().toString());
                                table_user.child(edtPhone.getText().toString()).setValue(user);
                                Toast.makeText(SignUpActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                                finish();
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

