package algo.yu.model;

public class Sentence {
    private int row;
    private String text;

    public Sentence(int row, String text) {
        this.row = row;
        this.text = text;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
