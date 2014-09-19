package com.viniciuscardoso.arch.vraptor.infra.converter;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

/**
 * @author Vinícius Cardoso
 * @project logplan
 */
@Convert(UploadedFile.class)
public class CustomUploadedFileConverter implements Converter<UploadedFile> {

    private final HttpServletRequest request;

    public CustomUploadedFileConverter(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public UploadedFile convert(String value, Class<? extends UploadedFile> type, ResourceBundle bundle) {
        Object upload = request.getAttribute(value);
        return upload == null ? null : type.cast(upload);
    }
}
