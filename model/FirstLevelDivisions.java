package model;

import java.time.LocalDateTime;

/**
 * This class creates the first level divisions objects.
 */
public class FirstLevelDivisions {
    private int id;
    private String division;
    private int country_id;

    public FirstLevelDivisions(int id, String division, int country_id) {
        this.id = id;
        this.division = division;
        this.country_id = country_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    @Override
    public String toString(){
        return ("#" + Integer.toString(getId()) + " " + getDivision());
    }
}
