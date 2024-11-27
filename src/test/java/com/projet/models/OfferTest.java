package com.projet.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import com.projet.database.DatabaseConnection;
import com.projet.database.UserAlreadyExistsException;

public class OfferTest {
    private Connection dbConnection;
    private User author = new User("test", "test", UserRole.USER);

    @Before
    public void setUp() throws SQLException, UserAlreadyExistsException {
        dbConnection = DatabaseConnection.getConnection();
        // Empty the users table
        // Diable foreign key checks to avoid errors
        dbConnection.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        dbConnection.prepareStatement("DELETE FROM users").executeUpdate();
        dbConnection.prepareStatement("DELETE FROM posts").executeUpdate();
        dbConnection.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        author.toDB();
    }

    @AfterAll
    public void tearDown() throws SQLException {
        dbConnection.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        dbConnection.prepareStatement("DELETE FROM users").executeUpdate();
        dbConnection.prepareStatement("DELETE FROM posts").executeUpdate();
        dbConnection.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

    @Test
    public void testToDatabase() throws SQLException {
        Offer offer = new Offer(author, "title", "content", "location");
        offer.toDatabase();
        assert offer.id != null;
        Offer offer2 = Offer.getOffer(offer.id);
        assert offer2 != null;
        assert offer2.id == offer.id;
        assert offer2.author.id == author.id;
        assert offer2.title.equals(offer.title);
    }

    @Test
    public void testGetOffers() throws SQLException {
        Offer offer = new Offer(author, "title", "content", "location");
        offer.toDatabase();
        ArrayList<Offer> offers = Offer.getOffers(0);
        assert offers.size() == 1;
        assert offers.get(0).id == offer.id;
    }

    @Test
    public void testGetMyOffers() throws SQLException {
        Offer offer = new Offer(author, "title", "content", "location");
        offer.toDatabase();
        ArrayList<Offer> offers = Offer.getMyOffers(author, 0);
        assert offers.size() == 1;
        assert offers.get(0).id == offer.id;
    }

    @Test
    public void testGetNumberOfPages() throws SQLException {
        Offer offer = new Offer(author, "title", "content", "location");
        offer.toDatabase();
        assert Offer.getNumberOfPages() == 1;
        for (int i = 0; i < Post.PAGE_SIZE; i++) {
            new Offer(author, "title", "content", "location").toDatabase();
        }
        assert Offer.getNumberOfPages() == 2;
    }
}
