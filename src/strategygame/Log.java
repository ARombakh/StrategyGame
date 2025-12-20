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
    
    public void start(String fileName) {
        setFileName(fileName);

        Path filePath = Paths.get(getFileName());
        boolean pathExists = Files.exists(filePath);

        if (!pathExists) {
            try {
                Files.createFile(filePath);
            } catch (Exception e) {
                System.out.println("File cannot be created. " + e.getMessage());
            }
        }
        
        LogEntry startEntry = new LogEntry("Start of log", "Log writing");
        
        try {
            add(startEntry);
        } catch (Exception e) {
            System.out.println("Can't add, " + e.getMessage());
        }
    }
    
    public void add(LogEntry entry) throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        try {
            Files.writeString(Paths.get(getFileName()),
                                dateTime.toString() + "\t"
                                + entry.toText() + "\n",
                                StandardOpenOption.APPEND);    
        } catch (IOException e) {
            System.out.println("File cannot be written to. " + e.getMessage());
        }

    }
    
    /*public static void main(String[] args) throws Exception {
        
        String path = "/home/artyom/Documents/Java/StrategyGameLog.txt";
        Log log = new Log(path);
        
        Path testFilePath = Paths.get(log.getFileName());
        boolean pathExists = Files.exists(testFilePath);
        
        if (!pathExists) {
            Files.createFile(testFilePath);
        }
        
        LogEntry entry = new LogEntry("Start of log", "Log writing");

        log.add(entry);
    }*/
}