import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;

public class LifeModel implements ActionListener {

    /*
     * This is the Model component.
     */
    private static int SIZE = 60;
    LifeView myView;
    Timer timer;
    private LifeCell[][] grid;
    private boolean[][] initial;
    private String fileName;

    private static final int[][] NEIGHBORS = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 },
            { 1, 0 }, { 1, 1 } };

    /**
     * Construct a new model using a particular file
     */
    public LifeModel(LifeView view, String fileName) throws IOException {
        int r, c;
        this.fileName = fileName;
        grid = new LifeCell[SIZE][SIZE];
        initial = new boolean[SIZE][SIZE];
        for (r = 0; r < SIZE; r++)
            for (c = 0; c < SIZE; c++) {
                grid[r][c] = new LifeCell();
                initial[r][c] = false;
            }

        if (fileName == null) // use random population
        {
            for (r = 0; r < SIZE; r++) {
                for (c = 0; c < SIZE; c++) {
                    if (Math.random() > 0.85) { // 15% chance of a cell starting alive
                        grid[r][c].setAliveNow(true);
                    }
                }
            }
        } else {
            Scanner input = new Scanner(new File(fileName));
            int numInitialCells = input.nextInt();
            for (int count = 0; count < numInitialCells; count++) {
                r = input.nextInt();
                c = input.nextInt();
                grid[r][c].setAliveNow(true);
                initial[r][c] = true;
            }
            input.close();
        }

        myView = view;
        myView.updateView(grid);
    }

    /**
     * Constructor a randomized model
     */
    public LifeModel(LifeView view) throws IOException {
        this(view, null);
    }

    /**
     * pause the simulation (the pause button in the GUI
     */
    public void pause() {
        timer.stop();
    }

    /**
     * resume the simulation (the pause button in the GUI
     */
    public void resume() {
        timer.restart();
    }

    /**
     * run the simulation (the pause button in the GUI
     */
    public void run() {
        timer = new Timer(50, this);
        timer.setCoalesce(true);
        timer.start();
    }

    /**
     * reset the simulation
     */
    public void reset() {
        if (fileName != null) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    grid[i][j].setAliveNext(initial[i][j]);
                }
            }
        } else {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (Math.random() > 0.85) { // 15% chance of a cell starting alive
                        grid[i][j].setAliveNext(true);
                    }
                }
            }
        }

        updateCells();
        myView.updateView(grid);
    }

    /**
     * called each time timer fires
     */
    public void actionPerformed(ActionEvent e) {
        oneGeneration();
        myView.updateView(grid);
    }

    /**
     * main logic method for updating the state of the grid / simulation
     */
    private void oneGeneration() {
        /*
         * student code here
         */
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int neighbors = countNeighbors(grid, i, j);

                if (grid[i][j].isAliveNow()) {
                    if (neighbors != 2 && neighbors != 3) {
                        grid[i][j].setAliveNext(false);
                        continue;
                    }

                    grid[i][j].setAliveNext(true);

                } else if (neighbors == 3) {
                    grid[i][j].setAliveNext(true);
                }
            }
        }
        updateCells();
    }

    /**
     * Counts the number of neighbors of a LifeCell
     *
     * @param g the LifeCell we want the number of neighbors of
     * @param x the x index of LifeCell g
     * @param y the y index of LifeCell g
     * @return the number of neighbors of LifeCell g
     */
    private int countNeighbors(LifeCell[][] g, int x, int y) {
        int count = 0;
        for (int[] offset : NEIGHBORS) {
            if (inBounds(x + offset[1], y + offset[0])) {
                if (g[x + offset[1]][y + offset[0]].isAliveNow()) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * returns if an (x, y) is inbounds in the array
     *
     * @param x the x value of the array
     * @param y the y value of the array
     * @return a boolean if (x, y) is inbounds
     */
    private boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < SIZE && y < SIZE;
    }

    /**
     * Updates the cells from Now to Next iteration
     */

    private void updateCells() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].setAliveNow(grid[i][j].isAliveNext());
            }
        }
    }
}
