package server.message.lobby;

import common.message.MessageHeader;
import server.message.AbstractMessage;
import server.message.AbstractResponse;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetGamesResponse extends AbstractResponse {

    private List<GetGamesGameInfo> games=new ArrayList<>();

    public GetGamesResponse(MessageHeader header) {
        super(header);
    }

    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        int numberOfGames=messageData.getInt();
        for (int i=0; i < numberOfGames; ++i) {
            GetGamesGameInfo gameInfo = new GetGamesGameInfo();
            gameInfo.parse(messageData);
            games.add(gameInfo);
        }
    }

    public void format(ByteBuffer messageData) {
        super.format(messageData);
        messageData.putInt(games.size());
        for (GetGamesGameInfo gameInfo : games) {
            gameInfo.format(messageData);
        }
    }

    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", games={");
        for (int i = 0; i < games.size(); ++i) {
            GetGamesGameInfo game = games.get(i);
            if (i > 0) s.append(",");
            game.toStringImpl(s);
        }
        s.append("}");
    }

    public List<GetGamesGameInfo> getGames() {
        return Collections.unmodifiableList(games);
    }

    public void addGame(GetGamesGameInfo game) {
        this.games.add(game);
    }
}
