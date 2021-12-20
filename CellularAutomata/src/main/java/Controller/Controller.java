package Controller;

import Strategy.Rule;
import Model.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Controller implements KeyListener { // Keylistener used for pause and randomize/restart button

    // reference to 2D Matirx of cells
    private Cell[][] cells;

    // size in terms of cells
    private final int width = run.width/ Cell.size;
    private final int height = run.height/ Cell.size;

    // random from util-library
    private Random random;

    // starts with generation 1
    private int currGeneration = 1;

    // used to implement pause button
    private boolean pause = false;

    // Constructor
    public Controller(){
        random = new Random();
        this.cells = new Cell[width][height];

        // initialising Matrix with random boolean values
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(i, j);
                cells[i][j].setAlive(random.nextBoolean());
            }
        }
    }

    // three helper methods for WillBeAlive

    // checks if index is out of bounds (for example when accessing neighbours of corner cell)
    public boolean safe(int i, int j){
       return i >= 0 && i < width && j >= 0 && j < height;
    }

    // checks if cell at given position is alive in current gen.
    public boolean alive(int i,int j){
       return cells[i][j].isAlive();
    }

    // checks if two cells are the same (important to not count cell as neighbour of itself)
    public boolean sameCell(int i, int j, Cell cell){
       return i == cell.getX() && j == cell.getY();
    }


    // checks if cell will be alive in next gen. according to the rule/strategy/automaton
    public boolean WillBeAlive(Cell cell, Rule rule){
        int count = 0;

        for (int i = cell.getX() - 1; i <= cell.getX() + 1; i++){
            for (int j = cell.getY() - 1; j <= cell.getY() + 1; j++){
                if(!sameCell(i, j, cell) && safe(i,j) && alive(i, j)){
                    count++;
                }
            }
        }

        // once living neighbours are counted, this info is sent to the encapsulated automaton algorithm
        return rule.cellAliveNextGen(count, cell.isAlive());
    }


    // entering next generation
    public void update(Rule rule){
        if (!pause){ // pause button will prevent updates from happening
            return;
        }

        // updating gen. count
        currGeneration++;

        // updating next states for each cell
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (WillBeAlive(cells[i][j], rule)) {
                    cells[i][j].setWill_be_alive(true);
                } else {
                    cells[i][j].setWill_be_alive(false);
                }
            }
        }

        // updating Matrix
        // have to be handled seperatly, otherwise updates might override other updates
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j].update();
            }
        }
    }


    // drawing each cell on screen
    public void draw(Graphics graphics){

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                this.cells[i][j].draw(graphics);
            }
        }

        // GUI-setup for generation counter in top right corner
        graphics.setColor(Color.MAGENTA);
        graphics.setFont((new Font("JerseyM54", Font.BOLD, 40)));
        graphics.drawString("" + this.currGeneration, 15, 15 + graphics.getFont().getSize());
    }

    // getter for an individual cell, does not return reference (only primitvie boolean) -> safe
    public boolean getCell(int i, int j){
        return this.cells[i][j].isAlive();
    }

    // getter for height and width
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }

    @Override
    public void keyPressed(KeyEvent e){

        // pressing R: restart with a new random level
        if(e.getKeyCode() == KeyEvent.VK_R) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    cells[i][j].setAlive(random.nextBoolean());
                }
            }
            // starts with first generation of new level
            this.currGeneration = 1;
        }
        // pressing P: pausing the game
        if(e.getKeyCode() == KeyEvent.VK_P){
            this.pause = !this.pause;
        }
    }


    // forced to override by implementing Keylistener, not needed
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
