package com.project.gymi;

public class WorkoutHelperClass {

    String abs_sets, abs_returns, backhand_sets,
            backhand_returns, chest_sets,chest_returns,hand_sets,hand_returns;

    public WorkoutHelperClass(){
    }


    public WorkoutHelperClass(String abs_sets, String abs_returns,String backhand_sets,
                              String backhand_returns, String chest_sets,String chest_returns,
                              String hand_sets,String hand_returns){
        this.abs_sets = abs_sets;
        this.abs_returns = abs_returns;
        this.backhand_sets = backhand_sets;
        this.backhand_returns = backhand_returns;
        this.chest_sets = chest_sets;
        this.chest_returns = chest_returns;
        this.hand_sets = hand_sets;
        this.hand_returns = hand_returns;
    }

    public String getAbs_sets() {
        return abs_sets;
    }

    public void setAbs_sets(String abs_sets) {
        this.abs_sets = abs_sets;
    }

    public String getAbs_returns() {
        return abs_returns;
    }

    public void setAbs_returns(String abs_returns) {
        this.abs_returns = abs_returns;
    }

    public String getBackhand_sets() {
        return backhand_sets;
    }

    public void setBackhand_sets(String backhand_sets) {
        this.backhand_sets = backhand_sets;
    }

    public String getBackhand_returns() {
        return backhand_returns;
    }

    public void setBackhand_returns(String backhand_returns) {
        this.backhand_returns = backhand_returns;
    }

    public String getChest_sets() {
        return chest_sets;
    }

    public void setChest_sets(String chest_sets) {
        this.chest_sets = chest_sets;
    }

    public String getChest_returns() {
        return chest_returns;
    }

    public void setChest_returns(String chest_returns) {
        this.chest_returns = chest_returns;
    }

    public String getHand_sets() {
        return hand_sets;
    }

    public void setHand_sets(String hand_sets) {
        this.hand_sets = hand_sets;
    }

    public String getHand_returns() {
        return hand_returns;
    }

    public void setHand_returns(String hand_returns) {
        this.hand_returns = hand_returns;
    }
}
