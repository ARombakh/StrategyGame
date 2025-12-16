/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import java.io.IOException;
import java.nio.file.*;

/**
 *
 * @author artyom
 */
public class ActionController {
    public static final int PLAYERS_QTY = 4;
    
    public enum DirectionType {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    
    private ActionData act;
    private Player[] players;

    public void setAct(ActionData act) {
        this.act = act;
    }

    public ActionData getAct() {
        return act;
    }
    
    public ActionController() {
        this.players = new Player[PLAYERS_QTY];
        
        for (int currPlayerIx = 0; currPlayerIx < PLAYERS_QTY; currPlayerIx++) {
            players[currPlayerIx] = new Player(currPlayerIx);
        }
    }
    
    private int nextPlayerIx(int playerIx) {
        if (playerIx == PLAYERS_QTY - 1) {
            return 0;
        }
        else {
            return playerIx + 1;
        }
    }

    public static void main(String[] args) throws Exception {
        String path = "/home/artyom/Documents/Java/StrategyGameLog.txt";
        Log log = new Log(path);
        
        Path testFilePath = Paths.get(log.getFileName());
        boolean pathExists = Files.exists(testFilePath);

        if (!pathExists) {
            Files.createFile(testFilePath);
        }

        LogEntry entryStart = new LogEntry("Start of log", "Log writing");

        log.add(entryStart);
        
        ActionController ac = new ActionController();
        
        boolean turnAllowed = true;

        Player player = null;
        int currPlayerIx = 0;
        while (true) {
            player = ac.players[currPlayerIx];
            
            ac.setAct(player.askAction());
            
            if (turnAllowed) {
                // make turn
                LogEntry entry = new LogEntry(ac.getAct().getDir().toString(),
                                            "Player turn");
                
                log.add(entry);
                
                currPlayerIx = ac.nextPlayerIx(currPlayerIx);
            }
        }
    }
}