package Service;

import model.User;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BoardManager {

    private static BoardManager boardManager;
    private final char[][] board;
    private final int sizeOfBoard;
    private char currentTurn = 'x';
    private final Map<Character, User> symbolUserMap;
    public static BoardManager getInstance(int sizeOfBoard) {
        if(boardManager == null) {
            boardManager = new BoardManager(sizeOfBoard);
        }
        return boardManager;
    }
    private BoardManager(int sizeOfBoard) {
        this.sizeOfBoard = sizeOfBoard;
        board = new char[sizeOfBoard][sizeOfBoard];
        symbolUserMap = new HashMap<>();
        initializeBoard();
        printBoard();
    }


    private void initializeBoard(){
        for(int i = 0;i<sizeOfBoard;i++) {
            for(int j = 0;j<sizeOfBoard;j++){
                board[i][j] = '-';
            }
        }
    }
    public void startGame(File inputFile) throws Exception{
        Scanner scanner = new Scanner(inputFile);
        String[] user1 = scanner.nextLine().split(" ");
        User firstUser = new User(user1[0],user1[1]);
        symbolUserMap.put('x', firstUser);
        String[] user2 = scanner.nextLine().split(" ");
        User secondUser = new User(user2[0],user2[1]);
        symbolUserMap.put('o', secondUser);
        int count = 0;
        while(scanner.hasNext() && count < sizeOfBoard*sizeOfBoard){
            String nextLine = scanner.nextLine();
            if(nextLine.equals("exit")) return;
            String[] entry = nextLine.split(" ");
            int row = Integer.parseInt(entry[0]) - 1;
            int column = Integer.parseInt(entry[1]) - 1;
            if (validMove(row, column)){
                makeMove(row, column);
                printBoard();
                if(checkIfWinner()) {
                    System.out.println(symbolUserMap.get(currentTurn).getName() + " won the game");
                    break;
                }
                count++;
                if(currentTurn == 'x') currentTurn = 'o';
                else currentTurn = 'x';
            } else {
                System.out.println("invalid move");
            }
        }
        if(count == sizeOfBoard*sizeOfBoard) System.out.println("game over");
    }

    private void printBoard() {
        for(int i = 0;i<sizeOfBoard;i++) {
            for(int j = 0;j<sizeOfBoard;j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean validMove(int row, int column) {
        return board[row][column] == '-';
    }

    private void makeMove(int row, int column) {
        board[row][column] = currentTurn;
    }

    private boolean checkIfWinner() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i<sizeOfBoard;i++) {
            result.append(currentTurn);
        }
        for(int i = 0; i<sizeOfBoard;i++) {
            StringBuilder row = new StringBuilder();
            StringBuilder column = new StringBuilder();
           for(int j = 0; j<sizeOfBoard;j++) {
               row.append(board[i][j]);
               column.append(board[j][i]);
           }
           if(row.toString().equals(result.toString()) || column.toString().equals(result.toString())) return true;
        }
        String diagonal = "";
        StringBuilder reverseDiagonal = new StringBuilder();
        for(int i = 0; i<sizeOfBoard;i++) {

            for(int j = 0; j<sizeOfBoard;j++) {
                if(i==j) diagonal+=board[i][j];
                if(i+j==sizeOfBoard -1) reverseDiagonal.append(board[i][j]);
            }
        }
        return diagonal.equals(result.toString()) || reverseDiagonal.toString().equals(result.toString());
    }
}
