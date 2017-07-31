package server.message.game;

import common.message.MessageHeader;
import common.util.StringUtil;
import server.message.AbstractMessage;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameUpdateMessage extends AbstractMessage {

    private List<GameUpdateCar> cars = new ArrayList<>();

    public GameUpdateMessage(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", cars={");
        for (int i = 0; i < cars.size(); ++i) {
            GameUpdateCar car = cars.get(i);
            if (i > 0) s.append(",");
            s.append("(");
            car.toStringImpl(s);
            s.append(")");
        }
        s.append("}");
    }

    @Override
    public void parse(ByteBuffer messageData) {
        int numberOfCars=messageData.getInt();
        for (int i=0; i < numberOfCars; ++i) {
            GameUpdateCar car = new GameUpdateCar();
            car.parse(messageData);
            cars.add(car);
        }
    }

    @Override
    public void format(ByteBuffer messageData) {
        messageData.putInt(cars.size());
        for (GameUpdateCar car : cars) {
            car.format(messageData);
        }
    }

    public Collection<GameUpdateCar> getCars() {
        return Collections.unmodifiableCollection(cars);
    }

    public void addCar(GameUpdateCar car) {
        this.cars.add(car);
    }
}
