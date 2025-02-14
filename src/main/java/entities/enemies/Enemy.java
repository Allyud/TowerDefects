package entities.enemies;

import BattleField.BattleField;
import core.*;
import entities.towers.Bullet;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Enemy extends RenderableObject {

    int baseHP;
    int HP;
    double baseSpeed;
    double speed;
    double traveledDistance;
    boolean isAlive;
    private boolean isVisible;
    int moneyReward;
    double[] dirVector;
    double dirAngle;
    int lvl = 1;
    protected int[][] path;
    Position nextPos;
    protected Queue<Position> pathQueue;
    protected BattleField battleField;
    int damage = 1;
    public Enemy(Size size, int baseHP, double baseSpeed, int scale, BattleField battleField) {
        super(new Position(0,0), size);
        this.baseHP = baseHP;
        this.HP = baseHP*scale/100;
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
        isAlive = true;
        isVisible = true;
    }
    public void moveTowards(Position target){
        double dx = target.getX() - position.getX();
        double dy = target.getY() - position.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        traveledDistance += speed;
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

    public void bulletHit(Bullet bullet){
        if (!bullet.isActive()){
            HP -= (int) bullet.getDamage();
            if (HP <= 0){
                kill();
            }
        }
    }
    private void kill(){
        isAlive = false;
        isVisible = false;
        battleField.enemyKilled(this);
    }
    public void update() {
        if (!pathQueue.isEmpty()) {
            nextPos = pathQueue.peek();
            moveTowards(nextPos);
            if (reachedDestination(nextPos)) {
                pathQueue.poll(); // Move to the next step in the path
            }
        }
        else{
            battleField.enemySucceed(this);
            isAlive = false;
            isVisible = false;
        }
    }

    public double getSquaredDistance(Position pos){
        return Math.pow(pos.getX() - getCenterPosition().getX(), 2) + Math.pow(pos.getY() - getCenterPosition().getY(), 2);
    }
    public int getHealth(){
        return HP;
    }
    public double getTraveledDistance(){
        return traveledDistance;
    }
    public int getMoneyReward(){
        return moneyReward;
    }

    public boolean isVisible() {
        return isVisible;
    }
    public boolean isAlive() { return isAlive; }

    public int getDamage() {
        return damage;
    }

    //public abstract void update();
}
