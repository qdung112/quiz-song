package controller.room;

import com.google.gson.Gson;
import dao.QuestionDAO;
import model.Game;
import model.Point;
import model.User;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/room/{roomId}")
public class RoomWebSocketEndpoint {
    private static final String USERNAME_ATTRIBUTE = "username";
    public static final Map<String, Set<Session>> rooms = new ConcurrentHashMap<>();
    public static final Map<String, LinkedList<User>> users = new ConcurrentHashMap<>();
    public static final Map<String, Game> games = new HashMap<>();
    public static final Map<String, List<Point>> roomsPoint = new HashMap<>();
    public static final Map<String, List<String>> userAnswer = new HashMap<>();

    Set<Session> roomSessions;
    List<Point> points = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId) {
        //System.out.println("username" + username);
        //welcomeUser(session,username);
        /*if(rooms.containsKey(roomId)){
            Set<Session> roomSessions = rooms.getOrDefault(roomId, Collections.emptySet());
            for (Session s : roomSessions) {
                if(s.getUserPrincipal().getName().equals(session.getUserPrincipal().getName())){
                    return;
                }
            }
        }*

        /*try {
            rooms.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(session);
        } catch (Exception e){
            check = false;
        }*/
        rooms.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(session);
        String userInfo = session.getUserPrincipal().getName();
        if(!roomsPoint.containsKey(roomId)){
            LinkedList<User> setUser = new LinkedList<>();
            User user = new User();
            user.setUsername(userInfo.substring(0,userInfo.indexOf("/")));
            user.setAvatar(userInfo.substring(userInfo.indexOf("/") + 1));
            setUser.add(user);
            users.put(roomId,setUser);
            List<Point> points = new ArrayList<>();
            roomsPoint.put(roomId,points);
        } else if(rooms.get(roomId).size() != users.get(roomId).size()){
            User user = new User();
            user.setUsername(userInfo.substring(0,userInfo.indexOf("/")));
            user.setAvatar(userInfo.substring(userInfo.indexOf("/") + 1));
            users.get(roomId).add(user);
        }
        // Send the updated list of usernames to all clients in the room
        broadcastUsernames(roomId);
    }

    @OnClose
    public void onClose(Session session, @PathParam("roomId") String roomId) {
        // Remove the session from the room
        String userInfo = session.getUserPrincipal().getName();
        rooms.get(roomId).remove(session);
        User user = new User();
        user.setUsername(userInfo.substring(0,userInfo.indexOf("/")));
        user.setAvatar(userInfo.substring(userInfo.indexOf("/") + 1));
        users.get(roomId).remove(user);
        broadcastUsernames(roomId);
        // Remove the username from the room's usernames
        // String username = getUsername(session);
        // Send the updated list of usernames to all clients in the room
        // broadcastUsernames(roomId);
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("roomId") String roomId, String message){
        if(message.startsWith("start game")){
            Integer cate = Integer.parseInt(message.substring(message.lastIndexOf(" ") + 1));
            Game game = new Game();
            game.setRoomId(roomId);
            game.setQuestions(QuestionDAO.getListQuestionsByCate(cate));
            game.setMembers(rooms.get(roomId).size());
            games.put(roomId,game);
            broadcastMessage(roomId,message);
            game.run();
        } else if (message.startsWith("point")) {
            Point point = new Point();
            point.setPoint(Integer.parseInt(message.substring(5)));
            point.setUsername(session.getUserPrincipal().getName().substring(0,session.getUserPrincipal().getName().indexOf("/")));
            roomsPoint.get(roomId).add(point);
        } else if(message.contains("choose")){
            broadcastMessage(roomId,message);
        }
    }

    public String getUsername(Session session) {
        return (String) session.getUserProperties().get(USERNAME_ATTRIBUTE);
    }

    public void setUsername(Session session, String username) {
        session.getUserProperties().put(USERNAME_ATTRIBUTE, username);
    }

    private void broadcastUsernames(String roomId) {
        Set<Session> roomSessions = rooms.getOrDefault(roomId, Collections.emptySet());

       /* for ( session : roomSessions) {
            User user = new User();
            user.setUsername(session.getUserPrincipal().getName());
            user.setAvatar((String) session.getUserProperties().get("avatar"));
            users.add(user);
        }*/

        Gson gson = new Gson();
        String jsonArray = gson.toJson(users.get(roomId));
        System.out.println(jsonArray);
        for (Session session : roomSessions) {
            session.getAsyncRemote().sendText(  jsonArray );
        }
    }

    private void broadcastQuestion(String roomId, String text){
        for (Session session : getRoom(roomId)) {
            session.getAsyncRemote().sendText("Question: " + text);
        }
    }

    private void broadcastMessage(String roomId, String text){
        // Create threads for sending text to each session
        List<Thread> threads = new ArrayList<>();

        for (Session session : getRoom(roomId)) {
            Thread thread = new Thread(() -> session.getAsyncRemote().sendText(text));
            threads.add(thread);
        }
        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.start();
        }
    }

    private void broadcastMessageAndRunGame(String roomId, String text){

        // Create threads for sending text to each session
        List<Thread> threads = new ArrayList<>();

        for (Session session : getRoom(roomId)) {
            Thread thread = new Thread(() -> session.getAsyncRemote().sendText(text));
            threads.add(thread);
            thread.start();
        }

        /*Thread gameThread = new Thread(() -> {
            Game game = new Game();
            game.setRoomId(roomId);
            games.put(roomId,game);
            game.run();
        });
        threads.add(gameThread);
        gameThread.start();*/

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void welcomeUser(Session session,String username){
        session.getAsyncRemote().sendText(username);
    }

    private void getAllUser(){

    }

    Set<Session> getRoom(String roomId){
        return rooms.getOrDefault(roomId, Collections.emptySet());
    }
    // Other WebSocket event handler methods


}