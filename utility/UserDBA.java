package utility;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides create, read, update and delete function for the user objects.
 */
public class UserDBA {

    private static User currentUser;

    public static User getCurrentUser(){
        return currentUser;
    }

    public static Boolean verifyLogin(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name='" + userName + "' AND Password='" + password + "'";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            int id = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String passWord = rs.getString("Password");
            currentUser = new User(id, username, passWord);

            return Boolean.TRUE;
        }
        else{
            return Boolean.FALSE;
        }
    }
}

