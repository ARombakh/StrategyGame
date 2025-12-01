/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

/**
 *
 * @author artyom
 */
public class GameLoop {/*
    private enum InquiryType {
        CHECK_STATE,
        TURN,
        ACT,
        WHAT_ACTION,
        DIRECTION_MOVE,
        DIRECTION_ACTION,
        DIRECTION_BUILDING,
        BUILDING_TYPE,
        MOVE,
        BUILD
    }
    
    private StrategyGame.Field field;
    private Action action;
    private GameUI ui;
    private StrategyGame.Unit unit;

    public StrategyGame.Unit getUnit() {
        return unit;
    }

    public void setUnit(StrategyGame.Unit unit) {
        this.unit = unit;
    }
    
    public StrategyGame.Field getField() {
        return field;
    }

    public void setField(StrategyGame.Field field) {
        this.field = field;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public GameUI getUi() {
        return ui;
    }
    
    public void setUi(GameUI ui) {
        this.ui = ui;
    }

    public GameLoop() {
        MapFactory theMap = new MapFactory();
        this.setField(theMap.createRiver());
        this.setAction(new Action());
        this.setUi(new GameUI());
    }
    
    public void loopIteration(int playerIndex) {
        InquiryType inquiry = InquiryType.WHAT_ACTION;
        
        action.setAction(null);
        action.setBuildingType(null);
        action.setDest(null);
        action.setDir(null);
        action.setSrc(null);
        action.setUnit(getField().player[playerIndex].getUnit());

        while (inquiry != InquiryType.CHECK_STATE) {
            switch (inquiry) {
                case WHAT_ACTION:
                    inquiry = chooseAction();
                    break;
                case DIRECTION_MOVE:
                    inquiry = chooseDirMov();
                    break;
                case MOVE:
                    inquiry = move();
                    break;
                case DIRECTION_ACTION:
                    inquiry = chooseDirAct();
                    break;
                case ACT:
                    inquiry = act();
                    break;
                case DIRECTION_BUILDING:
                    inquiry = chooseDirBuild();
                    break;
                case BUILDING_TYPE:
                    inquiry = chooseBuilding();
                    break;
                case BUILD:
                    inquiry = buildBuilding();
                    break;
            }
        }
    }
    
    public InquiryType chooseAction() {
        InquiryType inquiry = null;

//        StrategyGame.ActionType action;
        action.setAction(ui.getActionType());
        
        switch(action.getAction()) {
            case MOVE:
                inquiry = InquiryType.DIRECTION_MOVE;
                break;
            case INTERACT:
                inquiry = InquiryType.DIRECTION_ACTION;
                break;
            case BUILD:
                inquiry = InquiryType.DIRECTION_BUILDING;
                break;
        }
        
        return inquiry;
    }
    
    public InquiryType chooseDirMov() {
        InquiryType inquiry = null;
        
        action.setDir(ui.getDirection());
        
        
        // check dest cell
        
        if(impossible) {
            inquiry = InquiryType.WHAT_ACTION;
        }
        else {
            inquiry = InquiryType.MOVE;
        }
        
        return inquiry;
    }
    
    public InquiryType move() {
        // move
        
        return InquiryType.CHECK_STATE;
    }
    
    public InquiryType chooseDirAct() {
        InquiryType inquiry = null;
        // ask what move
        
        // check dest cell
        if(impossible) {
            inquiry = InquiryType.WHAT_ACTION;
        }
        else {
            inquiry = InquiryType.ACT;
        }
        
        return inquiry;
    }
    
    public InquiryType act() {
        // act
                        
        return InquiryType.CHECK_STATE;
    }
    
    public InquiryType chooseDirBuild() {
        InquiryType inquiry = null;
        // ask what move
        
        // check dest cell
        
        switch (//
                ) {
            case //occupied
                :
                inquiry = InquiryType.WHAT_ACTION;
                break;
            case null:
                inquiry = InquiryType.BUILDING_TYPE;
                break;
            case //building
                :
                inquiry = InquiryType.BUILD;
                break;
        }
        
        return inquiry;
    }
    
    public InquiryType chooseBuilding() {
        InquiryType inquiry = null;
        
        // ask building type
        
        // set building
        
        inquiry = InquiryType.BUILD;                
        
        return inquiry;
    }
    
    public InquiryType buildBuilding() {
        // build building
        
        return InquiryType.CHECK_STATE;
    }*/
}