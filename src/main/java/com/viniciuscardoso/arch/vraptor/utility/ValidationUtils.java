package com.viniciuscardoso.arch.vraptor.utility;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Project: trf-main
 * User: Vinicius Cardoso
 * Date: 28/04/14
 * Time: 10:16
 */
public class ValidationUtils {
    public static boolean isStringNullOrEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static boolean isLengthLessThan(String str, int num) {
        return str.length() < num;
    }

    public static boolean isEmailValido(String email) {
        return email.matches("^[\\w\\-\\._\\+%]+@(?:[\\w-]+\\.)+[\\w]{2,6}$");
    }

    public static boolean isDataMenorQueHoje(LocalDate data) {
        return data.isBefore(new LocalDate());
    }

    public static boolean isDataMenorQueHoje(LocalDateTime data) {
        return data.isBefore(new LocalDateTime(DateTimeZone.forID("America/Sao_Paulo")));
    }

    public static boolean isListNullOrEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isSetNullOrEmpty(Set set) {
        return set == null || set.size() == 0;
    }

    public static boolean containsIgnoreCase(StringBuilder strBuilder, String pattern) {
        return containsIgnoreCase(strBuilder.toString(), pattern);
    }

    public static boolean containsIgnoreCase(String str, String pattern) {
        return Pattern.compile(Pattern.quote(pattern), Pattern.CASE_INSENSITIVE).matcher(str).find();
    }
}
