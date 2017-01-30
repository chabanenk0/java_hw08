package com.github.chabanenk0.Main.Services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadManager
{
    private static final int POOLS_NUMBER = 10;

    protected int poolSize;

    protected String inputFileName;

    protected String outputFileName;

    protected UrlProvider urlProvider;

    protected HashSaver hashSaver;

    public ThreadManager(int poolSize, String inputFileName, String outputFileName)
    {
        this.poolSize = poolSize;
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.urlProvider = new UrlProvider(this.inputFileName);
        this.hashSaver = new HashSaver(this.outputFileName);
    }

    protected DownloaderService createDownloaderService()
    {
        return new DownloaderService(this.urlProvider, this.hashSaver);
    }

    public void runAll()
    {
        if (this.poolSize <= 0) {
            this.poolSize = ThreadManager.POOLS_NUMBER;
        }
        ExecutorService executor = Executors.newFixedThreadPool(this.poolSize);

        for(int i = 0; i < poolSize; i++) {
            executor.submit(this.createDownloaderService());
        }
        System.out.println("After threads submitted");
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (null != this.hashSaver) {
            this.hashSaver.closeOutputFile();
        }
    }
}
