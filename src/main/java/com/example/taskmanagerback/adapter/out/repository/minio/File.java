package com.example.taskmanagerback.adapter.out.repository.minio;

public record File(String name, byte[] bytes, String mimeType) {
}
