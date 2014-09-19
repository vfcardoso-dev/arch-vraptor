package com.viniciuscardoso.arch.vraptor.infra.converter;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.serialization.ProxyInitializer;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilder;
import br.com.caelum.vraptor.serialization.xstream.XStreamJSONSerialization;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * Project: trf-main
 * User: Vinicius Cardoso
 * Date: 15/07/14
 * Time: 10:05
 */

@Component
public class CustomJSONSerialization extends XStreamJSONSerialization {

    public CustomJSONSerialization(HttpServletResponse response, TypeNameExtractor extractor, ProxyInitializer initializer, XStreamBuilder builder) {
        super(response, extractor, initializer, builder);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected XStream getXStream() {
        XStream xstream = super.getXStream();

        xstream.registerConverter(new CollectionConverter(xstream.getMapper()) {
            @Override
            @SuppressWarnings("rawtypes")
            public boolean canConvert(Class type) {
                return Collection.class.isAssignableFrom(type);
            }
        });

        return xstream;
    }

}
