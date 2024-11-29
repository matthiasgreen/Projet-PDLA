package com.projet.database;

import java.sql.SQLException;

@FunctionalInterface
public interface SQLResultFunction<ResultSet, T> {
    T apply(ResultSet resultSet) throws SQLException;
}
