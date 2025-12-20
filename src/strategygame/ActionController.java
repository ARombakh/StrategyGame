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
    private static Log log;

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }
    
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
    }
    
    private int nextPlayerIx(int playerIx) {
        if (playerIx == PLAYERS_QTY - 1) {
            return 0;
        }
        else {
            return playerIx + 1;
        }
    }
    
    private void gameLoop(Game game) {
        int currPlayerIx = 0;
        
        while (!game.isGameOver(getPlayers())) {
            Player currPlayer = getPlayers()[currPlayerIx];
            
            System.out.printf("Turn no. %d\n", game.getTurnNum());   // Debug 
            
            setAct(currPlayer.askAction());
            
            if (game.isTurnPossible(getAct())) {
                game.makeTurn(getAct());
                
                game.nextTurn();

                LogEntry entry = new LogEntry(getAct().getCommand(),
                                            "Player turn");

                try {
                    log.add(entry);
                } catch (Exception e) {
                    System.out.println("Can't add, " + e.getMessage());
                }
                
                currPlayerIx = nextPlayerIx(currPlayerIx);
            }
            else {
                System.out.printf("Turn impossible. Player no. %d\n",
                        currPlayerIx);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String path = "/home/artyom/Documents/Java/StrategyGameLog.txt";
        log.start(path);

        ActionController ac = new ActionController();   // Debug создание игроков
        // сделать здесь, указывая кол-во
        
        boolean turnAllowed = true;
        
        Game game = new Game();
        
        ac.gameLoop(game);
    }
}