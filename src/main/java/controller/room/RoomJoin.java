package controller.room;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/join")
public class RoomJoin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomCode = request.getParameter("roomId"); // Get the join code from the request parameters
        if(!RoomWebSocketEndpoint.rooms.containsKey(roomCode)){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        /*HttpSession httpsession = request.getSession(true);
        Session session = (Session) request.getSession();
        session.getUserProperties().put("username", username);
        RoomWebSocketEndpoint.rooms.computeIfAbsent(roomCode, k -> ConcurrentHashMap.newKeySet()).add(session);*/

    }
}
