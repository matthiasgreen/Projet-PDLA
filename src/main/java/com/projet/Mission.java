package com.projet;

import java.sql.SQLException;

public class Mission extends Post {
    public Mission(String title, String description, User author) {
        super(title, description, author);
    }

    public void toDB() throws SQLException {
        super.toDB(false);
    }
}
