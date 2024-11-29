package com.projet.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import com.projet.database.DatabaseConnection;
import com.projet.database.UserAlreadyExistsException;

public class MissionTest {
    private Connection dbConnection;
    private UserInNeed author = new UserInNeed("test", "test");

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
        assertNotNull(mission.id);
        Mission mission2 = Mission.getMission(mission.id);
        assertNotNull(mission2);
        assertEquals(mission.id, mission2.id);
        assertEquals(author.id, mission2.author.id);
        assertEquals(mission.title, mission2.title);
    }

    @Test
    public void testValidate() throws SQLException {
        Mission mission = new Mission(author, "title", "content", "location");
        mission.toDatabase();
        mission.validate();
        assertEquals(MissionStatus.VALIDATED, mission.status);
        Mission mission2 = Mission.getMission(mission.id);
        assertNotNull(mission2);
        assertEquals(MissionStatus.VALIDATED, mission2.status);
    }

    @Test
    public void testRefuse() throws SQLException {
        Mission mission = new Mission(author, "title", "content", "location");
        mission.toDatabase();
        mission.refuse("reason");
        assertEquals(MissionStatus.REFUSED, mission.status);
        assertEquals("reason", mission.refusalReason);
        Mission mission2 = Mission.getMission(mission.id);
        assertNotNull(mission2);
        assertEquals(MissionStatus.REFUSED, mission2.status);
        assertEquals("reason", mission2.refusalReason);
    }

    @Test
    public void testGetMissions() throws SQLException {
        Mission mission = new Mission(author, "title", "content", "location");
        mission.toDatabase();
        List<Mission> missions = Mission.getMissions(0);
        assertEquals(1, missions.size());
        assertEquals(mission.id, missions.get(0).id);
    }

    @Test
    public void testGetMyMissions() throws SQLException {
        Mission mission = new Mission(author, "title", "content", "location");
        mission.toDatabase();
        List<Mission> missions = Mission.getMyMissions(author, 0);
        assertEquals(1, missions.size());
        assertEquals(mission.id, missions.get(0).id);
    }

    @Test
    public void testGetNumberOfPages() throws SQLException {
        Mission mission = new Mission(author, "title", "content", "location");
        mission.toDatabase();
        assertEquals(1, Mission.getNumberOfPages());
        for (int i = 0; i < Post.PAGE_SIZE; i++) {
            new Mission(author, "title", "content", "location").toDatabase();
        }
        assertEquals(2, Mission.getNumberOfPages());
    }
}
