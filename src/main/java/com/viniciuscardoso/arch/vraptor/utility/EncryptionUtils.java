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

    /**
     * Método que retorna uma sequência de caracteres randômicos, em uppercase por default.
     * @param length Comprimento da sequência.
     * @param includeLowercase incluir caracteres em lowercase
     * @param includeNumber incluir caracteres numéricos
     * @param includeSpecial incluir caracteres especiais
     * @return sequência de caracteres
     */
    public static String getRandomString(int length, boolean includeLowercase, boolean includeNumber, boolean includeSpecial) {
        String seq = "";
        if(length > 0) {
            for (int i = 0; i < length; i++) {
                seq += getRandomString(length, includeLowercase, includeNumber, includeSpecial);
            }
            return seq;
        } else {
            return seq;
        }
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

    private static Character getRandomChar(boolean includeLowercase, boolean includeNumber, boolean includeSpecial) {
        int caracter = ConvertUtils.toInteger(33 + Math.floor((Math.random() * 100) + 1));
        caracter = (caracter > 126) ? 126 : caracter;

        //para entender recursão primeiro vc precisa entender recursão.
        if(caracter > 64 && caracter < 91) {
            return getRandomChar(includeLowercase, includeNumber, includeSpecial);
        } else if(!includeLowercase && (caracter > 96 && caracter < 123)) {
            return getRandomChar(includeLowercase, includeNumber, includeSpecial);
        } else if(!includeNumber && (caracter > 47 && caracter < 58)) {
            return getRandomChar(includeLowercase, includeNumber, includeSpecial);
        } else if(!includeSpecial && ((caracter >= 33 && caracter < 48) || (caracter > 57 && caracter < 65) ||
                (caracter > 90 && caracter < 97) || (caracter > 122 && caracter <= 126))) {
            return getRandomChar(includeLowercase, includeNumber, includeSpecial);
        } else {
            return (char)caracter;
        }
    }
}
