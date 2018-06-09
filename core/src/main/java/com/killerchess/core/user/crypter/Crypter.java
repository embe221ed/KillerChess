package com.killerchess.core.user.crypter;

import org.mindrot.jbcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Crypter {

    private static final int LOG_ROUNDS = 12;
    private static final String HASHING_ALGORITHM_NAME = "SHA1PRNG";

    public static String getSalt() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance(HASHING_ALGORITHM_NAME);
            return BCrypt.gensalt(LOG_ROUNDS, secureRandom);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static String getHashedPassword(String passwordToHash, String salt) {
        return BCrypt.hashpw(passwordToHash, salt);
    }

}
