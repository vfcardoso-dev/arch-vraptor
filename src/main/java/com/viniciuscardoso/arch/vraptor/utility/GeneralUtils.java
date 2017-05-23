package com.viniciuscardoso.arch.vraptor.utility;

import java.io.*;

public class GeneralUtils {

    public static String extractStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
