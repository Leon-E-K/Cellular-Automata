package Controller;

import View.*;

public class run {

    // dimensions in pixels (static class member, because we don't want an instance of this class)
    public static int width;
    public static int height;

    // main-method, execution starts here
    public static void main(String[] args) {

        // initialising GUI
        View frame = new View();

        // adding GUI-properties
        frame.setVisible(true); // everything belonging to our frame is drawn
        frame.setResizable(false); // static, non-resizable

        // these values fit well on my laptop :)
        width = 1750;
        height = 1100;

        // initialises Screen, which in turn initialises the controller etc.
        frame.createScreen();

        // get current time (Unix UTC) as of starting
        long lastFrameTime = System.currentTimeMillis();

        // game-loop
        while(true) {

            // get current time at the beginning of an iteration
            long thisFrameTime = System.currentTimeMillis();

            // calculate difference (i.e. time since current gen. begun
            double timeSinceLastUpdate = (thisFrameTime - lastFrameTime)  / 1000.0;

            // updates for next iteration
            lastFrameTime = thisFrameTime;

            // only jump to next gen. if waited at least delay-amount of time

            // updates internally
            frame.update(timeSinceLastUpdate);

            // updates externally: has to refresh to display next generation once it has done so internally
            frame.repaint();

            // in any case, 10 ms delay
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
