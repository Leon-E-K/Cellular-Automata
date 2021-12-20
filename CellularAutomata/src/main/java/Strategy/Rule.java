package Strategy;

import View.*;

public class Rule {

    // Strategy pattern: user selects from predefined rules, which alter the implementation of
    // the fundamental game-algorithms slightly, to change between different cellular automata
    private enum RULES{
        GAMEOFLIFE, HIGHLIFE, SEEDS
    }

    // private field to save decision on which rule object should have (Singleton pattern)
    private static RULES selectedRule;

    // private constructor: want to only use static factory methods
    private Rule(RULES selectedRule){
        this.selectedRule = selectedRule;
    }

    // --------Three static factory methods to get objects of required rule-----------
    // they follow the Singleton pattern: only one instance of rule can be on the heap at any given time
    // (reduces risk for errors where different methods might act on different rules)

    // Conway's game of life (probably the most prominent one)
    // (B3/S23 rule set)
    public static Rule gameOfLife(){
        if(selectedRule == null) {
            return new Rule(RULES.GAMEOFLIFE);
        } else {
            return View.selectedRule;
        }
    }

    // High Life (B36/S23 rule set), very similar to Conway's game of life
    public static Rule highLife(){
        if(selectedRule == null) {
            return new Rule(RULES.HIGHLIFE);
        } else {
            return View.selectedRule;
        }
    }

    // Seeds (B2/S rule set), very chaotic...
    public static Rule seeds(){
        if(selectedRule == null) {
            return new Rule(RULES.SEEDS);
        } else {
            return View.selectedRule;
        }
    }


    // changes implementation based on selected rule
    public boolean cellAliveNextGen(int count, boolean itselfAlive){
        if (this.selectedRule == RULES.GAMEOFLIFE){
            return count  == 3 || itselfAlive && count == 2;
        } else if (this.selectedRule == RULES.HIGHLIFE){
            return count == 3 || !itselfAlive &&  count == 6 || itselfAlive && count == 2;
        } else {
            return !itselfAlive && count == 2;
        }
    }
}
