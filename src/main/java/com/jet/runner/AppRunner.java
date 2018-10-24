package com.jet.runner;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.jet.config.properties.S3Properties;
import com.jet.runner.domain.Holding;
import com.jet.runner.domain.HoldingKey;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

import static com.jet.runner.StreamUtils.applyFileReader;
import static com.jet.runner.StreamUtils.applyFileWriter;

@Component
@Slf4j
public class AppRunner implements CommandLineRunner {

    private static final String PACKAGE_IDS = "packageIds";
    private static final String RESOURCE_TYPE_IDS = "resourceTypeIds";
    private static final String HOLDINGS = "holdings";
    private static final Gson GSON = new GsonBuilder().create();

    private final TransferManager transferManager;
    private final S3Properties s3Properties;

    @Autowired
    public AppRunner(TransferManager transferManager, S3Properties s3Properties) {
        this.transferManager = transferManager;
        this.s3Properties = s3Properties;
    }

    @Override
    public void run(String... args) {
        log.info("Application started");
        File inputFile = downloadFile(false);
        analyzeDuplications(inputFile);
        File outputFile = new File(".", s3Properties.getOutputFileName());
        processFile(inputFile, outputFile);
        log.info("Application finished");
    }

    @SneakyThrows({IOException.class, InterruptedException.class})
    private File downloadFile(boolean isDownloaded) {
        if (isDownloaded) {
            return new File(".", s3Properties.getFileName());
        }
        File destinationFile = File.createTempFile("tmp", "file");
        Download transfer = transferManager.download(new GetObjectRequest(s3Properties.getBucketName(),
                String.format("%s/%s", s3Properties.getPath(), s3Properties.getFileName())), destinationFile);
        log.info("Download started");
        transfer.waitForCompletion();
        log.info("File downloaded");
        return destinationFile;
    }

    private void analyzeDuplications(File inputFile) {
        applyFileReader(inputFile, this::checkDuplications);
    }

    @SneakyThrows(IOException.class)
    private void checkDuplications(JsonReader reader) {
        Set<HoldingKey> set = new HashSet<>();
        reader.beginObject();
        scrollToHoldings(reader);
        reader.beginArray();
        while (reader.hasNext()) {
            Holding holding = GSON.fromJson(reader, Holding.class);
            HoldingKey key = HoldingKey.of(holding.getTitleId(), holding.getPackageId());
            if (set.contains(key)) {
                log.warn("Duplication found {}", key);
            } else {
                set.add(key);
            }
        }
        reader.endArray();
        reader.endObject();
    }

    private void processFile(File inputFile, File outputFile) {
        applyFileReader(inputFile, reader1 ->
                applyFileReader(inputFile, reader2 ->
                        applyFileWriter(outputFile, writer -> merge(reader1, reader2, writer))
                )
        );
    }

    @SneakyThrows(IOException.class)
    private void merge(JsonReader reader1, JsonReader reader2, OutputStreamWriter writer) {
        writer.write("{");
        reader1.beginObject();
        reader2.beginObject();
        while (reader1.hasNext()) {
            String name = reader1.nextName();
            log.info("Reading json name: {}", name);
            switch (name) {
                case PACKAGE_IDS:
                case RESOURCE_TYPE_IDS:
                    writer.write(String.format("\"%s\":\"%s\",", name, reader1.nextString()));
                    break;
                case HOLDINGS:
                    writer.write(String.format("\"%s\":[", name));
                    scrollToHoldings(reader2);
                    reader1.beginArray();
                    reader2.beginArray();
                    while (reader1.hasNext() && reader2.hasNext()) {
                        Holding holding1 = GSON.fromJson(reader1, Holding.class);
                        Holding holding2 = GSON.fromJson(reader2, Holding.class);
                        writer.write(GSON.toJson(holding1));
                        writer.write(",");
                        writer.write(GSON.toJson(holding2));
                        if (reader2.hasNext()) {
                            writer.write(",");
                        }
                    }
                    reader1.endArray();
                    reader2.endArray();
                    writer.write("]");
                    break;
                default:
                    reader1.skipValue();
            }
        }
        reader1.endObject();
        reader2.endObject();
        writer.write("}");
    }

    private void scrollToHoldings(JsonReader reader) throws IOException {
        while (!HOLDINGS.equals(reader.nextName())) {
            reader.skipValue();
        }
    }

}
