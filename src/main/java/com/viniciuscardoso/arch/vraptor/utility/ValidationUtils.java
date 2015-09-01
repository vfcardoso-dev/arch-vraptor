package com.viniciuscardoso.arch.vraptor.utility;

import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

    public static boolean isFileDefinedAndValid(UploadedFile arq, String mime, Integer maxSizeInMb) {
        return arq != null && arq.getSize() < (maxSizeInMb * Math.pow(1024, 2)) && arq.getContentType().equalsIgnoreCase(mime);
    }

    public static boolean isFileDefinedAndValid(UploadedFile[] arqs, String mime, Integer maxSizeInMb) {
        boolean resultado = true;
        if (arqs == null) resultado = false;
        for (UploadedFile arq : arqs) {
            if (arq.getSize() > (maxSizeInMb * Math.pow(1024, 2)) && !arq.getContentType().equalsIgnoreCase(mime)) {
                resultado = false;
            }
        }
        return resultado;
    }

    public static boolean isValidDate(String maybeDate, String format) {
        try {
            String dateStripped = maybeDate.split(" ")[0] + " 01:00:00";
            String formatStripped = format.split(" ")[0] + " HH:mm:ss";
            DateTimeFormatter fmt = DateTimeFormat.forPattern(formatStripped);
            DateTime dtTime = fmt.parseDateTime(dateStripped);
            return dtTime.toLocalDate() != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
