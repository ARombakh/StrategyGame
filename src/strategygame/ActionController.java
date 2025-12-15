/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

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
    
    public static void main(String[] args) {
        ActionController ac = new ActionController();

        Player player = null;
        int currPlayerIx = 0;
        while (true) {
            player = ac.players[currPlayerIx];
            
            ac.act = player.askAction();
            
            
            currPlayerIx = ac.nextPlayerIx(currPlayerIx);
        }
    }
}
