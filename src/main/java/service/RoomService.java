package service;

public class RoomService {
    public String generateUniqueRoomCode(){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 6; i++){
            int rd = (int) (Math.random() * 10);
            result.append(String.valueOf(rd));
        }
        return result.toString();
    }
}
