package com.example.starship9.firebaseuitest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    ScaleAnimation scaleAnim;
    private TextView textView;
    private StaggeredGridLayoutManager layoutManager;

    //getting a reference to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    private static final String userID = "53";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.animeRecycler);
        textView = (TextView)findViewById(R.id.noAnime);
        scaleAnim = new ScaleAnimation(1.15f,0f,1.15f,0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        if(recyclerView !=null){
            recyclerView.setHasFixedSize(true);
        }

        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerAdapter<Anime,AnimeViewHolder> adapter = new FirebaseRecyclerAdapter<Anime, AnimeViewHolder>(
                Anime.class,
                R.layout.anime_item,
                AnimeViewHolder.class,
                //referencing the node where we want the database to store the data from our Object
                ref.child("users").child(userID).child("anime").getRef()
        ) {
            @Override
            protected void populateViewHolder(AnimeViewHolder viewHolder, Anime model, int position) {
                if(textView.getVisibility()== View.VISIBLE){
                    textView.setVisibility(View.GONE);
                }
                viewHolder.animeName.setText(model.getAnimeName());
                viewHolder.ratingBar.setRating(model.getAnimeRating());
                Picasso.with(MainActivity.this).load(model.getAnimePoster()).into(viewHolder.animePoster);
            }
        };

        recyclerView.setAdapter(adapter);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containerFrame, new AddAnimeFragment()).addToBackStack(null).commit();
                scaleAnim.setDuration(400);
                fab.setAnimation(scaleAnim);
                scaleAnim.start();
                scaleAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fab.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if(fab.getVisibility() == View.GONE)
            fab.setVisibility(View.VISIBLE);
    }



    public static class AnimeViewHolder extends RecyclerView.ViewHolder{
        TextView animeName;
        RatingBar ratingBar;
        ImageView animePoster;

        public AnimeViewHolder(View v) {
            super(v);
            animeName = (TextView)v.findViewById(R.id.animeName);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
            animePoster = (ImageView)v.findViewById(R.id.animePoster);
        }
    }
    }

