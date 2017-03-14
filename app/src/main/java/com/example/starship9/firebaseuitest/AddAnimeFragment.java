package com.example.starship9.firebaseuitest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddAnimeFragment extends Fragment implements View.OnClickListener{

    private DatabaseReference ref;
    private TextInputEditText animeName;
    private TextInputEditText animeLogo;
    private RatingBar ratingBar;
    private Button button;

    public AddAnimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_anime, container, false);
        animeName = (TextInputEditText)v.findViewById(R.id.animeName);
        animeLogo = (TextInputEditText)v.findViewById(R.id.animeLogo);
        button = (Button)v.findViewById(R.id.submitButton);
        ratingBar  = (RatingBar)v.findViewById(R.id.ratingBar);

        ref = FirebaseDatabase.getInstance().getReference();
        button.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.submitButton:
                if(!animeName.equals(null) && !animeLogo.equals(null)){
                    myNewMovie("53", animeName.getText().toString().trim(), animeLogo.getText().toString().trim(),ratingBar.getRating());
                }else{
                    if(animeName.equals(null)){
                        Toast.makeText(getContext(), "Please enter an anime name", Toast.LENGTH_SHORT).show();
                    }else if(animeLogo.equals(null)){
                        Toast.makeText(getContext(), "Please specify an image URL", Toast.LENGTH_SHORT).show();
                    }
                }
                //removes current fragment
                getActivity().onBackPressed();
                break;
        }
    }

    private void myNewMovie(String userId, String animeName, String animePoster, float rating) {
        //Creating a anime object with user defined variables
        Anime anime = new Anime(animeName,animePoster,rating);
        //referring to movies node and setting the values from anime object to that location
        ref.child("users").child(userId).child("anime").push().setValue(anime);
    }

}
