package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides create, read, update and delete function for the first level division objects.
 */
public class DivisionDBA {

    private static ObservableList<FirstLevelDivisions> divisionList = DivisionDBA.getAllDivisions();

    public static ObservableList<FirstLevelDivisions> getDivisionList(){
        return divisionList;
    }

    public static ObservableList<FirstLevelDivisions> getAllDivisions(){
        ObservableList<FirstLevelDivisions> divisionList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryID = rs.getInt("COUNTRY_ID");
                String name = rs.getString("Division");
                int id = rs.getInt("Division_ID");
                FirstLevelDivisions D = new FirstLevelDivisions(id, name, countryID);
                divisionList.add(D);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionList;
    }
}
