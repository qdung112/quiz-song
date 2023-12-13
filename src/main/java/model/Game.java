package model;

import dao.QuestionDAO;
import enums.GameStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    private String roomId;
    private final int timePerQues = 10;
    private final int timeOff = 2;
    private Integer currentQuestion;
    private Integer timeLeft = 12000;
    private Long userAnswer;
    private List<Question> questions;
    private GameStatus status;

    private Integer members;

    public Game(){
        this.status = GameStatus.PREPARE;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Integer currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Integer getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Integer timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Long getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Long userAnswer) {
        this.userAnswer = userAnswer;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public void run() {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

            }
        };
        int i = 0;
        setStatus(GameStatus.RUNNING);
        for(Question q : questions){
            currentQuestion = i;
            for(int j = 1; j <= 12; j++){
                //games.put(roomId,this);
                //timer.scheduleAtFixedRate(task, 0, 1000);
                try {
                    Thread.sleep(1000);
                } catch (Exception e){
                    e.printStackTrace();
                }
                timeLeft = (j-2)*1000;
                System.out.println(timeLeft);
            }
            i++;
        }
        setStatus(GameStatus.END);
    }
}
