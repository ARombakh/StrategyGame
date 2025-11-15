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
    Scanner scanner = new Scanner(System.in);

    public Game() {
        this.field = initField();
        this.legend = field.new Legend();
        this.screen = field.new Screen(this.field);

        field.updateScreen(this.legend, this.screen);
    }

    public StrategyGame.Field initField() {
        StrategyGame.Field.GameCell Cell;

        // Можно ли наполнить поле без создания нового объекта "карта"??
        Maps map = new Maps();
        StrategyGame.Field plateau = map.Plateau();

        for (int i = 0; i < PLAYERS_COUNT; i++) {
            Cell = plateau.cells[X_START_PL[i]][Y_START_PL[i]];
            plateau.Player[i] = plateau.new Player(PLAYER_SYMBOL[i]);
            Cell.unit = plateau.new Unit(plateau.Player[i], Cell);
            plateau.Player[i].unit = Cell.unit;
            Cell.unit.player = plateau.Player[i];
        }

        return plateau;
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

    public boolean turn(int i) {
        boolean isSuccess;
        String move;
        String yes_no;
        boolean action;
        StrategyGame.Direction dir = null;
        StrategyGame.Field.Unit unitToGo;

        unitToGo = field.Player[i].unit;

        System.out.printf("Turn of Player %c.\n", field.Player[i].symbol);
        
        do {            
            System.out.print("Will it be action [y/n]? ");
            yes_no = this.scanner.next();
            if (!yes_no.equals("y") && !yes_no.equals("n")) {
                System.out.println("Unrecognized input. Repeat the input.");
            }
        } while (!yes_no.equals("y") && !yes_no.equals("n"));

        action = yes_no.toLowerCase().equals("y");

        if (action) {
            System.out.println("Action chosen. ");
        }
        else {
            System.out.println("Move chosen. ");
        }

        System.out.print("Enter direction: ");
        move = this.scanner.next();

        while ((dir = getDir(move)) == null) {
            System.out.print(
                    "Incorrect direction. Enter another direction: "
            );
            move = this.scanner.next();
        }

        if (action) {
            isSuccess = unitToGo.action(dir);
        }
        else {
            isSuccess = unitToGo.move(dir);
        }

        return isSuccess;
    }
}
