package display.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ShopUI extends JPanel {
    private JButton buyCrossbowButton;
    private JButton buyFireTowerButton;
    private String selectedTower;
    public ShopUI() {
        // Set a preferred height for the shop panel.
        setPreferredSize(new Dimension(1100, 100));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // For example, one tower type
        buyCrossbowButton = new JButton("<html><center>Crossbow<br>(100)</center></html>");
        buyCrossbowButton.setMargin(new Insets(0, 0, 0, 0));
        buyCrossbowButton.setPreferredSize(new Dimension(80, 80));
        buyFireTowerButton = new JButton ("<html><center>FireTower<br>(150)</center></html>");
        buyFireTowerButton.setMargin(new Insets(0, 0, 0, 0));
        buyFireTowerButton.setPreferredSize(new Dimension(80, 80));
        add(buyCrossbowButton);
        add(buyFireTowerButton);
    }

    public void setBuyCrossbowListener(ActionListener listener) {
        buyCrossbowButton.addActionListener(listener);
    }
    public void setBuyFireTowerListener(ActionListener listener) {
        buyFireTowerButton.addActionListener(listener);
    }

    public String getSelectedTower() {
        return selectedTower;
    }
    public void setSelectedTower(String selectedTower) {
        this.selectedTower = selectedTower;
    }
}

