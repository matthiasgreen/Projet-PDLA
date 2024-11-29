package com.projet.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlUtility {
    public static ResultSet executeUpdate(String sql, Object... params) throws SQLException {
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
        statement.executeUpdate();
        return statement.getGeneratedKeys();
    }

    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
        return statement.executeQuery();
    }

    public static <T> List<T> executeQuery(String sql, SQLResultFunction<ResultSet, T> function, Object... params) throws SQLException {
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
        ResultSet resultSet = statement.executeQuery();
        List<T> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(function.apply(resultSet));
        }
        return results;
    }
}
