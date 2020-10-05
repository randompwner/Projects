import java.awt.*;
import java.util.Random;
import javax.swing.*;

/**
 * The view (graphical) component
 */
public class LifeView extends JPanel {
    private static final long serialVersionUID = 1L;
    private static int SIZE = 60;
    Color col = Color.BLUE;

    /**
     * store a reference to the current state of the grid
     */
    private LifeCell[][] grid;

    private boolean doRandomColor;

    public LifeView() {
        grid = new LifeCell[SIZE][SIZE];
    }

    /**
     * entry point from the model, requests grid be redisplayed
     */
    public void updateView(LifeCell[][] mg) {
        grid = mg;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int testWidth = getWidth() / (SIZE + 1);
        int testHeight = getHeight() / (SIZE + 1);

        // keep each life cell square
        int boxSize = Math.min(testHeight, testWidth);

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c] != null) {
                    if (grid[r][c].isAliveNow()) {
                        if (doRandomColor) {
                            Color randCol = generateRandomColor();
                            g.setColor(randCol);
                        } else {
                            g.setColor(Color.BLUE);
                        }
                    } else
                        g.setColor(new Color(235, 235, 255));

                    g.fillRect((c + 1) * boxSize, (r + 1) * boxSize, boxSize - 2, boxSize - 2);
                }
            }
        }
    }

    /**
     * Generates a random color
     * 
     * @return a random Color
     */
    private Color generateRandomColor() {
        Random rand = new Random();
        int R = rand.nextInt(256);
        int G = rand.nextInt(256);
        int B = rand.nextInt(256);

        Color rando = new Color(R, G, B);
        return rando;
    }

    /**
     * Sets the value of doRandomColor
     *
     * @param flag boolean if we want random colors
     */
    public void setRandom(boolean flag) {
        doRandomColor = flag;
        updateView(grid);
    }

}
