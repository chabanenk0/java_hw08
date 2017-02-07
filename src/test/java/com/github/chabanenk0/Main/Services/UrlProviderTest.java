package com.github.chabanenk0.Main.Services;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UrlProviderTest
{
    @Test
    public void UrlProviderTest()
    {
        UrlProvider urlProvider;
        try {
            urlProvider = this.createUrlProvider("input.txt");
            assertNotNull(urlProvider);
        } catch (Exception exception) {
            assertTrue(false);
        }
    }

    private UrlProvider createUrlProvider(String filename)
    {
        return new UrlProvider(filename);
    }

    @Test
    public void getInvalidFileBufferedReaderTest()
    {
        // no file "input.txt" exists
        UrlProvider urlProvider = this.createUrlProvider("input.txt");
        try {
            BufferedReader reader = urlProvider.getInputFileBufferedReader();
            assertEquals(null, reader);
        } catch (Exception exception) {
            assertTrue(true);
        }
    }

    @Test
    public void getValidInputFileBufferedReaderTest()
    {
        String filename = this.getClass().getClassLoader().getResource("input_urls.txt").getFile();
        UrlProvider urlProvider = this.createUrlProvider(filename);
        try {
            BufferedReader reader = urlProvider.getInputFileBufferedReader();
            assertNotNull(reader);
        } catch (Exception exception) {
            assertTrue(false);
        }
    }

    @Test
    public void readFilenameFromInputStreamTest() throws IOException {
        String fileUrl = "https://github.com/chabanenk0/java_hw07/archive/develop.zip";
        BufferedReader fakeBufferedReader = mock(BufferedReader.class);
        when(fakeBufferedReader.readLine()).thenReturn(fileUrl);

        UrlProvider urlProvider = mock(UrlProvider.class);
        when(urlProvider.getInputFileBufferedReader()).thenReturn(fakeBufferedReader);
        doCallRealMethod().when(urlProvider).readFilenameFromInputStream();

        for(int i=0; i< 10; i++) {
            assertEquals(fileUrl, urlProvider.readFilenameFromInputStream());
        }
    }
    @Test
    public void readRealFilenamesFromInputStreamTest() throws IOException {
        String filename = this.getClass().getClassLoader().getResource("input_urls.txt").getFile();
        String url1 = "https://github.com/chabanenk0/java_hw07/archive/develop.zip";
        String url2 = "https://github.com/chabanenk0/java_hw07/archive/master.zip";
        String url3 = "https://github.com/chabanenk0/java_hw06/archive/develop.zip";

        UrlProvider urlProvider = this.createUrlProvider(filename);
        try {
            assertEquals(url1, urlProvider.readFilenameFromInputStream());
            assertEquals(url2, urlProvider.readFilenameFromInputStream());
            assertEquals(url3, urlProvider.readFilenameFromInputStream());
        } catch (Exception exception) {
            assertTrue(false);
        }
    }
}
