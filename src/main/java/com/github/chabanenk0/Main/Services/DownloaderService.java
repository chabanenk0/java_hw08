package com.github.chabanenk0.Main.Services;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dmitry on 26.01.17.
 */
public class DownloaderService implements Runnable
{
    protected UrlProvider urlProvider;

    protected HashSaver hashSaver;

    public DownloaderService(UrlProvider urlProvider, HashSaver hashSaver)
    {
        this.urlProvider = urlProvider;
        this.hashSaver = hashSaver;
    }

    public void run()
    {
        System.out.println("Started...");
        String sourceUrl = "";
        try {
            sourceUrl = this.urlProvider.readFilenameFromInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contents = this.getUrlContents(sourceUrl);
        String hash = this.calculateHash(contents);
        System.out.println("Hash is:" + hash);
        this.saveHash(sourceUrl, hash);
        System.out.println("Finished. After save...");
    }

    protected String getUrlContents(String sourceUrl)
    {
        StringBuilder response = new StringBuilder();
        try {
            URL website = new URL(sourceUrl);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return response.toString();
    }

    protected String calculateHash(String content)
    {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
            byte[] data = content.getBytes();
            m.update(data,0,data.length);
            BigInteger i = new BigInteger(1,m.digest());
            return String.format("%1$032X", i);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    protected synchronized void saveHash(String sourceUrl, String hash)
    {
        try {
            this.hashSaver.save(sourceUrl, hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
