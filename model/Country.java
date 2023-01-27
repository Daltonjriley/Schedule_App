package model;

import java.time.LocalDateTime;

/**
 * This class creates the country objects.
 */
public class Country {

    private int id;
    private String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Converts hexocde into id and name for the Country combo box.
     * @return The string with the id and name that is returned.
     */
    @Override
    public String toString(){
        return ("#" + Integer.toString(getId()) + " " + getName());
    }


}
