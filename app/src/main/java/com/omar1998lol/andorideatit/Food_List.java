package com.omar1998lol.andorideatit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omar1998lol.andorideatit.Interface.ItemClickListener;
import com.omar1998lol.andorideatit.Model.Category;
import com.omar1998lol.andorideatit.Model.Food;
import com.omar1998lol.andorideatit.ViewHolder.FoodViewHolder;
import com.omar1998lol.andorideatit.ViewHolder.MenuViewHolder;
import com.squareup.picasso.Picasso;

public class Food_List extends AppCompatActivity {
    FirebaseDatabase database ;
    DatabaseReference foodList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager ;
    String categoryId ="";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food__list);
        //fire base
        database = FirebaseDatabase.getInstance();
        foodList =database.getReference("Foods");
        recyclerView = findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // get Intent here
        if (!getIntent().equals(null))
            categoryId =getIntent().getStringExtra("categoryId");
        if (!categoryId.isEmpty() && !categoryId.equals(null) )
            loadListFood(categoryId);

    }

    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId) //like :select * from foods where MenuID =
                 ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int postion, boolean isLongClick) {
                        Toast.makeText(Food_List.this,""+local.getName(),Toast.LENGTH_LONG).show();
                    }
                });


            }
        };
        Log.d("Tag", String.valueOf(adapter.getItemCount()));
            recyclerView.setAdapter(adapter);
    }
}
