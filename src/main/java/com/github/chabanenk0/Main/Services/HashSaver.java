package com.github.chabanenk0.Main.Services;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Class for saving the calculated hashes
 */
public class HashSaver
{

    protected String outputFileName;
    protected BufferedWriter outputFileBufferedWriter;

    public HashSaver(String outputFileName)
    {
        this.outputFileName = outputFileName;
    }

    protected BufferedWriter getOutputFileBufferedWriter() throws FileNotFoundException
    {
        if (null != this.outputFileBufferedWriter) {
            return this.outputFileBufferedWriter;
        }
        OutputStream fileOutputStream = new FileOutputStream(this.outputFileName);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, Charset.forName("UTF-8"));
        this.outputFileBufferedWriter = new BufferedWriter(outputStreamWriter);

        return this.outputFileBufferedWriter;
    }

    public synchronized void save(String sourceUrl, String hash) throws IOException
    {
        this.getOutputFileBufferedWriter().write(sourceUrl + " : " + hash + "\n");
        this.getOutputFileBufferedWriter().flush();
    }

    public void closeOutputFile()
    {
        try {
            this.outputFileBufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
