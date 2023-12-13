package controller.room;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/roomServer")
public class RoomServer {
    private static final Map<String, Session> rooms = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // Add session to the connected sessions set
        String roomCode = getRoomCodeFromRequest(config);

        // Associate the session with the room code
        rooms.put(roomCode, session);
    }

    @OnClose
    public void onClose (Session session) {
        // Remove session from the connected sessions set
        //rooms.remove(getRoomCodeFromRequest(config));
    }

    private String getRoomCodeFromRequest(EndpointConfig config) {
        String roomCode;
        roomCode = (String) config.getUserProperties().get("room");
        // You may need to perform additional validation or parsing on the room code if required
        return roomCode;
    }

    public static Map<String, Session> getRooms() {
        return rooms;
    }
}
