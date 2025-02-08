package BattleField;

import java.util.ArrayList;

public class WaveManager {

    private ArrayList<Wave> waves;
    int currentWave;
    private BattleField battleField;
    public WaveManager(BattleField battleField) {
        this.battleField = battleField;
        this.waves = new ArrayList<>();
    }}
