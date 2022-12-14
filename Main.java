import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Sierpinski's Triangle");

        Panel gamePanel = new Panel();
        window.add(gamePanel);// Adds the game panel to the current window as a component

        window.pack();// Sizes the window to the preferred size of its components

        // Changed from .setLocation to .setLocationRelativeTo, making the window display at the centre of the screen
        window.setLocationRelativeTo(null); // Window will be displayed at the centre of the screen
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}