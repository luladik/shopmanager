package com.kapitsa.shop;
//Created by Luladik on 2/21/2016.

import com.kapitsa.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//extends Runnable
public abstract class Shop {
    protected Thread thread;

    protected static volatile Shop instance = null;

    protected final String ADD_ITEM = "INSERT IGNORE INTO shopmanager.Items VALUE (NULL, ?, ?, ?, ?)";
    protected final String ID = "SELECT id FROM shopmanager.Categories WHERE Categories.Title = ?";
    protected final String CHECK = "SELECT Title FROM shopmanager.Items WHERE Items.Title = ?";
    protected final String FIND = "SELECT * FROM Items WHERE Title = ?";
    protected final String SET_PRICE = "UPDATE Items SET price = ? WHERE Title = ?;";
    protected final String SET_STATUS = "UPDATE Items SET Status = ? WHERE Title = ?;";

    protected Shop() {
    }

    //логичнее было бы реализовать метод в классе Item, но можно и так как написано в ТЗ
    public synchronized void setItemPrice(Item item, double price) throws SQLException {

        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = DBManager.getDBConnection();
            preparedStatement = connection.prepareStatement(SET_PRICE);

            item.setPrice(price);

            preparedStatement.setDouble(1, price);
            preparedStatement.setString(2, item.getTitle());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SOMETHING WENT WRONG (setItemPrice)");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public synchronized void setItemState(Item item, StatusEnum status) throws SQLException {

        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = DBManager.getDBConnection();
            preparedStatement = connection.prepareStatement(SET_STATUS);

            item.setStatus(status);

            preparedStatement.setString(1, String.valueOf(status));
            preparedStatement.setString(2, item.getTitle());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SOMETHING WENT WRONG (setItemState)");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public synchronized Item getItemByTitle(String title) throws SQLException {

        PreparedStatement preparedStatement = null;
        Connection connection = null;
        Item item = new Item();
        try {
            connection = DBManager.getDBConnection();
            preparedStatement = connection.prepareStatement(FIND);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                item.setId(resultSet.getInt("id"));
                item.setPrice(resultSet.getDouble("Price"));
                item.setStatus(StatusEnum.valueOf(resultSet.getString("Status")));
                return item;
            } else return null;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SOMETHING WENT WRONG (getItem)");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return item;
    }

    public synchronized void addItem(Item item, String categoryTitle) throws SQLException {

        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = DBManager.getDBConnection();
            preparedStatement = connection.prepareStatement(CHECK);
            preparedStatement.setString(1, item.getTitle());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("Title").equals(item.getTitle()))
                    return;
            }

            preparedStatement = connection.prepareStatement(ID);
            preparedStatement.setString(1, categoryTitle);
            resultSet = preparedStatement.executeQuery();
            int categoryId = 0;
            while (resultSet.next())
                categoryId = resultSet.getInt(1);
            System.out.println(categoryId);
            preparedStatement.clearParameters();

            preparedStatement = connection.prepareStatement(ADD_ITEM);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setString(2, item.getTitle());
            preparedStatement.setDouble(3, item.getPrice());
            preparedStatement.setString(4, item.getStatus().toString());
            preparedStatement.executeUpdate();
            preparedStatement.clearParameters();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SOMETHING WENT WRONG (addItem)");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }
}
