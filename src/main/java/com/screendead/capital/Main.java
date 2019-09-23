package com.screendead.capital;

import java.net.URL;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        new Capital().run();
    }

    public static URL load(String resource) {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(resource));
    }
}
