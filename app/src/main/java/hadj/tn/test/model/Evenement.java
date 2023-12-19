package hadj.tn.test.model;

import android.graphics.Region;

public class Evenement {
    private int id;
    private String title;
    private String date;
    private String description;
    private String start_time;
    private String end_time;
    private String city;
    private String country;
    private int nbPartcipant=0;
    private int max_nb_participants;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNbPartcipant() {
        return nbPartcipant;
    }

    public void setNbPartcipant(int nbPartcipant) {
        this.nbPartcipant = nbPartcipant;
    }

    public int getMax_nb_participants() {
        return max_nb_participants;
    }

    public void setMax_nb_participants(int max_nb_participants) {
        this.max_nb_participants = max_nb_participants;
    }
}
