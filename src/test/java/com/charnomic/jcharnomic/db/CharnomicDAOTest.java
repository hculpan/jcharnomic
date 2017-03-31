package com.charnomic.jcharnomic.db;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by harryculpan on 3/28/17.
 */
public class CharnomicDAOTest {

    @Test
    public void test() throws Exception {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword("blah");
        System.out.println("encrypted password='" + encryptedPassword + "'");
    }

    @Test
    public void generateSql() {
        CharnomicDAO charnomicDAO = new CharnomicDAO();
        List<Player> playerList = charnomicDAO.retrievePlayers();
        try (Connection connection = charnomicDAO.getConnection()) {
            StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
            for (Player player : playerList) {
                if (!player.getLastname().equals("Culpan")) {
                    String password = new StringBuilder(player.getLastname().toLowerCase()).reverse().toString();
                    String encryptedPassword = passwordEncryptor.encryptPassword(password);

                    System.out.println(player.lastname + ": password='" + encryptedPassword + "'");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

