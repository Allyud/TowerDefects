package display.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel {
    private JLabel gameOverLabel;
    private JLabel scoreLabel;
    private JButton restartButton;

    public GameOverPanel() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 300));

        gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(gameOverLabel);
        add(Box.createVerticalStrut(20));
        add(scoreLabel);
        add(Box.createVerticalStrut(20));
        add(restartButton);
        add(Box.createVerticalGlue());
    }

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void setRestartButtonListener(ActionListener listener) {
        // Remove any existing listeners first
        for (ActionListener al : restartButton.getActionListeners()) {
            restartButton.removeActionListener(al);
        }
        restartButton.addActionListener(listener);
    }
}