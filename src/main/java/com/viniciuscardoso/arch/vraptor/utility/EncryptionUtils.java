package com.viniciuscardoso.arch.vraptor.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Vinícius Cardoso
 * @project GDR
 */
/**
 * Métodos utilitários para criptografia e hashing.
 *
 * @author Vinícius Cardoso
 */
public class EncryptionUtils {

    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA-1";
    public static final String SHA256 = "SHA-256";

    /**
     * Método que gera string criptografada
     *
     * @param phrase String a ser criptografada
     * @param algorithm MD5, SHA-1, SHA-256
     * @return String criptografada
     */
    public static String getHash(String phrase, String algorithm) {
        return getHexString(getByteHash(phrase, algorithm));
    }

    private static byte[] getByteHash(String phrase, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(phrase.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String getHexString(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int higherPart = ((bytes[i] >> 4) & 0xf) << 4;
            int lowerPart = bytes[i] & 0xf;
            if (higherPart == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(higherPart | lowerPart));
        }
        return s.toString();
    }
}
