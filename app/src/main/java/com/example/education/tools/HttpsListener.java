package com.example.education.tools;

public interface HttpsListener {
    void success(final String response);
    void failed(Exception exception);
}
