/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

/**
 *
 * @author artyom
 */
public class Log {
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
    
    public Log(String fileName) {
        setFileName(fileName);
    }
    
    public String add(LogEntry entry) {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.toString() + "\t" + entry.toText() + "\n";
    }
    
    public static void main(String[] args) throws Exception {
        
        String path = "/home/artyom/Documents/Java/StrategyGameLog.txt";
        Log log = new Log(path);
        
        Path testFilePath = Paths.get(log.getFileName());
        boolean pathExists = Files.exists(testFilePath);
        
        if (!pathExists) {
            throw new Exception("File " + testFilePath.toString() + " doesn't exist");
        }
        
        LogEntry entry = new LogEntry("Start of log", "Log writing");
        
        try {
            Files.writeString(testFilePath, log.add(entry),
                                StandardOpenOption.APPEND);
            
            String content = Files.readString(testFilePath);
            
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        System.out.printf(log.add(entry) + "\n");
    }
}