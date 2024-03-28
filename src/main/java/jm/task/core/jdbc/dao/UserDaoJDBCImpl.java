package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try(Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    "  `name` VARCHAR(45) NOT NULL," +
                    "  `lastName` VARCHAR(45) NOT NULL," +
                    "  `age` TINYINT(3) NOT NULL)";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println("Connection failed");
        }

    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Connection failed");
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        String sql = "INSERT INTO users(name, lastName, age) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Connection failed");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }


    }

    public void removeUserById(long id) throws SQLException {

        String sql = "DELETE FROM users WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public List<User> getAllUsers() {
        List <User> userList = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection connection = Util.getConnection()) {
            Statement createStatement = connection.createStatement();
            ResultSet resultSet = createStatement.executeQuery(sql);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "TRUNCATE TABLE users";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Connection failed");
        }

    }
}
