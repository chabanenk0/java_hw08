package com.github.chabanenk0.Main;

import com.github.chabanenk0.Main.Services.ThreadManager;

/**
 * Created by dmitry on 25.01.17.
 */
public class Main {
    public static void main(String[] args) {
        ThreadManager threadManager = new ThreadManager(10, "/tmp/urls.txt", "/tmp/hashes.txt");
        threadManager.runAll();
    }
}
