package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides create, read, update and delete function for the country objects.
 */
public class CountryDBA {

    private static ObservableList<Country> countryList = getAllCountries();

    public static ObservableList<Country> getCountryList(){return countryList;}

    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country C = new Country(countryId, countryName);
                countryList.add(C);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countryList;
    }
}
