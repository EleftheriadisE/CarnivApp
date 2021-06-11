package CarnivAPP.Interfaces;

import CarnivAPP.DataBase.DataBaseCursorHolder;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserInterface {

    DataBaseCursorHolder fetchOrders(Connection connection, String[] filterArguments) throws SQLException;

    DataBaseCursorHolder fetchInventory(Connection connection, String[] filterArguments) throws SQLException;
 
}