package com.jet.runner;

import com.google.gson.stream.JsonReader;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class StreamUtils {
    private StreamUtils() {
    }

    @SneakyThrows(IOException.class)
    public static void applyFileReader(File file, Consumer<JsonReader> consumer) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream)) {
                try (InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)) {
                    try (JsonReader jsonReader = new JsonReader(inputStreamReader)) {
                        consumer.accept(jsonReader);
                    }
                }
            }
        }
    }

    @SneakyThrows(IOException.class)
    public static void applyFileWriter(File file, Consumer<OutputStreamWriter> consumer) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream)) {
                try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(gzipOutputStream, StandardCharsets.UTF_8)) {
                    consumer.accept(outputStreamWriter);
                }
            }
        }
    }

}
