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
    StrategyGame.Field.Player playerTurn;

    public Game() {
        this.field = initField();
        this.legend = field.new Legend();
        this.screen = field.new Screen(this.field);
        this.playerTurn = field.Player[0];

        field.updateScreen(this.legend, this.screen);
    }

    public StrategyGame.Field initField() {
        StrategyGame.Field.GameCell Cell;

        // Можно ли наполнить поле без создания нового объекта "карта"??
        Map map = new Map();
        StrategyGame.Field field = map.Plateau();

        for (int i = 0; i < PLAYERS_COUNT; i++) {
            Cell = field.cells[X_START_PL[i]][Y_START_PL[i]];
            field.Player[i] = field.new Player(PLAYER_SYMBOL[i]);
            Cell.unit = Cell.new Unit(field.Player[i]);
            field.Player[i].unit = Cell.unit;
            Cell.unit.player = field.Player[i];
        }

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

    public boolean turn(int i) {
        boolean isSuccess = false;
        String move;
        String yes_no;
        boolean action;
        StrategyGame.Direction dir = null;
        StrategyGame.Field.GameCell.Unit unitToGo = null;
        Scanner scanner = new Scanner(System.in);

        unitToGo = field.Player[i].unit;

        System.out.printf("Turn of Player%c.\n", field.Player[i].symbol);
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

        return isSuccess;
    }
}
