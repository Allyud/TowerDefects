package BattleField;

import BattleField.Tiles.*;
import core.GlobalConstants;
import core.Position;
import core.RenderableObject;
import core.Size;
import entities.enemies.BasicEnemy;
import entities.enemies.Enemy;
import entities.towers.Crossbow;
import entities.towers.Tower;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

public class BattleField extends RenderableObject {
    int gridWidth;
    int gridHeight;
    Tile[][] tileGrid;
    int[][] rawGrid;
    int tileSize = 50;
    int[][] path;
    BufferedImage fieldImage;
    ArrayList<Enemy> enemies;
    ArrayList<Tower> towers;
    SpawnPortal spawnPortal;
    int fieldWidth, fieldHeight;
    public BattleField(int gridWidth, int gridHeight, int[][] rawGrid, int[][] path, int tileSize) {
        super(
                new Position((GlobalConstants.displayWidth - gridWidth*tileSize)/2 , (GlobalConstants.displayHeight - gridHeight*tileSize)/2),
                new Size(gridWidth*tileSize, gridHeight*tileSize)
             );
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        fieldWidth = gridWidth * tileSize;
        fieldHeight = gridHeight * tileSize;
        this.path = path;
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
        towers = new ArrayList<Tower>();
        enemies = new ArrayList<Enemy>();
        setGrid(rawGrid);
        generateImage();

        spawnEnemy(new BasicEnemy(100, this));
        /*for (int i = 0; i < 5; i++) {
            enemies.add(new BasicEnemy(100, this));
        }
        */
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
        return image;
    }
    public void handleMouseClick(int mouseX, int mouseY) {
        int tileX = (mouseX-50) / tileSize;
        int tileY = (mouseY-50) / tileSize;
        if (tileX < gridWidth && tileX >= 0 && tileY < gridHeight && tileY >= 0) {
            if (tileGrid[tileY][tileX] instanceof TowerTile towerTile) {
                if (towerTile.getTower() == null) {
                    Tower newTower = new Crossbow(tileX, tileY, this);
                    towerTile.setTower(newTower);
                    towers.add(newTower);
                    System.out.println("Placed a tower at: " + tileX + ", " + tileY);
                }
            }
        }

    }

    public Tile[][] getTileGrid() {
        return tileGrid;
    }
    private void spawnEnemy(Enemy enemy){
        enemy.spawn();
        enemies.add(enemy);
    }
    public int getTileSize(){
        return tileSize;
    }

    public void update(){
        for (Enemy enemy : enemies) {
            enemy.update();
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
            }
        }
        g.drawImage(spawnPortal.getImage(), spawnPortal.getPosition().intX(), spawnPortal.getPosition().intY(), null);
        g.dispose();
    }
    public SpawnPortal getSpawnPortal(){
        return spawnPortal;
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

}