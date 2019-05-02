package com.omar1998lol.andorideatit;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar1998lol.andorideatit.Database.Database;
import com.omar1998lol.andorideatit.Model.Food;
import com.omar1998lol.andorideatit.Model.Order;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

        TextView food_name ,food_price,food_description ;
        ImageView food_image ;
        CollapsingToolbarLayout collapsingToolbarLayout;
        FloatingActionButton btnCart;
        ElegantNumberButton numberButton;
        String foodId= "";
        FirebaseDatabase database;
        DatabaseReference foods ;
        Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);


        database = FirebaseDatabase.getInstance();
        foods =database.getReference("Foods");

        numberButton = findViewById(R.id.number_button);
        btnCart  =findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()
                ));

                Toast.makeText(FoodDetail.this,"Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });

        food_description = findViewById(R.id.food_description);
        food_price = findViewById(R.id.food_price);
        food_name = findViewById(R.id.food_name);
        food_image = findViewById(R.id.food_img);

        collapsingToolbarLayout =findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (!getIntent().equals(null))
            foodId =getIntent().getStringExtra("FoodId");
        if(!foodId.isEmpty())
            getDetailFood(foodId);

    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                currentFood =dataSnapshot.getValue(Food.class);
                //set Image
                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
