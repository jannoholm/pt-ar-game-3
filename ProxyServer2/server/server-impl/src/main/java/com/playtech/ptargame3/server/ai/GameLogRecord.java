package com.playtech.ptargame3.server.ai;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class GameLogRecord {

    private final String gameId;
    private int tick;
    private byte currentGamePhase;
    private byte currentCarPhase;
    private byte teamRedScore;
    private byte teamBlueScore;
    private GameLogRecordBall ball;
    private List<GameLogRecordCar> cars = new ArrayList<>();
    private List<GameLogRecordBullet> bullets = new ArrayList<>();

    public GameLogRecord(String gameId) {
        this.gameId = gameId;
    }

    public void parse(ByteBuffer messageData) {
        while (messageData.hasRemaining()) {
            byte type = messageData.get();
            switch (type) {
                case 100: // game state
                    currentGamePhase = messageData.get();
                    currentCarPhase = messageData.get();
                    teamRedScore = messageData.get();
                    teamBlueScore = messageData.get();
                    tick = messageData.getInt();
                    break;
                case 101: // ball
                    ball = new GameLogRecordBall();
                    ball.parse(messageData);
                    break;
                case 1: // car red1
                case 2: // car red2
                case 3: // car blue1
                case 4: // car blue2
                    GameLogRecordCar car = new GameLogRecordCar(type);
                    car.parse(messageData);
                    cars.add(car);
                    break;
                case 110:
                    GameLogRecordBullet bullet = new GameLogRecordBullet();
                    bullet.parse(messageData);
                    bullets.add(bullet);
                    break;
            }
        }
    }

    public String getGameId() {
        return gameId;
    }

    public int getTick() {
        return tick;
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
