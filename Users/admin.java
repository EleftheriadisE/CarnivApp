package CarnivAPP.Users;

import CarnivAPP.DataBase.DataBaseCursorHolder;
import CarnivAPP.DataBase.DataBaseUtils;
import CarnivAPP.Exceptions.InventoryException;
import CarnivAPP.Products.Product;

import java.sql.Connection;
import java.sql.SQLException;

public class Administrator extends User {

    public Administrator(final String Name, final String Password) {
        super(Name, Password);
    }

    public double getTotalPriceOfInventory(final Connection connection) throws SQLException {
        final DataBaseCursorHolder cursor = DataBaseUtils.innerJoinTables(connection, "Ðñïúüíôá", "Áðüèåìá", "Ôáõôüôçôá Ðñïúüíôïò",
                new String[]{"ÔéìÞ Ðñïúüíôïò", "Ðïóüôçôá Ðñïúüíôùí"}, new String[]{});
        double total = 0.0;
        while (cursor.getResults().next()) {
            total += cursor.getResults().getDouble(1) * cursor.getResults().getDouble(2);
        }
        return total;
    }

    public double getTotalPriceOfAllOrders(final Connection connection) throws SQLException {
        final DataBaseCursorHolder cursor = DataBaseUtils.filterFromTable(connection, "Ðáñáããåëßåò", new String[]{"Óõíïëéêü Êüóôïò"}, new String[]{});
        double total = 0.0;
        while (cursor.getResults().next()) {
            total += cursor.getResults().getDouble(1);
        }
        return total;
    }

    public void increaseProductAmountInInventory(final Connection connection, final Product product, final int amount)
            throws SQLException {
        final DataBaseCursorHolder cursor = DataBaseUtils.innerJoinTables(connection, "Ðñïúüíôá", "Áðüèåìá", "Ôáõôüôçôá Ðñïúüíôïò",
                new String[]{"Ôáõôüôçôá Ðñïúüíôïò", "Ðïóüôçôá Ðñïúüíôùí"}, new String[]{String.format("¼íïìá Ðñïúüíôïò = '%s'", product.getName())});

        while (cursor.getResults().next()) {
            final int productId = cursor.getResults().getInt(1);
            final int productAmount = cursor.getResults().getInt(2);
            final int newAmount = productAmount + amount;
            DataBaseUtils.updateTable(connection, "Áðüèåìá", new String[]{"Ðïóüôçôá Ðñïúüíôùí"}, new String[]{Integer.toString(newAmount)},
                    new String[]{String.format("Ôáõôüôçôá Ðñïúüíôïò = %d", productId)});

        }
    }

    public void addNewProductToInventory(final Connection connection, final Product product, final int amount) throws SQLException {
        DataBaseUtils.insertSpecificIntoTable(connection, "Products", new String[]{"product_name", "product_weight", "product_price"},
                new String[]{String.format("'%s'", product.getName()), Double.toString(product.getWeight()), Double.toString(product.getPrice())});

        final DataBaseCursorHolder cursor = DataBaseUtils.filterFromTable(connection, "Products", new String[]{"product_id"},
                new String[]{String.format("¼íïìá Ðñïúüíôïò = '%s'", product.getName())});
        while (cursor.getResults().next()) {
            final int productId = cursor.getResults().getInt(1);
            DataBaseUtils.insertSpecificIntoTable(connection, "Áðüèåìá", new String[]{"Ôáõôüôçôá Ðñïúüíôïò", "Ðïóüôçôá Ðñïúüíôùí"},
                    new String[]{Integer.toString(productId), Integer.toString(amount)});
        }
        cursor.closeCursor();
    }

    public void decreaseProductAmountInInventory(final Connection connection, final Product product, final int amount)
            throws SQLException, InventoryException {
        final DataBaseCursorHolder cursor = DataBaseUtils.innerJoinTables(connection, "Ðñïúüíôá", "Áðüèåìá", "Ôáõôüôçôá Ðñïúüíôïò",
                new String[]{"Ôáõôüôçôá Ðñïúüíôïò", "Ðïóüôçôá Ðñïúüíôùí"}, new String[]{String.format("'Ïíïìá Ðñïúüíôïò = '%s'", product.getName())});

        while (cursor.getResults().next()) {
            final int productId = cursor.getResults().getInt(1);
            final int productAmount = cursor.getResults().getInt(2);
            final int newAmount = productAmount - amount;

            if (newAmount >= 0) {
                DataBaseUtils.updateTable(connection, "Áðüèåìá", new String[]{"Ðïóüôçôá Ðñïúüíôùí"}, new String[]{Integer.toString(newAmount)},
                        new String[]{String.format("Ôáõôüôçôá Ðñïúüíôïò = %d", productId)});
            } else {
                throw new InventoryException("Äåí ìðïñåßôå íá áöáéñÝóåôå ôçí óõãêåêñéìÝíç ðïóüôçôá ðñïúüíôùí");
            }
        }
    }

    public void createUser(final Connection connection, final String UserName, final String UserPassword, final boolean privilege) {
        DataBaseUtils.insertSpecificIntoTable(connection, "×ñÞóôåò", new String[]{"¼íïìá ×ñÞóôç", "Êùäéêüò ×ñÞóôç", "Äéêáéþìáôá"},
                new String[]{String.format("'%s'", UserName), String.format("'%s'", UserPassword), privilege ? "'t'" : "'f'"});
    }

    public void deleteUser(final Connection connection, final String UserName) {
        DataBaseUtils.deleteFromTable(connection, "×ñÞóôåò", new String[]{String.format("¼íïìá ×ñÞóôç = '%s'", UserName)});
    }
}