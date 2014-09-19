package com.viniciuscardoso.arch.vraptor.infra.converter;

import br.com.caelum.vraptor.ioc.Component;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import org.joda.time.LocalDateTime;

/**
 * Project: trf-main
 * User: Vinicius Cardoso
 * Date: 11/07/14
 * Time: 12:13
 */
@Component
public class CustomLocalDateTimeConverterJson implements SingleValueConverter {

    @Override
    public String toString(Object value) {
        LocalDateTime d = new LocalDateTime(value);
        String data = d.toString("dd/MM/yyyy HH:mm:ss");
        return data;

    }

    @SuppressWarnings("rawtypes")
    public boolean canConvert(Class clazz) {
        return LocalDateTime.class.isAssignableFrom(clazz);
    }

    @Override
    public Object fromString(String arg0) {
        return null;
    }

}
