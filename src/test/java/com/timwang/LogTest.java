package com.timwang;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.timwang.log.Log;

public class LogTest {
    @Before
    public void clearLog() {
        Log.initialize();
    }
    @Test
    public void testAddAndGetLogs() throws IOException {
        for (int i = 0; i < 10; i++){
            Log.addLog("Test log " + i);
        }
        String[] logs = Log.getAllLogs();
        assertEquals(11, logs.length);
        for (int i = 0; i < 10; i++){
            String logI = logs[i];
            String[] logIArray = logI.split(" ");
            assertEquals(5, logIArray.length);
            assertEquals(8, logIArray[0].length());
            try {
                Integer.parseInt(logIArray[0]);
            } catch (NumberFormatException e) {
                assertEquals("logIArray[0] is not a number", true, false);
            }
            assertEquals(8, logIArray[1].length());
            try {
                Integer.parseInt(logIArray[1].split(":")[0]);
                Integer.parseInt(logIArray[1].split(":")[1]);
                Integer.parseInt(logIArray[1].split(":")[2]);
            } catch (NumberFormatException e) {
                assertEquals("logIArray[1] is not a time string", true, false);
            }
            assertEquals("Test", logIArray[2]);
            assertEquals("log", logIArray[3]);
            assertEquals(String.valueOf(9-i), logIArray[4]);
        }
        String[] log0Array = logs[10].split(" ");
        assertEquals(5, log0Array.length);
        assertEquals("session", log0Array[0]);
        assertEquals("start", log0Array[1]);
        assertEquals("at", log0Array[2]);
        
    }

    @Test
    public void testGetRecentLogs() throws IOException {
        for (int i = 0; i < 10; i++){
            Log.addLog("Test log " + i);
        }
        String[] logs = Log.getRecentLogs(2);
        assertEquals(2, logs.length);
        String[] logs2 = Log.getRecentLogs(5);
        assertEquals(5, logs2.length);
        String[] logs3 = Log.getRecentLogs(30);
        assertEquals(11, logs3.length);

    }
}