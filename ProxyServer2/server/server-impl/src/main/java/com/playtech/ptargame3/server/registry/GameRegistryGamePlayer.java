package com.playtech.ptargame3.server.registry;


public class GameRegistryGamePlayer {
    private final String clientId;
    private final GameRegistryGame.Team team;
    private final byte positionInTeam;

    public GameRegistryGamePlayer(String clientId, GameRegistryGame.Team team, byte positionInTeam) {
        this.clientId = clientId;
        this.team = team;
        this.positionInTeam = positionInTeam;
    }

    public String getClientId() {
        return clientId;
    }

    public GameRegistryGame.Team getTeam() {
        return team;
    }

    public byte getPositionInTeam() {
        return positionInTeam;
    }
}
