/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package connect4;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Mavis Beacon
 */
public class DrawBoard extends JPanel implements Runnable,MouseListener{

    Control c;

    Board board;
    JPanel north;
    JPanel main;
    JPanel south;

    JTextField one;
    JTextField two;
    JTextField three;
    JTextField four;
    JTextField five;
    JTextField six;
    JTextField seven;

    JTextField playerTurn;

    Color defaultColor=new Color(240,240,240);
    int currentColor=0;
    int currentPlace;

    boolean hold=false;
    boolean okToReturn=true;

    public DrawBoard(){
        this.board=new Board();
        this.c=new Control();
    }
    public DrawBoard(Board b,Control c,int currentColor){
        this.board=b;
        this.c=c;
        this.currentColor=currentColor;

        initializeBoard();
    }
    public DrawBoard(int [][] b, Control c){
        this.board=new Board(b);
        this.c=c;

        initializeBoard();
    }


    public void initializeBoard(){
        this.setLayout(new BorderLayout());

        north=new JPanel();
        north.setLayout(new GridLayout(1,6));
        one=new JTextField("  ");
        two=new JTextField("  ");
        three=new JTextField("  ");
        four=new JTextField("  ");
        five=new JTextField("  ");
        six=new JTextField("  ");
        seven=new JTextField("  ");


        one.setEditable(false);
        two.setEditable(false);
        three.setEditable(false);
        four.setEditable(false);
        five.setEditable(false);
        six.setEditable(false);
        seven.setEditable(false);

        one.setName("1");
        two.setName("2");
        three.setName("3");
        four.setName("4");
        five.setName("5");
        six.setName("6");
        seven.setName("7");


        one.addMouseListener(this);
        two.addMouseListener(this);
        three.addMouseListener(this);
        four.addMouseListener(this);
        five.addMouseListener(this);
        six.addMouseListener(this);
        seven.addMouseListener(this);


        one.setFont(new Font(Font.SANS_SERIF,0,25));

        north.add(one);
        north.add(two);
        north.add(three);
        north.add(four);
        north.add(five);
        north.add(six);
        north.add(seven);

        main=new JPanel();
        main.setLayout(new GridLayout(board.vertical,board.horizontal,0,0));
        for(int x=0;x<board.vertical;x++){
            for(int y=0;y<board.horizontal;y++){
                main.add(new Hole(board.board[y][x],x,y));
            }//end for
        }//end for

        south=new JPanel();
        playerTurn=new JTextField("Player 1's Turn");
        playerTurn.setForeground(Color.red);
        playerTurn.setEditable(false);
        south.setLayout(new FlowLayout());
        south.add(playerTurn);


        this.add(north,BorderLayout.NORTH);
        this.add(main,BorderLayout.CENTER);
        this.add(south,BorderLayout.SOUTH);


    }//end intializeBoard

    public void setBoard(Board b){
        this.board=b;
    }

    public void updateBoard(Board temp){
        this.board=temp;
        
        this.remove(main);
        main=new JPanel();
        main.setLayout(new GridLayout(board.vertical,board.horizontal,0,0));
        for(int x=0;x<board.vertical;x++){
            for(int y=0;y<board.horizontal;y++){
                main.add(new Hole(temp.board[y][x],x,y));
            }//end for
        }//end for
        this.add(main,BorderLayout.CENTER);

        String text;
        Color color;
        if(currentColor==2){
            text="Player 1's Turn";
            color=Color.red;
        }
        else{
            text="Players 2's Turn";
            color=Color.GREEN;
        }
        playerTurn.setForeground(color);
        playerTurn.setText(text);

        this.revalidate();
        this.repaint();

    }//end upateBoard

    public void animateMove(Board board,int place,int turn){
        //this.board=board;
        this.currentColor=turn+1;
        this.currentPlace=place;

        new Thread(this).start();
        
        
    }//end animateMove

    public void reset(){
        System.out.println("Reset");
        playerTurn.setForeground(Color.red);
        playerTurn.setText("Player 1's Turn");
        this.currentColor=2;
    }
    
    public void run() {
        int x=0;
        DrawBoard.this.hold=true;
        while( x<board.vertical && board.board[currentPlace][x]==0){
              board.board[currentPlace][x]=currentColor;
              updateBoard(board);
              try {
                  Thread.sleep(70);
              } catch (InterruptedException ex) {}
              board.board[currentPlace][x]=0;
              x++;
        }//end while

        if(x-1>=0)
            board.board[currentPlace][x-1]=currentColor;

        updateBoard(board);

        DrawBoard.this.hold=false;
        
        //Here becasue of animation reasons
        c.animationFinished();
    }//end run

    public void mouseClicked(MouseEvent e) {
        if(hold)
            return;
        if(currentColor!=2 && currentColor!=0)
            return;
        JTextField label=((JTextField)(e.getSource()));
        String name=label.getName();
        int place=Integer.parseInt(name);
        c.move(place-1);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
        JTextField label=((JTextField)(e.getSource()));
        String name=label.getName();
        Color color=(this.currentColor==2 || this.currentColor==0)?Color.white:Color.lightGray;
        //label.setBackground(color);

        Graphics g=label.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, label.getWidth(), label.getHeight());
        
    }

    public void mouseExited(MouseEvent e) {
        JTextField label=((JTextField)(e.getSource()));
        label.setBackground(defaultColor);
        Graphics g=label.getGraphics();
        g.clearRect(0, 0, label.getWidth(), label.getHeight());
    }


}//end DrawBoard
