package com.github.michaeldsa.aside;

public class Main {
    public static void main(String[] args) {
//        UIScanner scannerUI = new UIScanner();
//        scannerUI.start();
        Config config = new Config();
        config.initialize();
        System.out.println("aside.home = " + config.getAsideHome());

    }
}