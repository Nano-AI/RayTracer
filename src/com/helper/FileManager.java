package com.helper;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class FileManager extends PrintStream {
    private Vector2 imageSize;
    private String fileName;

    public Vector2 getImageSize() {
        return imageSize;
    }

    public FileManager(String output, int imageWidth, int imageHeight) throws FileNotFoundException {
        super(output);
        this.fileName = output;
        imageSize = new Vector2(imageWidth, imageHeight);
        // PNN header
        printf("P3\n%d %d\n255\n", (int) imageSize.getX(), (int) imageSize.getY());
    }

    public FileManager(String output, Vector2 size) throws FileNotFoundException {
        this(output, size.getX(), size.getY());
    }

    public int getImageWidth() {
        return imageSize.getX();
    }

    public int getImageHeight() {
        return imageSize.getY();
    }

    public String getFileName() {
        return this.fileName;
    }
}
