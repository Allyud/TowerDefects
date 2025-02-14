package BattleField;

import BattleField.Tiles.*;
import core.GlobalConstants;
import core.Position;
import core.RenderableObject;
import core.Size;
import entities.enemies.Enemy;
import entities.towers.Bullet;
import entities.towers.Crossbow;
import entities.towers.FireTower;
import entities.towers.Tower;
import game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.http.WebSocket;
import java.util.ArrayList;

public class BattleField extends RenderableObject {
    int gridWidth;
    int gridHeight;
    Tile[][] tileGrid;
    int[][] rawGrid;
    int tileSize;
    int[][] path;
    int pathLength;
    boolean Paused = false;
    BufferedImage fieldImage;
    ArrayList<Enemy> enemies;
    ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
    ArrayList<Tower> towers;
    SpawnPortal spawnPortal;
    Base base;
    Game game;
    private WebSocket.Listener enemyKilledListener;
    int fieldWidth, fieldHeight;
    public BattleField(int gridWidth, int gridHeight, int[][] rawGrid, int[][] path, int tileSize, Game game) {
        super(
                new Position((GlobalConstants.displayWidth - gridWidth*tileSize)/2 , (GlobalConstants.displayHeight - gridHeight*tileSize)/2),
                new Size(gridWidth*tileSize, gridHeight*tileSize)
             );
        this.game = game;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        fieldWidth = gridWidth * tileSize;
        fieldHeight = gridHeight * tileSize;
        this.path = path;
        pathLength = path.length;
        this.tileSize = tileSize;
        //Enemy.battleField = this;

        //rawGrid = new int[gridHeight][gridWidth];
        /*rawGrid = new int[][]{
                {2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2},
                {2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2},
                {2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2}}; */
        //path = new int[][]{ {0,1},{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{1,7} };
        spawnPortal= new SpawnPortal(this);
        base = new Base(this);
        towers = new ArrayList<Tower>();
        enemies = new ArrayList<Enemy>();
        setGrid(rawGrid);
        generateImage();
    }
    public void setGrid(int [][] rawGrid){
        this.rawGrid = rawGrid;
        // convert raw grid to tile grid
        tileGrid = new Tile[gridHeight][gridWidth];
        size = new Size(gridWidth*tileSize, gridHeight*tileSize);
        for(int y = 0; y < gridHeight; y++){
            for(int x = 0; x < gridWidth; x++){
                switch(rawGrid[y][x]){
                    case 0:
                        tileGrid[y][x] = new PathTile(x,y,this);
                        break;
                    case 1:
                        tileGrid[y][x] = new TowerTile(x,y,this);
                        break;
                    default:
                        tileGrid[y][x] = new WallTile(x,y,this, rawGrid[y][x] - 2);
                        break;
                }
            }
        }
    }
    @Override
    protected BufferedImage createImage(){
        image = new BufferedImage(gridWidth * tileSize, gridHeight * tileSize, BufferedImage.TYPE_INT_ARGB);
        updateFieldImage();
        return image;
    }

