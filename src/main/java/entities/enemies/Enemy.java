package entities.enemies;

import BattleField.BattleField;
import core.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public abstract class Enemy extends RenderableObject {

    int baseHP;
    int HP;
    double baseSpeed;
    double speed;

    boolean isAlive;
    private boolean isVisible;
    double[] dirVector;
    double dirAngle;
    int lvl = 1;
    protected int[][] path;
    Position nextPos;
    protected Queue<Position> pathQueue;
    protected BattleField battleField;
    public Enemy(Size size, int baseHP, double baseSpeed, BattleField battleField) {
        super(new Position(0,0), size);
        this.baseHP = baseHP;
        this.baseSpeed = baseSpeed ;
        speed = baseSpeed;
        this.battleField = battleField;
        path = battleField.getPath();
        generateImage();
        this.pathQueue = new LinkedList<>();
        initializePath(battleField.getPath());
    }

    public void spawn(){
        switch(battleField.getSpawnPortal().getDirection()){
            case 0:
                position.setPosition(path[0][1] * battleField.getTileSize(), path[0][0] * battleField.getTileSize() + (battleField.getTileSize() - size.getHeight())/2);
                break;
            case 1:
                position.setPosition(path[0][1] * battleField.getTileSize()+ (battleField.getTileSize() - size.getWidth())/2, (path[0][0]+1) * battleField.getTileSize() - size.getHeight());
                break;
            case 2:
                position.setPosition((path[0][1]+1) * battleField.getTileSize() - size.getWidth() , path[0][0] * battleField.getTileSize() + (battleField.getTileSize() - size.getHeight())/2);
                break;
            case 3:
                position.setPosition(path[0][1] * battleField.getTileSize()+ (battleField.getTileSize() - size.getWidth())/2, path[0][0] * battleField.getTileSize());
                break;
        }
        //position.setPosition(path[0][1] * battleField.getTileSize(), path[0][0] * battleField.getTileSize());
        //position.setPosition(100,100);
        isAlive = true;
        isVisible = true;
    }
    public void moveTowards(Position target){
        double dx = target.getX() - position.getX();
        double dy = target.getY() - position.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > speed) {
            position.setPosition(position.getX() + dx / distance * speed,
                    position.getY() + dy / distance * speed);
        } else {
            position.setPosition(target.getX(), target.getY());
        }
    }
    private boolean reachedDestination(Position target) {
        return Math.abs(position.getX() - target.getX()) < 1 &&
                Math.abs(position.getY() - target.getY()) < 1;
    }
    private void initializePath(int[][] path) {
        for (int[] tile : path) {
            pathQueue.add(new Position((tile[1]+0.5) * battleField.getTileSize() - (double) size.getWidth() /2 , (tile[0]+0.5) * battleField.getTileSize() - (double) size.getHeight() /2)); // Convert grid to pixels
        }
    }

    public void update() {
        if (!pathQueue.isEmpty()) {
            nextPos = pathQueue.peek();
            moveTowards(nextPos);
            if (reachedDestination(nextPos)) {
                pathQueue.poll(); // Move to the next step in the path
            }
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    //public abstract void update();
}
