package com.frikiplanet.nasaapod.apod.data.remote;

import com.squareup.moshi.JsonReader;

import java.io.IOException;

public class ServerErrorParser {

    public static String parse(JsonReader reader) throws IOException {
        String error = "";

        try {
            while (reader.hasNext()) {
                switch (reader.peek()) {
                    case BEGIN_OBJECT:
                        reader.beginObject();
                        break;
                    case END_OBJECT:
                        reader.endObject();
                        break;
                    case NAME:
                        String name = reader.nextName();

                        if ("msg".equals(name) || "message".equals(name)) {
                            error = reader.nextString();
                        }
                        break;
                    default: reader.skipValue();
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();

        } finally {
            reader.close();
        }

        return error;
    }
}
