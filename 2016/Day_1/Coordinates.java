class Coordinates {
    private int x;
    private int y;

    public Coordinates(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void incX(int i) {
        x += i;
    }

    public void decX(int i) {
        x -= i;
    }

    public void incY(int i) {
        y += i;
    }

    public void decY(int i) {
        y -= i;
    }
}

