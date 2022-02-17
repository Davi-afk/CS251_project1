/**
 * CS 251: Data Structures and Algorithms
 * Project 1: Part 12
 *
 * TODO: Complete Maze.
 *
 * @author , Jordan Davis
 * @username , davi1304
 * @sources , none
 *
 *
 *
 */
public class Maze {

    private final char SPACE = '.';
    private final char WALL = '#';
    private final char START = '$';
    private final char END = '%';

    /**
     * Finds the path using CyclicDoubleQueue as a traditional stack
     * Returns the path and number of spaces checked as {@code String}.
     *
     * @param  map a {@code char[][]} provide.
     * @return the path and number of spaces checked as {@code String}
     */
    public String solveWithStack(char[][] map) throws Exception {
        int[][] hasVisited = new int[map.length][map[0].length];
        Boolean noWay = false;
        int SpacesChecked = 0;
        int row = 0;
        int column = 0;
        CyclicDoubleQueue<String> spaces = new CyclicDoubleQueue<>(2000, 20, .5);
        spaces.enqueueFront("$" + 0 + ","+ 0 + ":0");
        hasVisited[0][0] = 0;
        while (true) {
            boolean dq = true;
            hasVisited[row][column] = 1;
            String temp = spaces.dequeueBack();
            spaces.enqueueBack(temp.substring(0, temp.indexOf(":")) + ":1");
            if (row + 1 < map.length) {
                if ((map[row + 1][column] == SPACE) && (hasVisited[row + 1][column] != 1) && (hasVisited[row + 1][column] != 4)) {
                    spaces.enqueueBack("." + (row + 1) + "," + column + ":2");
                    dq = false;
                }
                else if ((map[row + 1][column] == END))
                {
                    spaces.enqueueBack("%" + (row + 1) + "," + column + ":3");
                    hasVisited[row + 1][column] = 3;
                    break;
                }
            }
            if (column + 1 < map[0].length) {
                if ((map[row][column + 1] == SPACE) && (hasVisited[row][column + 1] != 1) && (hasVisited[row][column + 1] != 4))
                {
                    spaces.enqueueBack("." + row + "," + (column + 1) + ":2");
                    dq = false;
                }
                else if ((map[row][column + 1] == END))
                {
                    spaces.enqueueBack("%" + row + "," + (column + 1) + ":3");
                    hasVisited[row][column + 1] = 3;
                    break;
                }
            }
            if ((row - 1) > -1) {
                if ((map[row - 1][column] == SPACE) && (hasVisited[row - 1][column] != 1) && (hasVisited[row - 1][column] != 4)) {
                    spaces.enqueueBack("." + (row - 1) + "," + column + ":2") ;
                    dq = false;
                }
                else if ((map[row - 1][column] == END))
                {
                    spaces.enqueueBack("%" + (row - 1) + "," + column + ":3");
                    hasVisited[row - 1][column] = 3;
                    break;
                }
            }
            if ((column - 1) > -1) {
                if ((map[row][column - 1] == SPACE) && (hasVisited[row][column - 1] != 1) && (hasVisited[row][column - 1] != 4)) {
                    spaces.enqueueBack("." + row + "," + (column - 1) + ":2");
                    dq = false;
                }
                else if ((map[row][column - 1] == END))
                {
                    spaces.enqueueBack("%" + row + "," + (column - 1) + ":3");
                    hasVisited[row][column - 1] = 3;
                    break;
                }
            }
            if (dq)
            {
                String temp2 = spaces.dequeueBack();
                spaces.enqueueBack(temp2.substring(0, temp2.indexOf(":")) + ":4");
                hasVisited[row][column] = 4;

                spaces.dequeueBack();
                if (spaces.isEmpty())
                {
                    noWay = true;
                    break;
                }
                else {
                    String val = spaces.peekBack();
                    row = Integer.parseInt(val.substring(1, val.indexOf(",")));
                    column = Integer.parseInt(val.substring(val.indexOf(",") + 1, val.indexOf(":")));
                }
            }
            else {
                String val = spaces.peekBack();
                row = Integer.parseInt(val.substring(1, val.indexOf(",")));
                column = Integer.parseInt(val.substring(val.indexOf(",") + 1, val.indexOf(":")));
                hasVisited[row][column] = 1;
                SpacesChecked++;
            }
        }
        SpacesChecked = -1;
        for (int i = 0; i < hasVisited.length; i++)
        {
            for (int j = 0; j < hasVisited[0].length; j++)
            {
                if (hasVisited[i][j] != 0)
                {
                    SpacesChecked++;
                }
            }
        }
        String result = null;
        if (noWay)
        {
            result = "no way";
        }
        else {
            for (int i = 0; i < spaces.getQueueLength(); i++) {
                if (!spaces.isEmpty()) {
                    String x = spaces.dequeueFront();
                    int y = Integer.parseInt(x.substring(x.indexOf(":") + 1));
                    if (i != 0) {
                        if ((y == 1) || (y == 3))
                        {
                            result = result + "(" + x.substring(1, x.indexOf(",")) + "," + x.substring(x.indexOf(",") + 1, x.indexOf(":")) + ")";
                        }
                    } else {
                        if ((y == 1) || (y == 0)) {
                            result = "(" + x.substring(1, x.indexOf(",")) + "," + x.substring(x.indexOf(",") + 1, x.indexOf(":")) + ")";
                        }
                    }
                }
            }
            result = result + " " + SpacesChecked;
        }
        return result;
    }

