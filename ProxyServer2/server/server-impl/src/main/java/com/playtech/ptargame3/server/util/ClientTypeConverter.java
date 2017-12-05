package com.playtech.ptargame3.server.util;


import com.playtech.ptargame3.api.general.JoinServerRequest;
import com.playtech.ptargame3.server.registry.ProxyClientRegistry;

public class ClientTypeConverter {
    public static ProxyClientRegistry.ClientType convert(JoinServerRequest.ClientType clientType) {
        switch (clientType) {
            case TABLE:
                return ProxyClientRegistry.ClientType.TABLE;
            case CAMERA:
                return ProxyClientRegistry.ClientType.CAMERA;
            case GAME_CLIENT:
                return ProxyClientRegistry.ClientType.GAME_CLIENT;
            case PROXY:
                return ProxyClientRegistry.ClientType.PROXY;
            case CAR_CONTROL:
                return ProxyClientRegistry.ClientType.CAR_CONTROL;
            case BOT:
                return ProxyClientRegistry.ClientType.GAME_CLIENT;
            default:
                throw new IllegalArgumentException("Unknown client type: " + clientType);
        }
    }

    public static JoinServerRequest.ClientType convert(ProxyClientRegistry.ClientType clientType) {
        switch (clientType) {
            case TABLE:
                return JoinServerRequest.ClientType.TABLE;
            case CAMERA:
                return JoinServerRequest.ClientType.CAMERA;
            case GAME_CLIENT:
                return JoinServerRequest.ClientType.GAME_CLIENT;
            case PROXY:
                return JoinServerRequest.ClientType.PROXY;
            case CAR_CONTROL:
                return JoinServerRequest.ClientType.CAR_CONTROL;
            default:
                throw new IllegalArgumentException("Unknown client type: " + clientType);
        }
    }

}
