package com.viniciuscardoso.arch.vraptor.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

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

    /**
     * Gera uma sequência de caracteres pseudo-randômicos, por padrão em letras maiúsculas
     * @param length comprimento da sequência
     * @param includeAlphaLower adicionar caracteres minúsculos na saída
     * @param includeNumber adicionar caracteres numéricos na saída
     * @param includeSpecial adicionar caracteres especiais na saída
     * @return sequência de caracteres
     */
    public static String getRandomString(int length, boolean includeAlphaLower, boolean includeNumber, boolean includeSpecial) {
        String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (includeAlphaLower) source += "abcdefghijklmnopqrstuvwxyz";
        if (includeNumber) source += "01234567890";
        if (includeSpecial) source += "!@#$%&*()[]{}çÇ<>";
        int randomLength = source.length();
        String randomSeq = "";
        Random r = new Random();

        for(int i = 0; i < length; i++) {
            randomSeq += source.charAt(r.nextInt(randomLength));
        }

        return randomSeq;
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
        for (byte aByte : bytes) {
            int higherPart = ((aByte >> 4) & 0xf) << 4;
            int lowerPart = aByte & 0xf;
            if (higherPart == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(higherPart | lowerPart));
        }
        return s.toString();
    }
}
