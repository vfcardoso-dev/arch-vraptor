package com.viniciuscardoso.arch.vraptor.infra.adapter;

import com.google.gson.*;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

/**
 * Created by Vinícius on 19/02/2016.
 */
public class GsonLocalDateTypeAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
    private static final DateTimeFormatter DATE_FORMAT = ISODateTimeFormat.date();

    @Override
    public LocalDate deserialize(final JsonElement je, final Type type,
                                 final JsonDeserializationContext jdc) throws JsonParseException
    {
        final String dateAsString = je.getAsString();
        if (dateAsString.length() == 0)
        {
            return null;
        }
        else
        {
            return DATE_FORMAT.parseDateTime(dateAsString).toLocalDate();
        }
    }

    @Override
    public JsonElement serialize(final LocalDate src, final Type typeOfSrc,
                                 final JsonSerializationContext context)
    {
        String retVal;
        if (src == null)
        {
            retVal = "";
        }
        else
        {
            retVal = DATE_FORMAT.print(src);
        }
        return new JsonPrimitive(retVal);
    }
}
