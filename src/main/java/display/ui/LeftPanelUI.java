package display.ui;

import entities.towers.Tower;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LeftPanelUI extends JPanel {
    private JButton nextWaveButton;
    private JButton pauseButton;
    private JLabel baseHPLabel;
    private JLabel moneyLabel;
    private JLabel scoreLabel;
    private JLabel waveLabel;
    private TowerUpgradeUI towerUpgradePanel;

    public LeftPanelUI() {
        // Set a fixed width for the left panel
        setPreferredSize(new Dimension(200, 900));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        nextWaveButton = new JButton("Next Wave");
        nextWaveButton.setFont(new Font("Open Sans", Font.BOLD, 24));
        nextWaveButton.setPreferredSize(new Dimension(150, 75));
        nextWaveButton.setMaximumSize(new Dimension(150, 75));
        nextWaveButton.setMargin(new Insets(0, 0, 0, 0));
        pauseButton = new JButton("Pause");
        pauseButton.setFont(new Font("Open Sans", Font.BOLD, 24));
        pauseButton.setPreferredSize(new Dimension(150, 75));
        pauseButton.setMaximumSize(new Dimension(150, 75));
        pauseButton.setMargin(new Insets(0, 0, 0, 0));
        baseHPLabel = new JLabel();
        moneyLabel = new JLabel();
        scoreLabel = new JLabel();
        waveLabel = new JLabel();
        // Create the tower upgrade panel and hide it by default.
        towerUpgradePanel = new TowerUpgradeUI();

        baseHPLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        baseHPLabel.setHorizontalAlignment(SwingConstants.CENTER);

        moneyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);

        waveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        waveLabel.setHorizontalAlignment(SwingConstants.CENTER);

        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);


        nextWaveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add components with vertical spacing
        add(waveLabel);
        add(Box.createVerticalStrut(20));
        add(nextWaveButton);
        add(Box.createVerticalStrut(10));
        add(pauseButton);
        add(Box.createVerticalStrut(10));
        add(moneyLabel);
        add(Box.createVerticalStrut(40));
        add(baseHPLabel);
        add(Box.createVerticalStrut(20));
        add(scoreLabel);
        add(Box.createVerticalStrut(40));
        add(towerUpgradePanel);
    }

    public void setNextWaveButtonListener(ActionListener listener) {
        nextWaveButton.addActionListener(listener);
    }
    public void setPauseButtonListener(ActionListener listener) {
        pauseButton.addActionListener(listener);
    }

    public void updateBaseHP(int hp) {
        baseHPLabel.setText("<html><center><H1>Base HP:<br>\uD83D\uDC97 " + hp+"</center></html>");
    }

    public void updateMoney(int money) {
        moneyLabel.setText("<html><center><H1>Money:<br>\uD83D\uDCB0 " + money +"</center></html>");
    }
    public void updateScore(int score) {
        scoreLabel.setText("<html><center><H1>Score:<br>" + score +"</center></html>");
    }
    public void updateWave(int waveCount) {
        waveLabel.setText("<html><center><H1>Wave:<br>\uD83C\uDF0A " + waveCount +"</center></html>");
    }

    public TowerUpgradeUI getTowerUpgradePanel() {
        return towerUpgradePanel;
    }

    public void showTowerUpgradePanel(Tower tower) {
        towerUpgradePanel.start(tower);
    }

    public void hideTowerUpgradePanel() {
        towerUpgradePanel.setVisible(false);
    }

    public void updateAll(int waveCount, int HP,int money, int score) {
        updateWave(waveCount);
        updateMoney(money);
        updateScore(score);
        updateBaseHP(HP);
    }
}
