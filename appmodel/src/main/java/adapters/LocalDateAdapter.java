package adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    @Override
    public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
        if (localDate == null) {
            jsonWriter.nullValue();
            return;
        }

        jsonWriter.beginObject();
        jsonWriter.name("day");
        jsonWriter.value(localDate.getDayOfMonth());
        jsonWriter.name("month");
        jsonWriter.value(localDate.getMonthValue());
        jsonWriter.name("year");
        jsonWriter.value(localDate.getYear());
        jsonWriter.endObject();
    }

    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }

        int day = 0, month = 0, year = 0;
        String fieldname = null;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            JsonToken token = jsonReader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = jsonReader.nextName();
            }

            if ("day".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                day = jsonReader.nextInt();
            }

            if("month".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                month = jsonReader.nextInt();
            }

            if("year".equals(fieldname)) {
                //move to next token
                token = jsonReader.peek();
                year = jsonReader.nextInt();
            }
        }

        jsonReader.endObject();

        return LocalDate.of(year, month, day);
    }
}
