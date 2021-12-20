package Model;

import java.awt.*;

public class Cell {

    // position in x-y-plane relative to other Cells
    private final int x_pos;
    private final int y_pos;

    // tracks if Cell is alive in current generation, and will be alive in next gen.
    private boolean is_alive;
    private boolean will_be_alive;

    // static field for the size of each cell in pixels (class attribute because all cells have same size)
    public static int size = 10;

    // Constructor initialises position
    public Cell(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    public void draw(Graphics graphics){
        // living cells are black, dead ones are white
        if (this.is_alive){
            graphics.setColor(Color.BLACK);
        } else {
            graphics.setColor(Color.WHITE);
        }

        // need pixel position: scaling cell position by cell size in pixels
        graphics.drawRect(x_pos * size, y_pos * size, size, size);
        graphics.fillRect(x_pos * size, y_pos * size , size , size );
    }

    // updates when moving into next gen.
    public void update(){
        this.is_alive = this.will_be_alive;
    }

    // getters and setters
    public boolean isAlive(){
        return this.is_alive;
    }

    public void setAlive(boolean is_alive){
        this.is_alive = is_alive;
    }


    public void setWill_be_alive(boolean will_be_alive){
        this.will_be_alive = will_be_alive;
    }


    public int getX(){
        return this.x_pos;
    }
    public int getY(){
        return this.y_pos;
    }
}
