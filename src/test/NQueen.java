package test;

public class NQueen {
    int queens;
    boolean flag;
    Board board;

    public NQueen(int queens) {
        this.flag = false;
        this.board = new Board(queens);
        this.queens = queens;
    }

    void solveNqueen(){

        int i;
        //starting from (0,0) of the board
        hasSolution(2,0);

        //After running hasSolution() still flag == false
        //It means no Solution
        if(!flag)
            System.out.println("No Solution");

    }

    boolean hasSolution(int ctr, int colQueen){

        //Reached beyond last column
        //Means solution (configuration) found

        if(colQueen == queens){
            flag = true;
            board.displayBoard();
            //intentionally returning false to find more possible solution
            return false;
        }

        int i,j;
        for(i=ctr; i<queens; i++){

            //checking if position is safe
            if(board.isValidPlace(i,colQueen)){
                //setting current value to 1 means placing a queen
                board.chessBoard[i][colQueen] = 1;
                //moving to next column's 1st row
                if(hasSolution(0,colQueen+1))
                    return true;

                //backtrack
                //reset to 0 means removing queen
                board.chessBoard[i][colQueen] = 0;
            }

        }

        //When no row is safe in current column
        return false;
    }
}
