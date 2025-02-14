package display.ui;

import entities.towers.Tower;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TowerUpgradeUI extends JPanel {
    private JLabel titleLabel;
    private JLabel towerInfoLabel;
    private JLabel damageLabel;
    private JLabel reloadLabel;
    private JLabel rangeLabel;
    private JButton upgradeButton;
    private JButton sellButton;
    private JPanel buttonsPanel;

    public TowerUpgradeUI() {
        setBorder(BorderFactory.createTitledBorder(""));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 250));
        setFont(new Font("Open Sans", Font.PLAIN, 20));
        // Title Label (Center)
        titleLabel = new JLabel("Tower Info", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Labels for Tower Stats
        damageLabel = new JLabel("Damage: -", SwingConstants.CENTER);
        damageLabel.setFont(new Font("Open Sans", Font.PLAIN, 20));
        reloadLabel = new JLabel("Reload: -", SwingConstants.CENTER);
        reloadLabel.setFont(new Font("Open Sans", Font.PLAIN, 20));
        rangeLabel = new JLabel("Range: -", SwingConstants.CENTER);
        rangeLabel.setFont(new Font("Open Sans", Font.PLAIN, 20));

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setPreferredSize(new Dimension(200, 100));
        upgradeButton = new JButton("Upgrade");
        upgradeButton.setFont(new Font("Open Sans", Font.PLAIN, 16));
        sellButton = new JButton("Sell");
        sellButton.setFont(new Font("Open Sans", Font.PLAIN, 16));

        upgradeButton.setMargin(new Insets(0, 0, 0, 0));
        upgradeButton.setPreferredSize(new Dimension(80, 80));
        sellButton.setMargin(new Insets(0, 0, 0, 0));
        sellButton.setPreferredSize(new Dimension(80, 80));

        buttonsPanel.add(upgradeButton);
        buttonsPanel.add(sellButton);
        // Buttons


        // Center-align components
        damageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        reloadLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rangeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sellButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the panel
        add(Box.createVerticalStrut(5));
        add(titleLabel);
        add(Box.createVerticalStrut(10));
        add(damageLabel);
        add(Box.createVerticalStrut(10));
        add(reloadLabel);
        add(Box.createVerticalStrut(10));
        add(rangeLabel);
        add(Box.createVerticalStrut(10));
        add(buttonsPanel);
        setVisible(false);
    }

    public void setUpgradeButtonListener(ActionListener listener) {
        for (ActionListener al : upgradeButton.getActionListeners()) {
            upgradeButton.removeActionListener(al);
        }
        upgradeButton.addActionListener(listener);
    }
    public void setSellButtonListener(ActionListener listener) {
        for (ActionListener al : sellButton.getActionListeners()) {
            sellButton.removeActionListener(al);
        }
        sellButton.addActionListener(listener);
    }
    public void start(Tower tower){
        setVisible(true);
        updateStats(tower);
    }

    public void updateStats(Tower tower) {
        titleLabel.setText(tower.getName() + " - level " + tower.getLevel());
        damageLabel.setText("Damage: " +tower.getDamage());
        reloadLabel.setText("Reload: " + tower.getReloadTime());
        rangeLabel.setText("Range: " + tower.getRange());
        upgradeButton.setText("<html><center>Upgrade<br>"+tower.getUpgradeCost()+"</center></html>");
        sellButton.setText("<html><center>Sell<br>"+tower.getSellCost()+"</center></html>");
    }
}
