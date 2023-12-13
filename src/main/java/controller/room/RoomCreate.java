package controller.room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "room", value = "/room/create")
public class RoomCreate  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomCode = generateUniqueRoomCode(); // Generate a unique room code
        response.getOutputStream().write(roomCode.getBytes());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    private String generateUniqueRoomCode() {
        // Generate a unique room code using UUID or any other method
        return UUID.randomUUID().toString();
    }
}