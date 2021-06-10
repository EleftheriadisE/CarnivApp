package CarnivAPP.DataBase;
 
import CarnivAPP.Users.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {
    private String dbUrl;
    private User user;
    private Connection connection;

    public static Connection createConnection()
    {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/customers"; //MySQL URL με όνομα βάσης δεδομένων.
        String UserName = "root"; //MySQL UserName.
        String password = "root123"; //MySQL Password.
        
        try 
        {
            try 
            {
               Class.forName("com.mysql.jdbc.Driver"); //Φόρτωση των MySQL drivers.
            } 
            catch (ClassNotFoundException e)
            {
               e.printStackTrace();
            }       
            con = DriverManager.getConnection(url, UserName, password); //Απόπειρα σύνδεσης στη βάση δεδομένων.
            System.out.println("Printing connection object "+con);
        } 
        catch (Exception e) 
        {
           e.printStackTrace();
        }   
        return con; 
    }
   
    public DataBaseConnector(final String dbUrl) {
        this.dbUrl = dbUrl;
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(this.dbUrl);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public DataBaseConnector(final String dbUrl, final User user) {
        this.dbUrl = dbUrl;
        try {
            Class.forName("org.postgresql.Driver");
            this.user = user;
            this.connection = DriverManager.getConnection(this.dbUrl, this.user.getUserName(), this.user.getUserPassword());
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void changeDBUrl(final String newUrl) {
        this.dbUrl = newUrl;
    }

    public void changeUser(final User user) {
        this.user = user;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public User getUser() {
        return user;
    }

    public Connection getConnection() {
        return connection;
    }
}