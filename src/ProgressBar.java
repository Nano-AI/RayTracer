import javax.swing.*;

public class ProgressBar extends JPanel {

    JProgressBar pbar;
    JLabel message;

    private int min;
    private int max;
    private String title;
    public ProgressBar(String title, int min, int max) {
        this.min = min;
        this.max = max;
        this.title = title;
        // initialize Progress Bar
        pbar = new JProgressBar();
        message = new JLabel(title);
        pbar.setMinimum(min);
        pbar.setMaximum(max);
        pbar.setStringPainted(true);
        // add to JPanel
        add(message);
        add(pbar);
    }

    public void updateBar(int newValue) {
        pbar.setValue(newValue);
    }

    public void display() {
        JFrame frame = new JFrame(this.title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setVisible(true);
    }
}
