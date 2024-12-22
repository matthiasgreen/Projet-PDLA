package com.projet.models.post;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import com.projet.database.DatabaseConnection;
import com.projet.models.user.UserAlreadyExistsException;
import com.projet.models.user.Volunteer;

public class OfferTest {
    private Connection dbConnection;
    private Volunteer author = new Volunteer("test", "test");

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
        assertNotNull(offer.id);
        Offer offer2 = Offer.getOffer(offer.id);
        assertNotNull(offer2);
        assertEquals(offer.id, offer2.id);
        assertEquals(author.id, offer2.author.id);
        assertEquals(offer.title, offer2.title);
    }

    @Test
    public void testGetOffers() throws SQLException {
        Offer offer = new Offer(author, "title", "content", "location");
        offer.toDatabase();
        List<Offer> offers = Offer.getOffers(0);
        assertNotNull(offers);
        assertEquals(1, offers.size());
        assertEquals(offer.id, offers.get(0).id);
    }

    @Test
    public void testGetMyOffers() throws SQLException {
        Offer offer = new Offer(author, "title", "content", "location");
        offer.toDatabase();
        List<Offer> offers = Offer.getMyOffers(author, 0);
        assertNotNull(offers);
        assertEquals(1, offers.size());
        assertEquals(offer.id, offers.get(0).id);
    }

    @Test
    public void testGetNumberOfPages() throws SQLException {
        Offer offer = new Offer(author, "title", "content", "location");
        offer.toDatabase();
        assertEquals(1, Offer.getNumberOfPages());
        for (int i = 0; i < Post.PAGE_SIZE; i++) {
            new Offer(author, "title", "content", "location").toDatabase();
        }
        assertEquals(2, Offer.getNumberOfPages());
    }
}
