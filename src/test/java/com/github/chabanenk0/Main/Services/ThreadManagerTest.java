package com.github.chabanenk0.Main.Services;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ThreadManagerTest
{
    @Test
    public void ThreadManagerTest()
    {
        ThreadManager threadManager;
        try {
            threadManager = this.createThreadManager();
            assertNotNull(threadManager);
        } catch (Exception exception) {
            assertTrue(false);
        }
    }

    private ThreadManager createThreadManager()
    {
        return new ThreadManager(10, "inputfile.txt", "outfile.txt");
    }

    @Test
    public void runAllTest()
    {
        DownloaderService fakeDownloaderService = mock(DownloaderService.class);
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                System.out.println("DownloaderService::run called..." );
                return null;
            }
        }).when(fakeDownloaderService).run();

        ThreadManager threadManager = mock(ThreadManager.class);
        when(threadManager.createDownloaderService()).thenReturn(fakeDownloaderService);
        doCallRealMethod().when(threadManager).runAll();

        threadManager.runAll();
        verify(fakeDownloaderService, times(10)).run();
    }
}
