package CarnivAPP.Users;

import CarnivAPP.Basket;
import CarnivAPP.DataBase.DataBaseCursorHolder;
import CarnivAPP.DataBase.DataBaseUtils;
import CarnivAPP.Exceptions.BasketException;
import CarnivAPP.Products.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Supplier extends User {
    private int idRetrievedBasket = -1;

    public Supplier(final String Name, final String Password) {
        super(Name, Password);
    }

    public void addProductToBasket(final Basket basket, final Product product, final int Amount) throws BasketException {
        basket.addProducts(product, Amount);
    }

    public void removeProductFromBasket(final Basket basket, final Product product, final int Amount) throws BasketException {
        basket.removeProducts(product, Amount);
    }

    public int retrievedBasketId() {
        return this.idRetrievedBasket;
    }

    public void setRetrievedBasketId(final int id) {
        this.idRetrievedBasket = id;
    }

    public void setRetrievedBasketId(final Connection connection) {
        this.idRetrievedBasket = getCurrentBasketId(connection);
    }

    private int getCurrentBasketId(final Connection connection) {
        final DataBaseCursorHolder cursor;
        int id = -1;

        try {
            cursor = DataBaseUtils.filterFromTable(connection, "Καλάθια",
                    new String[]{"Κωδικός Καλθιού"}, new String[]{String.format("Ιδιοκτήτης Καλαθιού='%s'", super.getUserName()), "Και", "Επεξεργάσηκε='f'"});
            while (cursor.getResults().next()) {
                id = cursor.getResults().getInt(1);
            }
            cursor.closeCursor();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void saveBasket(final Connection connection, final Basket basket) {
        final List<String> basketDetails = basket.toDBFormat();
        final String names = basketDetails.get(0);
        final String amounts = basketDetails.get(1);

        DataBaseUtils.insertSpecificIntoTable(connection, "Καλάθια", new String[]{"Ιδιοκτήτης Καλαθιού", "Ονόματα Προϊόντων", "Ποσότητα Προϊόντων"}, new String[]{String.format("'%s'", super.getUserName()), String.format("'%s'", names), String.format("%s", amounts)});
    }

    public Basket restoreBasket(final Connection connection) throws SQLException {
        final DataBaseCursorHolder cursor = DataBaseUtils.filterFromTable(connection, "Καλάθια", new String[]{"Ονόματα Προϊόντων", "Ποσότητα Προϊόντων"}, new String[]{String.format("Ιδιοκτήτης Καλαθιού = '%s'", getUserName()), "Και", "Επεξεργάστηκε = FALSE"});
        cursor.getResults().next();
        final String productsName = cursor.getResults().getString(1);
        final String productsAmount = cursor.getResults().getString(2);
        final Basket restoredBasket = new Basket();
        restoredBasket.restoreFromDB(productsName, productsAmount);
        cursor.closeCursor();
        return restoredBasket;
    }

    public void completeOrder(final Connection connection, final String address) {
        DataBaseUtils.insertSpecificIntoTable(connection, "Παραγγελίες", new String[]{"Κωδικός Καλαθιού", "Ιδιοκτήτης Παραγγελίας", "Διεύθυνση"}, new String[]{String.valueOf(idRetrievedBasket), String.format("'%s'", super.getUserName()), String.format("'%s'", address)});
        DataBaseUtils.updateTable(connection, "Καλάθια", new String[]{"Επεξεργάστηκε"}, new String[]{"'t'"}, new String[]{"Επεξεργάστηκε='f'", "Και", String.format("Ιδιοκτήτης Καλαθιού='%s'", super.getUserName()), "Και", String.format("Κωδικός Καλαθιού=%d", idRetrievedBasket)});

    }
}