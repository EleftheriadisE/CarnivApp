package CarnivAPP.Users;

import CarnivAPP.Basket;
import CarnivAPP.DataBase.DataBaseCursorHolder;
import CarnivAPP.DataBase.DataBaseUtils;
import CarnivAPP.Interfaces.UserInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User implements UserInterface {
    private String UserName;
    private String UserPassword;
    private Basket basket;

    public User(final String Name, final String Password) {
        this.UserName = Name;
        this.UserPassword = Password;
    }

    @Override
    public DataBaseCursorHolder fetchOrders(final Connection connection, final String[] filterArguments) throws SQLException {
        //¸÷åé ôï äéêáßùìá ï ×ñÞóôçò;
        DataBaseCursorHolder cursor = DataBaseUtils.filterFromTable(connection, "×ñÞóôåò", new String[]{"Äéêáéþìáôá"}, new String[]{String.format("¼íïìá ×ñÞóôç = '%s'", UserName)});
        cursor.getResults().next();

        final boolean userPrivilege = cursor.getResults().getBoolean(1);
        cursor.closeCursor();

        if (userPrivilege) {
            cursor = DataBaseUtils.innerJoinTables(connection, "ÊáëÜèéá", "Ðáñáããåëßåò", "Êùäéêïß Êáëáèéþí", new String[]{"Ïíüìáôá Ðñïúüíôùí", "Ðïóüôçôá Ðñïúüíôùí", "Äéåýèõíóç"}, filterArguments);
            return cursor;
        } else {
            final List<String> nameAndFilterArguments = new ArrayList<>();
            nameAndFilterArguments.add(String.format("Ðáñáããåëßá Áðü ôïí ×ñÞóôç = '%s'", UserName));
            if (filterArguments.length != 0) {
                nameAndFilterArguments.add("Êáé");
            }
            nameAndFilterArguments.addAll(Arrays.asList(filterArguments));
            cursor = DataBaseUtils.innerJoinTables(connection, "ÊáëÜèéá", "Ðáñáããåëßåò", "Êùäéêïß Êáëáèéþí", new String[]{"Ïíüìáôá Ðñïúüíôùí", "Ðïóüôçôá Ðñïúüíôùí", "Äéåýèõíóç"}, nameAndFilterArguments.toArray(new String[0]));
            return cursor;
        }
    }

    @Override
    public DataBaseCursorHolder fetchInventory(final Connection connection, final String[] filterArguments) throws SQLException {
        return DataBaseUtils.innerJoinTables(connection, "Ðñïúüíôá", "Áðüèåìá", "Ôáõôüôçôá Ðñïúüíôïò", new String[]{"Ôáõôüôçôá Ðñïúüíôïò", "¼íïìá Ðñïúüíôïò", "ÂÜñïò Ðñïúüíôïò", "ÔéìÞ Ðñïúüíôïò", "Ðïóüôçôá Ðñïúüíôùí"}, filterArguments);
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setBasket(final Basket basket) {
        this.basket = basket;
    }

    public Basket getBasket() {
        return basket;
    }
}