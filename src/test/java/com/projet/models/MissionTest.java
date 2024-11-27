package com.projet.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import com.projet.database.DatabaseConnection;
import com.projet.database.UserAlreadyExistsException;

public class MissionTest {
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
        Mission mission = new Mission(author, "title", "content", "location");
        mission.toDatabase();
        assert mission.id != null;
        Mission mission2 = Mission.getMission(mission.id);
        assert mission2 != null;
        assert mission2.id == mission.id;
        assert mission2.author.id == author.id;
        assert mission2.title.equals(mission.title);
    }

    @Test
    public void testValidate() throws SQLException {
        Mission mission = new Mission(author, "title", "content", "location");
        mission.toDatabase();
        mission.validate();
        assert mission.status == MissionStatus.VALIDATED;
        Mission mission2 = Mission.getMission(mission.id);
        assert mission2 != null;
        assert mission2.status == MissionStatus.VALIDATED;
    }

    @Test
    public void testRefuse() throws SQLException {
        Mission mission = new Mission(author, "title", "content", "location");
        mission.toDatabase();
        mission.refuse("reason");
        assert mission.status == MissionStatus.REFUSED;
        assert mission.refusalReason.equals("reason");
        Mission mission2 = Mission.getMission(mission.id);
        assert mission2 != null;
        assert mission2.status == MissionStatus.REFUSED;
        assert mission2.refusalReason.equals("reason");
    }

    @Test
    public void testGetMissions() throws SQLException {
        Mission mission = new Mission(author, "title", "content", "location");
        mission.toDatabase();
        ArrayList<Mission> missions = Mission.getMissions(0);
        assert missions.size() == 1;
        assert missions.get(0).id == mission.id;
    }

    @Test
    public void testGetMyMissions() throws SQLException {
        Mission mission = new Mission(author, "title", "content", "location");
        mission.toDatabase();
        ArrayList<Mission> missions = Mission.getMyMissions(author, 0);
        assert missions.size() == 1;
        assert missions.get(0).id == mission.id;
    }

    @Test
    public void testGetNumberOfPages() throws SQLException {
        Mission mission = new Mission(author, "title", "content", "location");
        mission.toDatabase();
        assert Mission.getNumberOfPages() == 1;
        for (int i = 0; i < Post.PAGE_SIZE; i++) {
            new Mission(author, "title", "content", "location").toDatabase();
        }
        assert Mission.getNumberOfPages() == 2;
    }
}
