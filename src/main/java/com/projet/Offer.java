package com.projet;

import java.sql.SQLException;

public class Offer extends Post {
    public Offer(String title, String description, User author) {
        super(title, description, author);
    }

    public void toDB() throws SQLException {
        super.toDB(true);
    }
}
