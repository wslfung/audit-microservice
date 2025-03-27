package com.wilsonfung.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtil {
    private static final String SHA_1 = "SHA-1";

    public static String sha1(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_1);
            byte[] hash = digest.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not available", e);
        }
    }
}
