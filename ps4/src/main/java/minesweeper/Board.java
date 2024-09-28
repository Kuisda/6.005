/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.util.*;

/**
 * TODO: Specification
 */
public class Board {
    // TODO: Abstraction function, rep invariant, rep exposure, thread safety
    // Abstraction function:
    //     represents a board of minesweeper game,the board message will be send to the user,like a player will see in the Minesweeper game.
    //     the realboard will only include the mine positions,in one word,the real situation
    // req invariant:
    //    private final char[][] board;
    //    private final int[][] realboard; //1 represent mine,0 represent empty
    //    private final int sizeX;
    //    private final int sizeY;
    //    private final int players;
    // rep exposure:
    //     use private and final fields to ensure that the rep is not exposed.
    //     the board can only be modified through the public methods provided.
    //     the realboard will generate at the beginning of the game, and will not be modified.
    // thread safety argument:
    //     the rep:sizeX,sizeY,realboard and dir will not be modified after the constructor,they are immutable.
    //     the functions that return or change variable players and board is needed to be care about thread safety.

    private final char[][] board;
    private final int[][] realboard;
    private final int sizeX;
    private final int sizeY;
    private int players;
    private final int[][] dir = {{-1,0},{1,0},{0,1},{0,-1},{-1,-1},{-1,1},{1,-1},{1,1}};


    /**
     * Constructor will build the realboard ,first generate mines positions, and then generate the number around the mines.
     * if position is a mine,the number will be -1;
     * @param sizeX
     * @param sizeY
     */
    public Board(int sizeX, int sizeY) {
        this.board = new char[sizeX][sizeY];
        this.realboard = new int[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.players = 0;
        int numMines = (int) ((sizeX * sizeY) * 0.2);

        for(int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY;j++){
                realboard[i][j] = 0;
                board[i][j] = '-';
            }
        }

        Random random = new Random();
        while(numMines>0){
            int position = random.nextInt(sizeX*sizeY);
            int row = position/sizeY;
            int col = position%sizeY;
            if(realboard[row][col] == 0){
                realboard[row][col] = 1;
                numMines-=1;
            }
        }
    }

    public Board(int sizeX,int sizeY,long seed) {
        this.board = new char[sizeX][sizeY];
        this.realboard = new int[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.players = 0;
        int numMines = (int) ((sizeX * sizeY) * 0.2);

        for(int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY;j++){
                realboard[i][j] = 0;
                board[i][j] = '-';
            }
        }

        Random random = new Random(seed);
        while(numMines>0){
            int position = random.nextInt(sizeX*sizeY);
            int row = position/sizeY;
            int col = position%sizeY;
            if(realboard[row][col] == 0){
                realboard[row][col] = 1;
                numMines-=1;
            }
        }
    }

