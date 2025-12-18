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

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Player[] getPlayers() {
        return players;
    }

    public ActionData getAct() {
        return act;
    }
    
    public ActionController() {
        setPlayers(new Player[PLAYERS_QTY]);
        
        for (int currPlayerIx = 0; currPlayerIx < PLAYERS_QTY; currPlayerIx++) {
            getPlayers()[currPlayerIx] = new Player(currPlayerIx, true);
        }
        
        setAct(new ActionData());
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

        LogEntry entryStart = new LogEntry("Start of log", "Log writing");

        log.add(entryStart);
        
        ActionController ac = new ActionController();
        
        boolean turnAllowed = true;

        int currPlayerIx = 0;
        
        Game game = new Game(1);

        while (!game.isGameOver(ac)) {
            ac.getAct().setPlayer(ac.players[currPlayerIx]);

            System.out.printf("Turn no. %d\n", game.getTurnNum());   // Debug 
            
            // ?? Имеет ли смысл делать такой "оборот" или нужно как-то 
            // переделать
            ac.getAct().getPlayer().askAction(ac);
            
            if (game.isTurnPossible(ac.getAct())) {
                game.makeTurn(ac.getAct());
                
                game.nextTurn();

                LogEntry entry = new LogEntry(ac.getAct().getDir().toString(),
                                            "Player turn");
                
                log.add(entry);
                
                currPlayerIx = ac.nextPlayerIx(currPlayerIx);
            }
            else {
                System.out.printf("Turn impossible. Player no. %d\n",
                        currPlayerIx);
            }
        }
    }
}