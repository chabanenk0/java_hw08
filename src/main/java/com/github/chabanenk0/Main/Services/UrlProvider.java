package com.github.chabanenk0.Main.Services;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by dmitry on 26.01.17.
 */
public class UrlProvider
{
    protected String inputFileName;

    protected BufferedReader inputFileBufferedReader;

    public UrlProvider(String inputFileName)
    {
        this.inputFileName = inputFileName;
    }

    protected BufferedReader getInputFileBufferedReader() throws FileNotFoundException
    {
        if (null != this.inputFileBufferedReader) {
            return this.inputFileBufferedReader;
        }
        InputStream fileInputStream = new FileInputStream(this.inputFileName);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
        this.inputFileBufferedReader = new BufferedReader(inputStreamReader);

        return this.inputFileBufferedReader;
    }

    public synchronized String readFilenameFromInputStream() throws IOException
    {
        return this.getInputFileBufferedReader().readLine();
    }
}
