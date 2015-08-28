package com.viniciuscardoso.arch.vraptor.infra.converter;

import br.com.caelum.vraptor.ioc.Component;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import org.joda.time.LocalTime;

/**
 * Project: trf-main
 * User: Vinicius Cardoso
 * Date: 11/07/14
 * Time: 12:12
 */
@Component
public class CustomLocalTimeConverterJson implements SingleValueConverter {

    @Override
    public String toString(Object value) {
        LocalTime t = new LocalTime(value);
        return t.toString("HH:mm");
    }

    @SuppressWarnings("rawtypes")
    public boolean canConvert(Class clazz) {
        return LocalTime.class.isAssignableFrom(clazz);
    }

    @Override
    public Object fromString(String arg0) {
        return null;
    }

}
