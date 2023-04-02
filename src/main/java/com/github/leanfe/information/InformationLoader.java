package com.github.leanfe.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;


public class InformationLoader {

    public static final class Converter {
        // Date-time helpers

        private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
                .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                .appendOptional(DateTimeFormatter.ISO_INSTANT)
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter()
                .withZone(ZoneOffset.UTC);

        private static OffsetDateTime parseDateTimeString(String str) {
            return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
        }

        private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ISO_TIME)
                .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
                .parseDefaulting(ChronoField.YEAR, 2020)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter()
                .withZone(ZoneOffset.UTC);

        private static OffsetTime parseTimeString(String str) {
            return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
        }

        // Serialize/deserialize helpers
        public static Information fromJsonString(String json) throws IOException {
            return getObjectReader().readValue(json);
        }

        private static ObjectReader reader;

        private static void instantiateMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            SimpleModule module = new SimpleModule();
            module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<>() {
                @Override
                public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    String value = jsonParser.getText();
                    return Converter.parseDateTimeString(value);
                }
            });
            mapper.registerModule(module);
            reader = mapper.readerFor(Information.class);
        }

        private static ObjectReader getObjectReader() {
            if (reader == null) instantiateMapper();
            return reader;
        }

    }

    @lombok.Data
    public static class Information {
        @lombok.Getter(onMethod_ = {@JsonProperty("fileName")})
        @lombok.Setter(onMethod_ = {@JsonProperty("fileName")})
        public String fileName;
        @lombok.Getter(onMethod_ = {@JsonProperty("jsonObject")})
        @lombok.Setter(onMethod_ = {@JsonProperty("jsonObject")})
        public JSONObject jsonObject;
    }

    @lombok.Data
    public static class JSONObject {
        @lombok.Getter(onMethod_ = {@JsonProperty("servername")})
        @lombok.Setter(onMethod_ = {@JsonProperty("servername")})
        public String servername;
        @lombok.Getter(onMethod_ = {@JsonProperty("version")})
        @lombok.Setter(onMethod_ = {@JsonProperty("version")})
        public String version;
        @lombok.Getter(onMethod_ = {@JsonProperty("max_online")})
        @lombok.Setter(onMethod_ = {@JsonProperty("max_online")})
        public long maxOnline;
        @lombok.Getter(onMethod_ = {@JsonProperty("server_tags")})
        @lombok.Setter(onMethod_ = {@JsonProperty("server_tags")})
        public String serverTags;
        @lombok.Getter(onMethod_ = {@JsonProperty("description")})
        @lombok.Setter(onMethod_ = {@JsonProperty("description")})
        public String description;
    }

}
