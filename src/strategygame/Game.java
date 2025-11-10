/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import java.util.Scanner;
import static strategygame.StrategyGame.*;

/**
 *
 * @author artyom
 */
public class Game {
    StrategyGame.Field field;
    StrategyGame.Field.Legend legend;
    StrategyGame.Field.Screen screen;
    StrategyGame.Player playerTurn;

    public Game() {
        this.field = initField();
        this.legend = field.new Legend();
        this.screen = field.new Screen(this.field);

        field.updateScreen(this.legend, this.screen);
    }

    public StrategyGame.Field initField() {
        StrategyGame.Field.GameCell Cell;

        // Можно ли наполнить поле без создания нового объекта "карта"??
        Map map = new Map();
        StrategyGame.Field field = map.Plateau();

        Cell = field.cells[X_START_PL1][Y_START_PL1];
        Cell.unit = field.new Unit(StrategyGame.Player.PLAYER1, Cell);
        field.Player1 = Cell.unit;

        Cell = field.cells[X_START_PL2][Y_START_PL2];
        Cell.unit = field.new Unit(StrategyGame.Player.PLAYER2, Cell);
        field.Player2 = Cell.unit;
        playerTurn = StrategyGame.Player.PLAYER1;

        return field;
    }

    public StrategyGame.Direction getDir(String move) {
        StrategyGame.Direction dir = null;

        dir = switch (move.toLowerCase()) {
            case "up" -> StrategyGame.Direction.UP;
            case "down" -> StrategyGame.Direction.DOWN;
            case "left" -> StrategyGame.Direction.LEFT;
            case "right" -> StrategyGame.Direction.RIGHT;
            default -> null;
        };

        return dir;
    }

    public boolean turn() {
        boolean isSuccess = false;
        String move;
        String yes_no;
        boolean action;
        StrategyGame.Direction dir = null;
        StrategyGame.Field.Unit unitToGo = null;
        Scanner scanner = new Scanner(System.in);

        switch (playerTurn) {
            case PLAYER1 -> unitToGo = field.Player1;
            case PLAYER2 -> unitToGo = field.Player2;
            default ->
                throw new AssertionError("The player doesn't exist!");
        }

        System.out.printf("Turn of %s.\n", playerTurn);
        System.out.print("Will it be action [y/n]? ");
        yes_no = scanner.next();

        action = yes_no.toLowerCase().equals("y");

        if (action) {
            System.out.println("Action chosen. ");
        }
        else {
            System.out.println("Move chosen. ");
        }

        System.out.print("Enter direction: ");
        move = scanner.next();

        while ((dir = getDir(move)) == null) {
            System.out.print(
                    "Incorrect direction. Enter another direction: "
            );
            move = scanner.next();
        }

        if (action) {
            isSuccess = unitToGo.action(dir);
        }
        else {
            isSuccess = unitToGo.move(dir);
        }

        if (isSuccess) {
            if (unitToGo.player == StrategyGame.Player.PLAYER1) {
                playerTurn = StrategyGame.Player.PLAYER2;
            }
            else {
                playerTurn = StrategyGame.Player.PLAYER1;
            }                
        }

        return isSuccess;
    }
    
}