    /**
     * constructor to load board from file.
     * @param sizeX
     * @param sizeY
     * @param boardData a list of characters representing the board.Read from file.(required its length equals to sizeX*sizeY)
     *
     */
    public Board(int sizeX,int sizeY,ArrayList<Integer> boardData){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.board = new char[sizeX][sizeY];
        this.realboard = new int[sizeX][sizeY];
        this.players = 0;
        for(int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY;j++){
                board[i][j] = '-';
                realboard[i][j] = boardData.get(i*sizeY+j);
            }
        }
    }

    /**return a board message,
     *
     * @return a board message,a string representation of the board’s state.Does not mutate anything on the server.
     */
    public String BoardMessage(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY;j++){
                sb.append(board[i][j]);
                if (j != sizeY - 1) {
                    sb.append(" ");
                } else {
                    sb.append("\r\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * Send to client when first connect to the server and join the game.
     *
     */
    public String HelloMessage(){
        synchronized (this){
            players += 1;
            return "Welcome to Minesweeper!  Board: " + sizeY + " columns " + sizeX + " rows  " + "Players: " + players + " including you." +
                    " Type 'help' for help.\r\n";
        }
    }

    /**
     * exec when client close the connection to the server.
     */
    public void HandleUserClose(){
        synchronized (this){
            players -= 1;
        }
    }

    public String HandleByeMessage(){
//        players -=1;
        return "Bye!\r\n";
    }

    public String HandleHelpMessage(){
        return "This is help message.";
    }

    /**when client send a command 'look' to the server,return the board message.
     *
     * @return a board message,a string representation of the board’s state
     */
    public String HandleLookMessage(){
        return BoardMessage();
    }

    /**
     * when client send a command 'dig x y' to the server,dig the position (x,y) and return the board message.
     * if position is a mine ,send "BOOM" message and set position to not mine.
     * if position is not a mine,change the board to show the state of the board after the dig.
     * if a flag is already set at the position or the position is already exposed,just send the board message.
     * @param x
     * @param y
     * @return "BOOM" message or board message
     */
    public String HandleDigMessage(int x,int y){
        synchronized (this){
            if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) {
                return BoardMessage();
            }
            if (board[x][y] == '-') {
                if (realboard[x][y] == 1) {
                    ChangeBoardAfterFailDig(x, y);
//                    players -= 1;
                    return "BOOM!\r\n";
                } else {
                    ChangeBoardAfterSuccessDig(x, y);
                }
            }
            return BoardMessage();
        }
    }

    /**
     * when client send a command 'flag x y' to the server,
     * if the position is not exposed ,set a flag at the position.
     * if the position is already exposed,just send the board message.
     * @param x
     * @param y
     * @return
     */
    public String HandleFlagMessage(int x,int y){
        synchronized (this) {
            if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) {
                return BoardMessage();
            }
            if (board[x][y] == '-') {
                board[x][y] = 'F';
            }
            return BoardMessage();
        }
    }

    public String HandleDeflagMessage(int x,int y){
        synchronized (this){
            if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) {
                return BoardMessage();
            }
            if (board[x][y] == 'F') {
                board[x][y] = '-';
            }
            return BoardMessage();
        }
    }



    /**when client dig a position without a mine ,change the board to show the state of the board after the dig.
     * to recursively turn near positions to ' ' in board if the position is without a mine,and its neighboring positions without mines.
     * end when neighboring positions have mines ,show the number of mines around the position.
     *
     * @param x
     * @param y
     * x,y is required to be valid
     */
    private void ChangeBoardAfterSuccessDig(int x,int y){
        if(board[x][y] == '-'){
            int count = FindNearMines(x,y);
            if(count == 0){
                board[x][y] = ' ';
                for(int i=0;i<8;i++){
                    int tempx = x+dir[i][0];
                    int tempy = y+dir[i][1];
                    if(tempx>=0 && tempx<sizeX && tempy>=0 && tempy<sizeY){
                        if(realboard[tempx][tempy] == 0){
                            ChangeBoardAfterSuccessDig(tempx,tempy);
                        }
                    }
                }

            }else{
                board[x][y] = (char)('0'+count);
            }
        }



    }

    /**find the neighboring positions of (x,y) that have mines
     *
     * @param x
     * @param y
     * @return the number of mines around (x,y)
     */
    private int  FindNearMines(int x,int y){
        int count = 0;
        for(int i=0;i<8;i++){
            int tempx = x+dir[i][0];
            int tempy = y+dir[i][1];
            if(tempx>=0 && tempx<sizeX && tempy>=0 && tempy<sizeY){
                if(realboard[tempx][tempy] == 1){
                    count += 1;
                }
            }
        }
        return count;
    }

    /**when client dig a position with a mine,send "BOOM" message and set position to not mine
     * if the surrounding position of (x,y) is exposed ,change the number in the board
     * @param x
     * @param y
     */
    private void ChangeBoardAfterFailDig(int x,int y){
        realboard[x][y] = 0;
        for(int i=0;i<8;i++){
            int tempx = x+dir[i][0];
            int tempy = y+dir[i][1];
            if(tempx>=0 && tempx<sizeX && tempy>=0 && tempy<sizeY){
                if(realboard[tempx][tempy] == 0){
                    //board[tempx][tempy] will be '-','F' or '1','2','3','4',since its neighboring positions are mines,it can't be ' ';
                    if(board[tempx][tempy]=='1'){
                        board[tempx][tempy] = ' ';
                    }else if(board[tempx][tempy] >='2' && board[tempx][tempy] <='4' ){
                        board[tempx][tempy] = (char)(board[tempx][tempy]-1);
                    }
                }
            }
        }

    }
}
