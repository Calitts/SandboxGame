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

    @Deprecated
    public void copyData(Pixel[][] data){
        for (int x = 0; x < col; x++){
            for (int y = 0; y < row; y++) {
                screenData[x][y] = data[x][y];
            }
        }
    }

    public void setPixel(Pixel pixel, Vector2D position){
        screenData[position.getX()][position.getY()] = pixel;
    }

    public void setPixel(Pixel pixel, int x, int y){
        screenData[x][y] = pixel;
    }

    public Pixel getPixel(Vector2D position){
        return screenData[position.getX()][position.getY()];
    }

    public Pixel getPixel(int x, int y){
        return screenData[x][y];
    }

    public Pixel getNeighbor(Vector2D position, int xOffset, int yOffset){
        xOffset = position.getX() + xOffset;
        yOffset = position.getY() + yOffset;

        if (xOffset < 0 || xOffset > col-1) return new Pixel();
        if (yOffset < 0 || yOffset > row-1) return new Pixel();

        screenData[xOffset][yOffset].setPosition(xOffset, yOffset);
        return screenData[xOffset][yOffset];
    }

    //Questione meus metodos, mas não os meus resultados
    public Pixel[] getNeighborPixels(Vector2D position) {
        return new Pixel[]{
                getNeighbor(position, -1, 0),
                getNeighbor(position, 1, 0),
                getNeighbor(position, 0, -1),
                getNeighbor(position, 0, 1)
        };
    }
}
