package CarnivAPP.DataBase;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCursorHolder {
    private ResultSet results;
    private Statement statement;

    public DataBaseCursorHolder(final ResultSet resultSet, final Statement statement) {
        this.results = resultSet;
        this.statement = statement;
    }

    public ResultSet getResults() {
        return results;
    }

    public void closeCursor() {
        try {
            this.results.close();
            this.statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}