package com.charnomic.jcharnomic.db;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.junit.Test;

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
}

