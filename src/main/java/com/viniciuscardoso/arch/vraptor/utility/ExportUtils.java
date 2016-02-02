package com.viniciuscardoso.arch.vraptor.utility;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public static void html2pdf(InputStream input, OutputStream out) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();

        XMLWorkerHelper.getInstance().parseXHtml(writer, document, input);
        document.close();
    }
}
