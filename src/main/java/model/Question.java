package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question {

    String content;

    String [] answers;
    String audio;
    String rightAnswer;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "content='" + content + '\'' +
                ", answers=" + Arrays.toString(answers) +
                ", audio='" + audio + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                '}';
    }
}
