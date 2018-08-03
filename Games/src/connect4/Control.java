  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package connect4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Mavis Beacon
 */
public class Control implements ActionListener {
    Board board;
    DrawBoard db;
    Player player1;
    Player player2;
    Player [] players;
    int turn=0;

  public static void main(String args []){

      Control c=new Control();

    }

    public Control(){
        board=new Board();

        int humanPlayer=0;
        int aiPlayer=1;
        
        db=new DrawBoard(new Board(board),this,humanPlayer);
        player1=new Player(humanPlayer+1);
        //player2=new Player(2);
        player2=new Connect4AI(aiPlayer+1,6);
        players=new Player[2]; 
        players[humanPlayer]=player1;
        players[aiPlayer]=player2;      


        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().add(db);
        frame.setSize(500,330);
        frame.setTitle("Connect 4 By Mavis Beacon Productions");

        if(players[turn].isAI)
            move(players[turn].notifyTurn(new Board(board)));

    }

    public void move(int place){  
        System.out.println("move");
  
        boolean valid=board.move(place, turn);
        
        if(checkWinner()) 
            return;

        db.animateMove(new Board(board),place,turn);  
        //db.updateBoard(new Board(board));
        //board.drawBoard();

        if(valid) 
            turn=(turn+1)%2;
        

    }//end move

    private boolean checkWinner(){
        int winner=board.isWon();
        if(winner!=0){
            win(winner);
            return true;
        }
        return false;
    }

    private void win(int winner){
        db.updateBoard(new Board(board));
        System.out.println("in win");
        JOptionPane.showMessageDialog(null, "Player " + winner + " has won");
        resetValues(); 
    } 

    public void playComputer(){
        System.out.println("In PlayComp Turn="+turn);
        if(checkWinner()) 
            return; 
        if(players[turn].isAI)   
            move(players[turn].notifyTurn(new Board(board)));
    }
 
    private void resetValues(){
        turn=0;
        db.reset();
        board.clearBoard();
        ((Connect4AI)player2).firstMove=true;
        db.updateBoard(new Board(board));
    }
    
    public void animationFinished(){
        if(players[turn].isAI)
           playComputer();    
    }

    public void actionPerformed(ActionEvent e) { 
        System.out.println(e.getActionCommand());
        if(e.getActionCommand().equals("1"))
            move(0);
        else if(e.getActionCommand().equals("2"))
            move(1);
        else if(e.getActionCommand().equals("3"))
            move(2);
        else if(e.getActionCommand().equals("4"))
            move(3);
        else if(e.getActionCommand().equals("5"))
            move(4);
        else if(e.getActionCommand().equals("6"))
            move(5);
    }
}
