package com.viniciuscardoso.arch.vraptor.infra.converter;

import br.com.caelum.vraptor.ioc.Component;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import org.joda.time.LocalDate;

/**
 * Project: trf-main
 * User: Vinicius Cardoso
 * Date: 11/07/14
 * Time: 12:12
 */
@Component
public class CustomLocalDateConverterJson implements SingleValueConverter {

    @Override
    public String toString(Object value) {
        LocalDate d = new LocalDate(value);
        String data = d.toString("dd/MM/yyyy");
        return data;

    }

    @SuppressWarnings("rawtypes")
    public boolean canConvert(Class clazz) {
        return LocalDate.class.isAssignableFrom(clazz);
    }

    @Override
    public Object fromString(String arg0) {
        return null;
    }

}
