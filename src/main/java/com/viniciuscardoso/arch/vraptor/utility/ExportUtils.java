package com.viniciuscardoso.arch.vraptor.utility;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 16:02
 */

public class ExportUtils {

    public static void html2pdf(String input, OutputStream out) throws DocumentException, IOException {
        html2pdf(new ByteArrayInputStream(input.getBytes()), out);
    }

    public static void html2pdf(org.jsoup.nodes.Document doc, OutputStream out) throws DocumentException, IOException {
        html2pdf(new ByteArrayInputStream(doc.html().getBytes()), out);
    }

    @SuppressWarnings("deprecation")
    public static void html2pdf(InputStream input, OutputStream out) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();
        HTMLWorker hw = new HTMLWorker(document);
        hw.parse(new InputStreamReader(input));
        document.close();
    }
}