    protected void updateFieldImage(){
        fieldImage = new BufferedImage(gridWidth * tileSize, gridHeight * tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = fieldImage.createGraphics();

        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                Tile tile = tileGrid[row][col];
                if (tile != null && tile.getImage() != null) {
                    g.drawImage(tile.getImage(), col * tileSize, row * tileSize, null);
                }
            }
        }
        g.dispose();
    }

    public boolean pointIsInsideField(int X, int Y){
        return X < position.intX()+size.getWidth() && X > position.intX()
                && Y < position.intY()+size.getHeight() && Y > position.intY();
    }
    public Tile[][] getTileGrid() {
        return tileGrid;
    }
    public void spawnEnemy(Enemy enemy){
        enemy.spawn();
        enemies.add(enemy);
    }
    public int getTileSize(){
        return tileSize;
    }

    public void update(){
        if (!Game.isPaused()) {
            for (Enemy enemy : enemies) {
                enemy.update();
            }
            for (Tower tower : towers) {
                tower.update();
            }
            if(!enemiesToRemove.isEmpty()){
                enemies.removeAll(enemiesToRemove);
                enemiesToRemove.clear();
            }

        }
    }
    public void render(){
        Graphics2D g = image.createGraphics();

        g.fillRect(0, 0, fieldWidth, fieldHeight);
        g.drawImage(fieldImage, 0, 0, null);
        for (Enemy enemy : enemies) {
            if (enemy.isVisible()){
                g.drawImage(enemy.getImage(), enemy.getPosition().intX(), enemy.getPosition().intY(), null);
            }

        }
        for (Tower tower : towers) {
            if (tower.isVisible()){
                g.drawImage(tower.getImage(), tower.getPosition().intX(), tower.getPosition().intY(), null);
                for (Bullet bullet: tower.getBullets()){
                    if (bullet.isActive()){
                        g.drawImage(bullet.getImage(), bullet.getPosition().intX(), bullet.getPosition().intY(), null);
                    }
                }
            }
        }
        g.drawImage(spawnPortal.getImage(), spawnPortal.getPosition().intX(), spawnPortal.getPosition().intY(), null);
        g.drawImage(base.getImage(), base.getPosition().intX(), base.getPosition().intY(), null);
        g.dispose();
    }

    public void enemyKilled(Enemy enemy){
        enemies.remove(enemy);
        game.addMoney(enemy.getMoneyReward());
        game.addScore(enemy.getMoneyReward());
    }

    public SpawnPortal getSpawnPortal(){
        return spawnPortal;
    }
    public void addTower(Tower tower){
        towers.add(tower);
    }
    public ArrayList<Enemy> getCurrentEnemies(){
        return enemies;
    }
    public int getFieldWidth(){
        return fieldWidth;
    }
    public int getFieldHeight(){
        return fieldHeight;
    }
    public int[][] getPath(){
        return path;
    }
    public int getPathLength(){
        return pathLength;
    }
    public void highlightAvailableTowerTiles(){
        for (Tile[] row: tileGrid){
            for(Tile tile: row){
                if (tile instanceof TowerTile){
                    if (((TowerTile) tile).getTower() == null){
                        ((TowerTile)tile).setPurchaseModeImage();
                    }
                }
            }
        }
        updateFieldImage();
    };
    public void resetTowerTiles(){
        for (Tile[] row: tileGrid){
            for(Tile tile: row){
                if (tile instanceof TowerTile){
                    ((TowerTile)tile).setDefaultImage();
                }
            }
        }
        updateFieldImage();
    }

    public void highlightTowerTile(TowerTile towerTile) {
        towerTile.setPurchaseModeImage();
        updateFieldImage();
    }
    public void resetTowerTile(TowerTile towerTile) {
        towerTile.setDefaultImage();
        updateFieldImage();
    }
    public boolean isPaused(){
        return Paused;
    }
    public void deleteTower(Tower tower){
        tower.getTile().removeTower();
        towers.remove(tower);
    }
    public void dealDamage(int damage){
        base.recieveDamage(damage);
    }

    public void enemySucceed(Enemy enemy) {
        enemiesToRemove.add(enemy);
        dealDamage(enemy.getDamage());
        game.updateBaseHP(base.getHP());
    }

    public void baseDefeated(){
        System.out.println("Base defeated");
        game.baseDefeated();
    }

    public void highlightTower(TowerTile towerTile) {
        highlightTowerTile(towerTile);
        Graphics2D g = fieldImage.createGraphics();
        g.setColor(Color.GREEN);
        Stroke oldStroke = g.getStroke();
        int radius = (int) (towerTile.getTower().getRange()*tileSize);
        g.setStroke(new BasicStroke(4));
        g.drawOval(towerTile.getPosition().intX()-radius+tileSize/2,towerTile.getPosition().intY()-radius+tileSize/2, 2*radius, 2*radius);
        g.dispose();
        g.setStroke(oldStroke);
    }

    public Base getBase() {
        return base;
    }
}