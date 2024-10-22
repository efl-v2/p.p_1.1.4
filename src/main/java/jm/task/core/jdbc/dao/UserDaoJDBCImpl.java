package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        // Код для создания таблицы
        try (Connection connection = Util.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                    " id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                    " name VARCHAR(255) NOT NULL,\n" +
                    " lastName VARCHAR(255) NOT NULL,\n" +
                    " age TINYINT NOT NULL\n" +
                    " )";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        // Код для удаления таблицы
        try (Connection connection = Util.getConnection()) {
            String sql = "DROP TABLE IF EXISTS users";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        // Код для сохранения пользователя в таблицу
        Connection connection = Util.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)");
            connection.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        // Код для удаления пользователя по id из таблицы
        Connection connection = Util.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        // Код для получения всех пользователей из таблицы
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }
    @Override
    public void cleanUsersTable() {
        // Код для очистки таблицы
        Connection connection = Util.getConnection();
        try {
            connection.setAutoCommit(false);
            String sql = "DELETE FROM users";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
