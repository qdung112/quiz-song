package enums;

public enum GameStatus {
    PREPARE(2),
    RUNNING(1),
    END(0)
    ;
    private int value;

    GameStatus(int i) {

    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
