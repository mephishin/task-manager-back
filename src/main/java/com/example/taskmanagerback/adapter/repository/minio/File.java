package com.example.taskmanagerback.adapter.repository.minio;

public record File(String name, byte[] bytes, String mimeType) {
}
