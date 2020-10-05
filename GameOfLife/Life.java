import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
/*
    this is the Controller component
 */

class Life extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private LifeView view;
    private LifeModel model;
    private JButton runButton, pauseButton, resumeButton, resetButton;
    private JToggleButton colorButton;

    /**
     * construct a randomized starting grid
     */
    Life() throws IOException {
        this(null);
    }

    /**
     * construct a grid using the info in text file
     */
    Life(String fileName) throws IOException {
        super("Conway's Life");

        // build the buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        runButton = new JButton("Run");
        runButton.addActionListener(this);
        runButton.setEnabled(true);
        controlPanel.add(runButton);
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);
        pauseButton.setEnabled(false);
        controlPanel.add(pauseButton);
        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(this);
        resumeButton.setEnabled(false);
        controlPanel.add(resumeButton);
        // build the view
        view = new LifeView();
        view.setBackground(Color.white);

        // Reset Button
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setEnabled(false);
        controlPanel.add(resetButton);

        // Color Button
        colorButton = new JToggleButton("Randomize Color");
        colorButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    view.setRandom(true);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    view.setRandom(false);
                }
            }
        });

        colorButton.setEnabled(true);
        controlPanel.add(colorButton);

        // put buttons, view together
        Container c = getContentPane();
        c.add(controlPanel, BorderLayout.NORTH);
        c.add(view, BorderLayout.CENTER);

        // build the model
        model = new LifeModel(view, fileName);
    }

    public static void main(String[] args) throws IOException {
        Life conway = new Life("glgun13.lif"); // parameterize to start w/ a particular file

        conway.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        conway.setSize(570, 640);
        // conway.show(); //deprecated, added call below
        conway.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        if (b == runButton) {
            model.run();
            runButton.setEnabled(false);
            pauseButton.setEnabled(true);
            resumeButton.setEnabled(false);
            resetButton.setEnabled(false);
        } else if (b == pauseButton) {
            model.pause();
            runButton.setEnabled(false);
            pauseButton.setEnabled(false);
            resumeButton.setEnabled(true);
            resetButton.setEnabled(true);
        } else if (b == resumeButton) {
            model.resume();
            runButton.setEnabled(false);
            pauseButton.setEnabled(true);
            resumeButton.setEnabled(false);
            resetButton.setEnabled(false);
        } else if (b == resetButton) {
            model.reset();
            runButton.setEnabled(true);
            pauseButton.setEnabled(false);
            resumeButton.setEnabled(false);
            resetButton.setEnabled(false);
        }

    }
}
