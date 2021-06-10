package CarnivAPP.Registration;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import CarnivAPP.Registration.RegisterBean;
import CarnivAPP.DataBase.DataBaseConnector;
 
public class RegisterDao { 
    public String registerUser(RegisterBean registerBean)
    {
        String FullName = registerBean.getFullName();
        String Email = registerBean.getEmail();
        String UserName = registerBean.getUserName();
        String Password = registerBean.getPassword();
        
        Connection con = null;
        PreparedStatement preparedStatement = null;         
        try
        {
            con = DataBaseConnector.createConnection();
            String query = "Εισάγετε Χρήστες(ID Χρήστη, Πλήρες Όνομα, E-mail, Όνομα Χρήστη, Κωδικός Χρήστη) τιμές (NULL,?,?,?,?)";
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, FullName);
            preparedStatement.setString(2, Email);
            preparedStatement.setString(3, UserName);
            preparedStatement.setString(4, Password);
            
            int i= preparedStatement.executeUpdate();
            
            if (i!=0) //Για επιβεβαίωση εισαγωγής των δεδομένων στη βάση δεδομένων.
            return "Επιτυχία"; 
        }
        catch(SQLException e)
        {
           e.printStackTrace();
        }       
        return "Κάτι πήγε στραβά.";  //Μήνυμα αποτυχίας.
    }
}