package com.example.starship9.firebaseuitest;

/**
 * Created by starship9 on 13/3/17.
 */

public class Anime {

    public String animeName;
    public float animeRating;
    public String animePoster;
    public Anime(){

    }
    public Anime(String animeName, String animePoster, float animeRating){
        this.animeName = animeName;
        this.animeRating = animeRating;
        this.animePoster = animePoster;
    }

    public String getAnimeName(){
        return animeName;
    }
    public String getAnimePoster(){
        return animePoster;
    }
    public float getAnimeRating(){
        return animeRating;
    }

    public void setAnimeName(String animeName){
        this.animeName = animeName;
    }

    public void setAnimePoster(String animePoster){
        this.animePoster = animePoster;
    }
    public void setAnimeRating(float animeRating){
        this.animeRating = animeRating;
    }

}
