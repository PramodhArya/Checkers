public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void position(int row, int col) {
        this.x = row;
        this.y = col;
    }

    public int row() {
        return x;
    }

    public int col() {
        return y;
    }

}