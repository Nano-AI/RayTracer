package com.window;

import com.helper.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ImageViewer {
    private String file;
    JFrame frame;
    private BufferedImage image;
    private Vector2 size = new Vector2(0, 0);
    public ImageViewer(int width, int height) {
        setImageSize(width, height);
    }

    public ImageViewer() {

    }

    public void readFile(String file) throws FileNotFoundException {
        this.file = file;
        Scanner scanner = new Scanner(new File(file));

        String format = scanner.nextLine();
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        int range = scanner.nextInt();

        setImageSize(width, height);


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int r = scanner.nextInt();
                int g = scanner.nextInt();
                int b = scanner.nextInt();
                // System.out.println(j + " " + i);
                Color color = new Color(r, g, b);
                setRGB(j, i, color);
            }
        }
    }

    public void setImageSize(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.size.set(width, height);
    }

    public void setRGB(int x, int y, Color c) {
        image.setRGB(x, y, c.getRGB());
    }

    public void display() {
        frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));

        frame.pack();
        frame.setVisible(true);
    }

    public Vector2 getSize() {
        return this.size;
    }
}
