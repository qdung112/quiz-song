package controller.game;

import controller.room.RoomWebSocketEndpoint;
import model.Game;
import model.Point;
import org.codehaus.jackson.map.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet("/getAllPoint")
public class PointAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = req.getReader().readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        String roomId = mapper.readValue(sb.toString(),Game.class).getRoomId();
        List<Point> points = RoomWebSocketEndpoint.roomsPoint.get(roomId);
        points.sort(Comparator.comparing(Point::getPoint, Comparator.reverseOrder()));
        mapper.writeValue(resp.getOutputStream(), points);
    }
}