    /**
     * Finds the path using CyclicDoubleQueue as a traditional queue.
     * Returns the path and number of spaces checked as {@code String}.
     *
     * @param  map a {@code char[][]} provide.
     * @return the path and number of spaces checked as {@code String}
     */
    public String solveWithQueue(char[][] map) throws Exception {
        int[][] hasVisited = new int[map.length][map[0].length];
        Boolean noWay = false;
        int SpacesChecked = 0;
        int row = 0;
        int column = 0;
        CyclicDoubleQueue<String> spaces = new CyclicDoubleQueue<>(2000, 20, .5);
        CyclicDoubleQueue<String> Actual = new CyclicDoubleQueue<>(2000, 20, .5);
        CyclicDoubleQueue<String> Check = new CyclicDoubleQueue<>(2000, 20, .5);
        spaces.enqueueFront("$" + 0 + ","+ 0 + ":0");
        hasVisited[0][0] = 0;
        while (true) {
            boolean dq = true;
            hasVisited[row][column] = 1;
            String temp = spaces.dequeueFront();
            spaces.enqueueFront(temp.substring(0, temp.indexOf(":")) + ":1");
            if (row + 1 < map.length) {
                if ((map[row + 1][column] == SPACE) && (hasVisited[row + 1][column] != 1) && (hasVisited[row + 1][column] != 4)) {
                    spaces.enqueueBack("." + (row + 1) + "," + column + ":2");
                    dq = false;
                }
                else if ((map[row + 1][column] == END))
                {
                    spaces.enqueueBack("%" + (row + 1) + "," + column + ":3");
                    Actual.enqueueBack(temp.substring(0, temp.indexOf(":")) + ":1");
                    Actual.enqueueBack("%" + (row + 1) + "," + column + ":3");
                    hasVisited[row + 1][column] = 3;
                    break;
                }
            }
            if (column + 1 < map[0].length) {
                if ((map[row][column + 1] == SPACE) && (hasVisited[row][column + 1] != 1) && (hasVisited[row][column + 1] != 4))
                {
                    spaces.enqueueBack("." + row + "," + (column + 1) + ":2");
                    dq = false;
                }
                else if ((map[row][column + 1] == END))
                {
                    spaces.enqueueBack("%" + row + "," + (column + 1) + ":3");
                    Actual.enqueueBack(temp.substring(0, temp.indexOf(":")) + ":1");
                    Actual.enqueueBack("%" + row + "," + (column + 1) + ":3");
                    hasVisited[row][column + 1] = 3;
                    break;
                }
            }
            if ((row - 1) > -1) {
                if ((map[row - 1][column] == SPACE) && (hasVisited[row - 1][column] != 1) && (hasVisited[row - 1][column] != 4)) {
                    spaces.enqueueBack("." + (row - 1) + "," + column + ":2") ;
                    dq = false;
                }
                else if ((map[row - 1][column] == END))
                {
                    spaces.enqueueBack("%" + (row - 1) + "," + column + ":3");
                    Actual.enqueueBack(temp.substring(0, temp.indexOf(":")) + ":1");
                    Actual.enqueueBack("%" + (row - 1) + "," + column + ":3");
                    hasVisited[row - 1][column] = 3;
                    break;
                }
            }
            if ((column - 1) > -1) {
                if ((map[row][column - 1] == SPACE) && (hasVisited[row][column - 1] != 1) && (hasVisited[row][column - 1] != 4)) {
                    spaces.enqueueBack("." + row + "," + (column - 1) + ":2");
                    dq = false;
                }
                else if ((map[row][column - 1] == END))
                {
                    spaces.enqueueBack("%" + row + "," + (column - 1) + ":3");
                    Actual.enqueueBack(temp.substring(0, temp.indexOf(":")) + ":1");
                    Actual.enqueueBack("%" + row + "," + (column - 1) + ":3");
                    hasVisited[row][column - 1] = 3;
                    break;
                }
            }
            if (dq)
            {
                String temp2 = spaces.dequeueFront();
                spaces.enqueueFront(temp2.substring(0, temp2.indexOf(":")) + ":4");
                hasVisited[row][column] = 4;
                Actual.enqueueBack(spaces.dequeueFront());
                if (spaces.isEmpty())
                {
                    noWay = true;
                    break;
                }
                else {
                    String val = spaces.peekFront();
                    row = Integer.parseInt(val.substring(1, val.indexOf(",")));
                    column = Integer.parseInt(val.substring(val.indexOf(",") + 1, val.indexOf(":")));
                }
            }
            else {
                Actual.enqueueBack(spaces.dequeueFront());
                String val = spaces.peekFront();
                row = Integer.parseInt(val.substring(1, val.indexOf(",")));
                column = Integer.parseInt(val.substring(val.indexOf(",") + 1, val.indexOf(":")));
                hasVisited[row][column] = 1;
                SpacesChecked++;
            }
        }
        SpacesChecked = -1;
        for (int i = 0; i < hasVisited.length; i++)
        {
            for (int j = 0; j < hasVisited[0].length; j++)
            {
                if (hasVisited[i][j] != 0)
                {
                    SpacesChecked++;
                }
            }
        }
        Boolean noWay2 = false;
        row = 0;
        column = 0;
        CyclicDoubleQueue<String> spaces2 = new CyclicDoubleQueue<>(2000, 20, .5);
        CyclicDoubleQueue<String> spaces3 = new CyclicDoubleQueue<>(2000, 20, .5);
        spaces2.enqueueFront("$" + 0 + ","+ 0 + ":0");
        spaces3.enqueueFront(  0 + ","+ 0);
        int[][] hasVisited2 = new int[map.length][map[0].length];
        String[] visited = new String[200000];
        int index = 0;
        visited[index] = 0 + "," + 0;
        index++;
        hasVisited2[0][0] = 0;
        while (true) {
            boolean dq = true;
            hasVisited2[row][column] = 1;
            String temp = spaces2.dequeueBack();
            spaces2.enqueueBack(temp.substring(0, temp.indexOf(":")) + ":1");
            if (row + 1 < map.length) {
                boolean doit = true;
                String val = (row + 1) + "," + (column);
                for (int i = 0; i < index; i++)
                {
                    if (visited[i].equals(val))
                    {
                        doit = false;
                        break;
                    }
                }
                if ((map[row + 1][column] == SPACE) && (doit))
                {
                    spaces3.enqueueBack((row + 1) + "," + (column));
                    visited[index] = (row + 1) + "," + (column);
                    index++;
                }
                else if ((map[row + 1][column] == END))
                {
                    spaces3.enqueueBack((row + 1) + "," + (column));
                    break;
                }
            }
            if (column + 1 < map[0].length) {
                boolean doit = true;
                String val = (row) + "," + (column + 1);
                for (int i = 0; i < index; i++)
                {
                    if (visited[i].equals(val))
                    {
                        doit = false;
                        break;
                    }
                }
                if ((map[row][column + 1] == SPACE) && (doit))
                {
                    spaces3.enqueueBack((row) + "," + (column + 1));
                    visited[index] = (row) + "," + (column + 1);
                    index++;
                }
                else if ((map[row][column + 1] == END))
                {
                    spaces3.enqueueBack((row) + "," + (column + 1));
                    break;
                }
            }
            if ((row - 1) > -1) {
                boolean doit = true;
                String val = (row - 1) + "," + (column);
                for (int i = 0; i < index; i++)
                {
                    if (visited[i].equals(val))
                    {
                        doit = false;
                        break;
                    }
                }
                if ((map[row - 1][column] == SPACE) && (doit))
                {
                    spaces3.enqueueBack((row - 1) + "," + (column));
                    visited[index] = (row - 1) + "," + (column);
                    index++;
                }
                else if ((map[row][column + 1] == END))
                {
                    spaces3.enqueueBack((row - 1) + "," + (column));
                    break;
                }
            }
            if ((column - 1) > -1) {
                boolean doit = true;
                String val = (row) + "," + (column - 1);
                for (int i = 0; i < index; i++)
                {
                    if (visited[i].equals(val))
                    {
                        doit = false;
                        break;
                    }
                }
                if ((map[row][column - 1] == SPACE) && (hasVisited2[row][column - 1] != 1) && (hasVisited2[row][column - 1] != 4))
                {
                    spaces3.enqueueBack((row) + "," + (column - 1));
                    visited[index] = (row) + "," + (column - 1);
                    index++;
                }
                else if ((map[row][column + 1] == END))
                {
                    spaces3.enqueueBack((row) + "," + (column - 1));
                    break;
                }
            }
            if ((column - 1) > -1) {
                if ((map[row][column - 1] == SPACE) && (hasVisited2[row][column - 1] != 1) && (hasVisited2[row][column - 1] != 4)) {
                    spaces2.enqueueBack("." + row + "," + (column - 1) + ":2");
                    dq = false;
                }
                else if ((map[row][column - 1] == END))
                {
                    spaces2.enqueueBack("%" + row + "," + (column - 1) + ":3");
                    hasVisited2[row][column - 1] = 3;
                    break;
                }
            }
            if ((row - 1) > -1) {
                if ((map[row - 1][column] == SPACE) && (hasVisited2[row - 1][column] != 1) && (hasVisited2[row - 1][column] != 4)) {
                    spaces2.enqueueBack("." + (row - 1) + "," + column + ":2") ;
                    dq = false;
                }
                else if ((map[row - 1][column] == END))
                {
                    spaces2.enqueueBack("%" + (row - 1) + "," + column + ":3");
                    hasVisited2[row - 1][column] = 3;
                    break;
                }
            }
            if (column + 1 < map[0].length) {
                if ((map[row][column + 1] == SPACE) && (hasVisited2[row][column + 1] != 1) && (hasVisited2[row][column + 1] != 4))
                {
                    spaces2.enqueueBack("." + row + "," + (column + 1) + ":2");
                    dq = false;
                }
                else if ((map[row][column + 1] == END))
                {
                    spaces2.enqueueBack("%" + row + "," + (column + 1) + ":3");
                    hasVisited2[row][column + 1] = 3;
                    break;
                }
            }
            if (row + 1 < map.length) {
                if ((map[row + 1][column] == SPACE) && (hasVisited2[row + 1][column] != 1) && (hasVisited2[row + 1][column] != 4)) {
                    spaces2.enqueueBack("." + (row + 1) + "," + column + ":2");
                    dq = false;
                }
                else if ((map[row + 1][column] == END))
                {
                    spaces2.enqueueBack("%" + (row + 1) + "," + column + ":3");
                    hasVisited2[row + 1][column] = 3;
                    break;
                }
            }
            if (dq)
            {
                String temp2 = spaces2.dequeueBack();
                spaces2.enqueueBack(temp2.substring(0, temp2.indexOf(":")) + ":4");
                hasVisited2[row][column] = 4;

                spaces2.dequeueBack();
                if (spaces.isEmpty())
                {
                    noWay2 = true;
                    break;
                }
                else {
                    String val = spaces2.peekBack();
                    row = Integer.parseInt(val.substring(1, val.indexOf(",")));
                    column = Integer.parseInt(val.substring(val.indexOf(",") + 1, val.indexOf(":")));
                }
            }
            else {
                String val = spaces2.peekBack();
                row = Integer.parseInt(val.substring(1, val.indexOf(",")));
                column = Integer.parseInt(val.substring(val.indexOf(",") + 1, val.indexOf(":")));
                hasVisited2[row][column] = 1;
            }
        }
        String result = null;
        if (noWay2)
        {
            result = "no way";
        }
        else {
            String y = spaces3.dequeueBack();
            result = "(" + y + ")";
            row = Integer.parseInt(y.substring(0, y.indexOf(",")));
            column = Integer.parseInt(y.substring(y.indexOf(",") + 1));
            for (int i = 0; i < spaces3.getQueueLength(); i++)
            {
                if ((row == 0) && (column == 0))
                {
                    break;
                }
                if (!spaces3.isEmpty()) {
                    String x = spaces3.peekBack();
                    int row2 = Integer.parseInt(x.substring(0, x.indexOf(",")));
                    int column2 = Integer.parseInt(x.substring(x.indexOf(",") + 1));
                    if ((row == (row2 + 1)) && (column == column2)) {
                        y = spaces3.dequeueBack();
                        result = "(" + y + ")" + result;
                        row = Integer.parseInt(y.substring(0, y.indexOf(",")));
                        column = Integer.parseInt(y.substring(y.indexOf(",") + 1));
                    } else if ((row == (row2)) && (column == (column2 + 1))) {
                        y = spaces3.dequeueBack();
                        result = "(" + y + ")" + result;
                        row = Integer.parseInt(y.substring(0, y.indexOf(",")));
                        column = Integer.parseInt(y.substring(y.indexOf(",") + 1));
                    } else if ((row == (row2 - 1)) && (column == column2)) {
                        y = spaces3.dequeueBack();
                        result = "(" + y + ")" + result;
                        row = Integer.parseInt(y.substring(0, y.indexOf(",")));
                        column = Integer.parseInt(y.substring(y.indexOf(",") + 1));
                    } else if ((row == (row2)) && (column == (column2 - 1))) {
                        y = spaces3.dequeueBack();
                        result = "(" + y + ")" + result;
                        row = Integer.parseInt(y.substring(0, y.indexOf(",")));
                        column = Integer.parseInt(y.substring(y.indexOf(",") + 1));
                    } else {
                        spaces3.dequeueBack();
                    }
                }
            }
            result = result + " " + SpacesChecked;
        }
        return result;
    }
    public static void main(String[] args) throws Exception {
        Maze maze = new Maze();
        char[][] map = {
                {'$', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'.', '.','.', '.','.', '.', '.', '.', '#', '#'},
                {'#', '.', '#', '#', '#', '#', '#', '#', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '%', '#', '#'},
                {'#', '#', '.', '#', '.', '#', '#', '#', '#', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
                };
        char[][] map2 = {
                {'$', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'.', '.','.', '.','.', '.', '.', '.', '#', '#'},
                {'#', '#', '#', '#', '#', '.', '.', '#', '.', '#'},
                {'#', '.','.', '.','.', '.', '.', '.', '#', '#'},
                {'#', '#','.', '#','.', '#', '%', '#', '#', '#'}

        };
        char[][] map3 = {
                {'$', '.', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '#', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '.', '.', '#', '#', '#', '#', '.', '.', '#', '#', '#', '#', '.', '.', '#', '.', '.', '#', '#', '#', '#', '.', '.', '#'}
        };
        System.out.println(maze.solveWithQueue(map2));
    }

}
