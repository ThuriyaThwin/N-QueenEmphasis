package test;
public class Board {

    int chessBoard[][];
    int queens;

    public Board(int queens) {
        chessBoard = new int[20][20];
        this.queens = queens;
    }

    void displayBoard(){
        int i, j;
        for(i=0; i<queens; i++){
            for(j=0; j<queens; j++){
                if(chessBoard[i][j] == 1)
                    System.out.print(" * ");
                else
                    System.out.print(" - ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
        System.out.print("\n");
    }

    void reset(){
        for(int i=0; i<queens; i++){
            for(int j=0; j<queens; j++){
                chessBoard[i][j] = 0;
            }
        }
    }

    boolean isValidPlace(int row, int col){
        int i,j;

        //Checking horizontally
        for(i=col; i>=0; i--){
            if(chessBoard[row][i] == 1)
                return false;
        }

        //checking left diagonal
        for(i=row, j=col; i>=0 && j>=0; i--,j--){
            if(chessBoard[i][j] == 1)
                return false;
        }

        //checking right diagonal
        for(i=row, j=col; i<queens && j>=0; i++,j--){
            if(chessBoard[i][j] == 1)
                return false;
        }

        return true;
    }
}
