package com.playtech.ptargame3.server.ai;


import java.util.List;

public class GameLogRecord {

    private final byte currentGamePhase;
    private final byte currentCarPhase;
    private final byte teamRedScore;
    private final byte teamBlueScore;
    private final GameLogRecordBall ball;
    private final List<GameLogRecordCar> cars;
    private final List<GameLogRecordBullet> bullets;

    public GameLogRecord(byte currentGamePhase, byte currentCarPhase, byte teamRedScore, byte teamBlueScore, GameLogRecordBall ball, List<GameLogRecordCar> cars, List<GameLogRecordBullet> bullets) {
        this.currentGamePhase = currentGamePhase;
        this.currentCarPhase = currentCarPhase;
        this.teamRedScore = teamRedScore;
        this.teamBlueScore = teamBlueScore;
        this.ball = ball;
        this.cars = cars;
        this.bullets = bullets;
    }

    public byte getCurrentGamePhase() {
        return currentGamePhase;
    }

    public byte getCurrentCarPhase() {
        return currentCarPhase;
    }

    public byte getTeamRedScore() {
        return teamRedScore;
    }

    public byte getTeamBlueScore() {
        return teamBlueScore;
    }

    public GameLogRecordBall getBall() {
        return ball;
    }

    public List<GameLogRecordCar> getCars() {
        return cars;
    }

    public List<GameLogRecordBullet> getBullets() {
        return bullets;
    }
}
