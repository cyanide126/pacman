public class PacCharacter{

    private char appearance;
    private int row;
    private int col;

    public PacCharacter(int row, int col, char appearance) {
        this.row = row;
        this.col = col;
        this.appearance = appearance;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getAppearance() {
        return appearance;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setAppearance(char appearance) {
        this.appearance = appearance;
    }
}
