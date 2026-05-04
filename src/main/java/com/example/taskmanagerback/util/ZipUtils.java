package com.example.taskmanagerback.util;

import com.example.taskmanagerback.adapter.out.repository.minio.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {
    public static List<File> unzip(MultipartFile zipFile) throws IOException {
        List<File> extractedFiles = new ArrayList<>();

        try (InputStream fileInputStream = zipFile.getInputStream();
             ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)) {

            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    byte[] fileBytes = zipInputStream.readAllBytes();

                    String originalName = entry.getName();

                    String mimeType = getMimeType(originalName);

                    extractedFiles.add(new File(originalName, fileBytes, mimeType));
                }
                zipInputStream.closeEntry();
            }
        }
        return extractedFiles;
    }

    private static String getMimeType(String fileName) throws IOException {
        return Files.probeContentType(Paths.get(fileName));
    }
}
