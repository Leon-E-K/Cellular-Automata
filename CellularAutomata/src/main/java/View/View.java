package View;

import Controller.*;
import Model.*;
import Strategy.Rule;
import Exceptions.*;

import javax.swing.*;
import java.awt.*;


public class View extends JFrame { // class represents the main JFrame

    // GUI-component
    private Screen screen;

    // reference to controller (implements game logic)
    private Controller controller;

    // smallest possible delay between updating to a new generation in seconds (0.05 as default value)
    private double DELAY =  0.05;
    // will take time to make sure to never update before at least the delay has passed
    private double timeSinceLastUpdate;

    // Strategy pattern: selecting a rule to change between Cellular Automaton used
    public static Rule selectedRule;

    // Constructor creating the View
    public View() {

        // GUI-method: gets rid of standard window border
        setUndecorated(true);

        // First: User selects which Cellular Automaton (selection out of 3 common ones)
        String[] selectionValues = {"Game of life", "Highlife", "Seeds"};
        String initialSelection = "Game of Life";

        // GUI-component that stores decision as a String Value
        // this is safe: we don't let the user enter a String, they can choose between pre-defined ones
        String selectedRule = (String) JOptionPane.showInputDialog(null, "Select cellular atomaton",
                "Cellular Automata", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);

        // switch-case to implement strategy based on user's selection
        switch (selectedRule) {
            case "Game of life":
                View.selectedRule = Rule.gameOfLife();
                break;
            case "Highlife":
                View.selectedRule = Rule.highLife();
                break;
            case "Seeds":
                View.selectedRule = Rule.seeds();
                break;
            default:
                System.exit(0); // closes VM in case something went wrong (should never happen)
        }

        // ensuring correct user input regarding Cell-size in pixels
        boolean correctInput = false;
        String message = "Set the cell size! (1-20)";

        while (!correctInput) {
            String inputCellSize = JOptionPane.showInputDialog(this, message);

            try {
                Cell.size = Integer.parseInt(inputCellSize);
                if (Cell.size > 20 || Cell.size < 1) {
                    throw new UnreasonableValueException();
                }
                // statement only reachable if no error occured
                correctInput = true;
            } catch (UnreasonableValueException exc) {
                message = "Your last input was not within the bounds of 1-20, try again!";
            } catch (Exception exc) {
                message = "Something caused an issue, did you insert any extra spaces? Did you input an integer?";
            }
        }


        // ensuring correct user input regarding minimum delay between generations in ms
        correctInput = false;
        message = "Set the delay in ms between generations! (0.05 - 1)";

        while (!correctInput) {
            String inputDelay = JOptionPane.showInputDialog(this, message);

            try {
                DELAY = Double.parseDouble(inputDelay);
                if (DELAY > 1 || DELAY < 0.05) {
                    throw new UnreasonableValueException();
                }
                // statement only reachable if no error occured
                correctInput = true;
            } catch (UnreasonableValueException exc) {
                message = "Your last input was not within the bounds of 0.05 - 1, try again!";
            } catch (Exception exc) {
                message = "Something caused an issue, did you insert any extra spaces?";
            }
        }

        // GUI-method: sets size to maximum size as defined by screen
        setExtendedState(MAXIMIZED_BOTH);

    }

    public void createScreen(){
        controller = new Controller();

        // observer-pattern: using key-presses to allow
        addKeyListener(controller);

        // initialises screen with bounds as defined above
        this.screen = new Screen();
        this.screen.setBounds(0, 0, run.width, run.height);

        // adding screen onto this View (a JFrame)
        this.add(this.screen);
    }

    // refreshing, used between generations
    public void repaint(){
        this.screen.repaint();
    }

    // making sure that at least delay has passed
    public void update(double timeSinceLastUpdate){
        this.timeSinceLastUpdate += timeSinceLastUpdate;

        if (this.timeSinceLastUpdate > DELAY) {

            controller.update(selectedRule);
            this.timeSinceLastUpdate = 0;
        }
    }

    // screen is a JLabel
    private class Screen extends JLabel{
        @Override
        public void paintComponent(Graphics graphics){
            super.paintComponent(graphics);
            controller.draw(graphics);
        }
    }

}
