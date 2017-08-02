package com.playtech.ptargame3.server.util;


import com.playtech.ptargame3.api.lobby.Team;
import com.playtech.ptargame3.server.registry.GameRegistryGame;

public class TeamConverter {
    public static Team convert(GameRegistryGame.Team team) {
        switch (team) {
            case RED:
                return Team.RED;
            case BLUE:
                return Team.BLUE;
            default:
                throw new IllegalArgumentException("Unknown team type: " + team);
        }
    }

    public static GameRegistryGame.Team convert(Team team) {
        switch (team) {
            case RED:
                return GameRegistryGame.Team.RED;
            case BLUE:
                return GameRegistryGame.Team.BLUE;
            default:
                throw new IllegalArgumentException("Unknown team type: " + team);
        }
    }
}
