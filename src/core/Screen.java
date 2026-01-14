package core;

public class Screen {
    private final int col;
    private final int row;
    private Pixel[][] screenData;

    public Screen(int col, int row) {
        this.col = col;
        this.row = row;
        screenData = new Pixel[col][row];
    }

    public static Screen getEmptyScreen(int col, int row){
        Screen screen = new Screen(col, row);
        for (int x = 0; x < screen.col; x++){
            for (int y = 0; y < screen.row; y++){
                screen.screenData[x][y] = new Pixel();
            }
        }
        return screen;
    }

    public void clearScreen(){
        screenData = getEmptyScreen(col, row).screenData;
    }

    public void setPixel(Pixel pixel, Vector2D position){
        screenData[position.getX()][position.getY()] = pixel;
    }

    public Pixel getPixel(Vector2D position){
        return screenData[position.getX()][position.getY()];
    }

    public Pixel getNeighbor(Vector2D position, int xOffset, int yOffset ){
        if (position.getX() + xOffset < 0 && position.getX() + xOffset > col) return new Pixel();
        if (position.getY() + yOffset < 0 && position.getX() + yOffset > row) return new Pixel();

        return screenData[position.getX() + xOffset][position.getY() + yOffset];
    }
}
