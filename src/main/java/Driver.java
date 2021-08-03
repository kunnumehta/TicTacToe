import Service.BoardManager;

import java.io.File;

public class Driver {

    public static void main(String[] args) throws Exception{
        if(args.length < 1) {
            System.out.println("input file is required");
            System.exit(-1);
        }
        File file = new File(Driver.class.getResource(args[0]).getFile());
        BoardManager boardManager = BoardManager.getInstance(3);
        boardManager.startGame(file);
    }
}
