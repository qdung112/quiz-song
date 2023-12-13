package dao;

import controller.room.Main;
import model.Answer;
import model.Question;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionDAO {

    public static List<Question> getListQuestionsByCate(Integer cateId){
        PreparedStatement pst;
        String sql;
        ResultSet rs;
        List<Question> questions = new ArrayList<>();
        try {
            sql = "SELECT c.idcategory, m.link, m.question, a1.answer1, a1.code as code1, a2.answer2, a2.code as code2, a3.answer3, a3.code as code3, a4.answer4, a4.code as code4\n" +
                    "FROM music m\n" +
                    "JOIN category c ON c.idcategory = m.idcategory\n" +
                    "JOIN answer1 a1 ON a1.idmusic = m.idmusic\n" +
                    "JOIN answer2 a2 ON a2.idmusic = m.idmusic\n" +
                    "JOIN answer3 a3 ON a3.idmusic = m.idmusic\n" +
                    "JOIN answer4 a4 ON a4.idmusic = m.idmusic \n" +
                    "WHERE c.idcategory = ? \n" +
                    "LIMIT 10";
            pst = DBConnection.getConnection().prepareStatement(sql);
            pst.setInt(1,cateId);
            rs = pst.executeQuery();
            while(rs.next()){
                Question question = new Question();
                question.setAudio(rs.getString("link"));
                question.setContent(rs.getString("question"));

                String [] answers = {rs.getString("answer1"),rs.getString("answer2"),
                rs.getString("answer3"),rs.getString("answer4")};
                rdAnswer(answers);
                for(int i = 1 ; i <= 4 ; i++){
                    int code = rs.getInt("code" + i);
                    if(code == 1){
                        question.setRightAnswer(rs.getString("answer"+i));
                    }
                }
                question.setAnswers(answers);
                questions.add(question);
            }
            return questions;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void rdAnswer(String [] answers){
        Random random = new Random();
        int rotations = random.nextInt(4) + 1;
        int length = answers.length;
        rotations = rotations % length; // Adjust rotations if it's greater than the array length
        reverse(answers, 0, length - 1); // Reverse the entire array
        reverse(answers, 0, rotations - 1); // Reverse the first part
        reverse(answers, rotations, length - 1); // Reverse the remaining part
    }

    public static void reverse(String [] arr, int start, int end) {
        while (start < end) {
            String temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

}
