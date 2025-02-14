package game;

import BattleField.BattleField;
import BattleField.Tiles.TowerTile;
import BattleField.WaveManager;
import core.GlobalConstants;
import display.Display;
import display.ui.*;
import entities.towers.*;
import input.MouseInput;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game {
    private static Game instance;
    public Display display;
    private BattleField battleField;
    private WaveManager waveManager;
    private MouseInput mouseInput;
    static private boolean isPaused = false;
    private int baseHP;
    private int money;
    private int score;
    private boolean towerPurchaseMode = false;
    private String selectedTowerType = null;
    private Tower selectedTowerForUpgrade = null;

    private static long gameStartTime = System.currentTimeMillis();
    private static long gamePauseStartTime;
    private static long totalGamePausedTime = 0;
    private GameOverPanel gameOverPanel;
    private GameState gameState = GameState.RUNNING;

    private int[][] initialGrid = new int[][]{
            {2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2},
            {0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 0, 2, 2, 2, 0, 1, 2, 2, 2, 2, 1, 0, 2},
            {2, 0, 2, 2, 2, 0, 2, 2, 2, 2, 2, 1, 0, 2},
            {2, 0, 1, 2, 1, 0, 1, 0, 0, 0, 0, 0, 0, 2},
            {2, 0, 0, 0, 0, 0, 1, 0, 1, 2, 2, 1, 2, 2},
            {2, 2, 2, 2, 1, 0, 1, 0, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 0, 2, 0, 1, 2, 2, 1, 2, 2},
            {2, 2, 2, 2, 2, 0, 2, 0, 0, 0, 0, 0, 0, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2}};
    private int[][] initialPath =  new int[][]{ {1,0},{1,1},{2,1},{3,1},{4,1},{5,1},{5,2},{5,3},{5,4},{5,5},{6,5},{7,5},{8,5},{7,5},{6,5},{5,5},{4,5},{3,5},{2,5},{1,5},{1,6},{1,7},{1,8},{1,9},{1,10},{1,11},{1,12},{2,12},{3,12},{4,12},{4,11},{4,10},{4,9},{4,8},{4,7},{5,7},{6,7},{7,7},{8,7},{8,8},{8,9},{8,10},{8,11},{8,12},{9,12}};
    //private Shop shop;

    private Game(int width, int height) {
        display = new Display(width, height);
        mouseInput = new MouseInput();
        display.getCanvas().addMouseListener(mouseInput);
        display.getCanvas().addMouseMotionListener(mouseInput);
        display.getCanvas().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                handleMouseClick(e.getX(), e.getY());
            }
        });
        /*battleField = new BattleField(14, 10,
        new int[][]{
                {2, 0, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2},
                {2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 0, 0, 0, 2, 1, 2, 2, 2, 2},
                {2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2}},
        new int[][]{ {0,1},{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{1,7}, {1,8},{1,9},{1,10},{1,11},{1,12},{2,12},{2,11},{1,11},{1,12},{2,12},{2,11},{3,11},{3,10},{3,9},{4,9},{4,8},{4,7},{5,7},{5,6},{5,5},{6,5},{6,4},{6,3},{7,3},{7,2},{7,1}, {8,1},{8,2},{8,3},{8,4},{8,5},{8,6},{8,7},{8,8},{8,9},{8,10},{8,11},{8,12},{9,12} },
                80);
         */
        ShopUI shopUI = display.getShopUI();
        shopUI.setBuyCrossbowListener(_ -> toggleTowerPurchaseMode("Crossbow", 100));
        shopUI.setBuyFireTowerListener(_ -> toggleTowerPurchaseMode("FireTower", 150));
        LeftPanelUI leftPanelUI = display.getLeftPanelUI();
        leftPanelUI.setNextWaveButtonListener(_ -> {
            waveManager.startNextWave();
            leftPanelUI.updateWave(waveManager.getCurrentWaveIndex());
        });
        leftPanelUI.setPauseButtonListener(_ -> {
            pause();
        });
        leftPanelUI.getTowerUpgradePanel().setUpgradeButtonListener(_ -> upgradeTower(selectedTowerForUpgrade));
        leftPanelUI.getTowerUpgradePanel().setSellButtonListener(_ -> sellTower(selectedTowerForUpgrade));
        startGame();
    }
    private void startGame(){
        money = 500;
        baseHP = 20;
        score = 0;
        selectedTowerForUpgrade = null;
        selectedTowerType = null;
        isPaused = false;
        gameState = GameState.RUNNING;
        if(gameOverPanel != null) {
            gameOverPanel.setVisible(false);
        }
        display.setGlassPane(new JPanel());
        // Reinitialize your battlefield, wave manager, etc.
        battleField = new BattleField(14, 10, initialGrid, initialPath, 80, this);
        waveManager = WaveManager.resetInstance(battleField);
        closeUpgradeUI();
        display.getLeftPanelUI().updateAll(waveManager.getCurrentWaveIndex(), baseHP, money, score);
        update();
    }
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game(GlobalConstants.displayWidth, GlobalConstants.displayHeight);
        }
        return instance;
    }
    public void update() {
        if (gameState == GameState.RUNNING) {
            battleField.update();
            waveManager.update();
            //display.getLeftPanelUI().setNextWaveButtonEnabled(waveManager.waveCanBeSpawned());

        }
    }

    public void baseDefeated(){
        gameState = GameState.GAME_OVER;
        showGameOverScreen();
    }
    private void showGameOverScreen() {
        // Create and configure the GameOverPanel
        gameOverPanel = new GameOverPanel();
        gameOverPanel.setScore(score);
        gameOverPanel.setRestartButtonListener(e -> startGame());

        // Set the GameOverPanel as the glass pane overlay
        display.setGlassPane(gameOverPanel);
        gameOverPanel.setVisible(true);
    }
    private void restartGame() {
        gameOverPanel.setVisible(false);
        display.setGlassPane(new JPanel()); // Reset glass pane

        // Reset game state, reinitialize objects, etc.
        money = 200;
        baseHP = 20;
        score = 0;
        gameState = GameState.RUNNING;
        // Reinitialize your battlefield, wave manager, etc.
        battleField = new BattleField(14, 10, initialGrid, initialPath, 80, this);
        waveManager = WaveManager.getInstance(battleField);
        // Update UI panels
        display.getLeftPanelUI().updateMoney(money);
        display.getLeftPanelUI().updateBaseHP(baseHP);
        display.getLeftPanelUI().updateWave(waveManager.getCurrentWaveIndex());
    }

    public void render() {
        display.render(this);
    }
    public void handleMouseClick(int mouseX, int mouseY) {
        if (towerPurchaseMode) {
            if (battleField.pointIsInsideField(mouseX, mouseY)) {
                int tileX = (mouseX- battleField.getPosition().intX()) / battleField.getTileSize();
                int tileY = (mouseY- battleField.getPosition().intY()) / battleField.getTileSize();
                if (battleField.getTileGrid()[tileY][tileX] instanceof TowerTile towerTile) {
                    if (towerTile.getTower() == null) {
                        placeTower(selectedTowerType, towerTile);
                        //towerTile.setDefaultImage();
                        toggleTowerPurchaseMode("",0 );
                    }
                }
            }
        }
        else{
            if (battleField.pointIsInsideField(mouseX, mouseY)) {
                int tileX = (mouseX- battleField.getPosition().intX()) / battleField.getTileSize();
                int tileY = (mouseY- battleField.getPosition().intY()) / battleField.getTileSize();
                if (battleField.getTileGrid()[tileY][tileX] instanceof TowerTile towerTile) {
                    if (!selectTower(towerTile)){
                        closeUpgradeUI();
                        if (selectedTowerForUpgrade != null) { battleField.resetTowerTile(selectedTowerForUpgrade.getTile());}
                    }
                }
                else{
                    closeUpgradeUI();
                    if (selectedTowerForUpgrade != null) { battleField.resetTowerTile(selectedTowerForUpgrade.getTile());}
                }
            }
        }
    }
    private boolean selectTower(TowerTile towerTile) {
        if (towerTile.getTower() != null) {
            if (selectedTowerForUpgrade != null) { battleField.resetTowerTile(selectedTowerForUpgrade.getTile());}
            selectedTowerForUpgrade = towerTile.getTower();
            battleField.highlightTower(selectedTowerForUpgrade.getTile());
            openUpgradeUI(selectedTowerForUpgrade);
            return true;
        }
        return false;
    }
    private void openUpgradeUI(Tower tower) {
        display.getLeftPanelUI().getTowerUpgradePanel().setUpgradeButtonListener(e -> upgradeTower(tower));
        display.getLeftPanelUI().showTowerUpgradePanel(tower);

    }
    public enum GameState {
        RUNNING, GAME_OVER
    }
    private void closeUpgradeUI() {
        display.getLeftPanelUI().hideTowerUpgradePanel();
    }
    private void upgradeTower(Tower tower) {
        int upgradeCost = tower.getUpgradeCost();
        if (money >= upgradeCost) {
            money -= upgradeCost;
            tower.upgrade();
            display.getLeftPanelUI().updateMoney(money);
            display.getLeftPanelUI().getTowerUpgradePanel().updateStats(tower);
        } else {
            System.out.println("Not enough money!");
        }
        selectTower(tower.getTile());
    }
    private void sellTower(Tower tower) {
        money += tower.getSellCost();
        display.getLeftPanelUI().updateMoney(money);
        closeUpgradeUI();
        battleField.deleteTower(tower);
    }

    private void toggleTowerPurchaseMode(String towerType, int cost) {
        System.out.println(towerType + "Pressed");
        if (towerPurchaseMode){
            towerPurchaseMode = false;
            battleField.resetTowerTiles();
        }
        else{
        if (money >= cost) {
            selectedTowerType = towerType;
            towerPurchaseMode = true;
            battleField.highlightAvailableTowerTiles();
        }
        else{
            System.out.println("Not enough money");
        }
        }
    }
    private void placeTower(String towerType, TowerTile towerTile){
        Tower newTower = null;
        switch (towerType){
            case "Crossbow":
                newTower = new Crossbow(towerTile, battleField);
                break;
            case "FireTower":
                newTower = new FireTower(towerTile, battleField);
                break;
        }
        if (newTower != null){
            towerTile.setTower(newTower);
            battleField.addTower(newTower);
            money -= newTower.getPrice();
            display.getLeftPanelUI().updateMoney(money);
            selectedTowerType = null;
        }
    }

    public BattleField getBattleField() {
        return battleField;
    }
    public void pause(){
        if (isPaused) {
            totalGamePausedTime += System.currentTimeMillis() - gamePauseStartTime;
            isPaused = false;
        }
        else{
            gamePauseStartTime = System.currentTimeMillis();
            isPaused = true;
        }
    }
    public static long getCurrentGameTime() {
        if (isPaused) {
            return gamePauseStartTime - gameStartTime - totalGamePausedTime;
        } else {
            return System.currentTimeMillis() - gameStartTime - totalGamePausedTime;
        }
    }
    public static boolean isPaused() {
        return isPaused;
    }
    public void addMoney(int money) {
        this.money += money;
        display.getLeftPanelUI().updateMoney(this.money);
    }

    public void updateBaseHP(int baseHP) {
        this.baseHP = baseHP;
        display.getLeftPanelUI().updateBaseHP(this.baseHP);
    }

    public void addScore(int score) {
        this.score+= score;
        display.getLeftPanelUI().updateScore(this.score);
    }
    /*public Shop getShop() {
        return shop;
    }
     */
    /*
    public void restartGame() {
        money = 500;
        baseHP = 20;

        battleField = new BattleField(14, 10, originalGrid, originalPath, tileSize);

        waveManager = WaveManager.getInstance(battleField);

        waveManager.reset();
        battleField.clearEnemies();
        battleField.clearTowers();
        display.getLeftPanelUI().updateMoney(money);
        display.getLeftPanelUI().updateBaseHP(baseHP);
    }

     */
}
