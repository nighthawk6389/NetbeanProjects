/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cardGames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author Mavis Beacon
 */
public class Blackjack extends AbstractGame implements ActionListener,KeyListener{
    Cards deck;
    Players [] players;
    Players dealer;
    BlackjackSurface surface;
    JButton hit=new JButton("Hit");
    JButton stay=new JButton("Stay");
    JButton game = new JButton("New Game");
    boolean gameOver=false;
    private final int cardsPerHand=2;
    int currentPlayer;
    
    ExecutorService exec;
    
    public Blackjack(){
     deck=new Cards();
     players=new Players[1];
     for(int x=0;x<players.length;x++)
         players[x]=new Players();
     dealer=new Players();
     dealer.setId("Dealer");
     surface=new BlackjackSurface(dealer,players);
     
     exec = Executors.newCachedThreadPool();

     JPanel buttons=new JPanel();
     buttons.add(hit);
     buttons.add(stay);
     buttons.add(game);

     this.addKeyListener(this);
     hit.addActionListener(this);
     stay.addActionListener(this);
     game.addActionListener(this);

     this.setLayout(new BorderLayout());
     setFocusable(true);
     add(surface,"Center");
     add(buttons,"South");
     //add(hit,"South");
     //add(stay,"West");
     dealCards();
     //hit(players[0]);
     surface.updateSurface(dealer, players);

    } 

    private void dealCards(){
        deck.shuffle();
        dealer.getHand().empty();
        for(Players p:players){
            p.getHand().empty();
            p.setTurn(false);
        }
        List<Card> dealerCards = dealer.getHand().getCards();
        for(int x=0;x<cardsPerHand;x++){
            Card nextCardForDealer = deck.getNextCard();
            dealerCards.add(nextCardForDealer);
            if( x == 0)
                nextCardForDealer.setVisible(false);
            
            for(int y=0;y<players.length;y++){
                if(players[y].getMoney()>0)
                    players[y].getHand().getCards().add(deck.getNextCard());
            }
        }//end forTop
        System.out.println("Dealer: "+dealer.getHand());
         surface.updateSurface(dealer,players);
        for(int x=0;x<players.length;x++){
            players[x].setId("Player"+x);
            surface.updateSurface(dealer,players);
            System.out.println("Player"+x+": "+players[x].getHand());
        }
        currentPlayer=0;
        players[0].setTurn(true);
        surface.setColor(currentPlayer,true);
    }//end dealCards

    private void hit(final Players player){
        player.getHand().getCards().add(deck.getNextCard());
        System.out.println("After Hit: "+player.getId()+" has "+player.getHand());
        //surface.updateSurface(dealer,players);
        checkIfGame(player);
      
    }
    private void stay(final Players player){
        player.setTurn(false);
        setNextTurn();
        //surface.updateSurface(dealer, players);

    }
    private void dealersTurn(){
        Runnable run = new Runnable(){
            public void run(){
                dealer.getHand().setVisibility(true);
                surface.updateSurface(dealer, players);
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Blackjack.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for(int x=0;x<players.length;x++){
                    while(dealer.getHand().getBlackjackSum()<players[x].getHand().getBlackjackSum() && dealer.getHand().getBlackjackSum()<21){
                        hit(dealer);
                        surface.animateHit(dealer);
                        surface.updateSurface(dealer, players);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Blackjack.class.getName()).log(Level.SEVERE, null, ex);
                    }


                    }
                }//end for
                checkEndGame();
            }
        };
        exec.submit(run);
    }//end dealersTurn
    
    private void checkIfGame(Players player){
        int sum=player.getHand().getBlackjackSum();
        boolean bool=false;
        if(sum > 21){
            System.out.println(player.getId()+" Busted!");
            player.setTurn(false);
            bool=true;
        }
        else if(sum == 21){
            System.out.println(player.getId()+" hit BlackJack!");
            player.setTurn(false);
           bool=true;
        }

        if(bool && currentPlayer!=-1)
            setNextTurn();
        System.out.println(sum);

    }

    public void checkEndGame(){
        int dealerSum=dealer.getHand().getBlackjackSum();
        int playerSum=0;
        for(int x=0;x<players.length;x++){
            playerSum=players[x].getHand().getBlackjackSum();
            System.out.println("Players Sum: "+playerSum+"  Dealer Sum"+dealerSum);
            if(playerSum>21){
                 System.out.println("Player "+(x+1)+" busted");
                players[x].setMoney(players[x].getMoney()-players[x].getBet());
                break;
            }//end if

            if(dealerSum>playerSum && dealerSum<=21){
                System.out.println("Dealer beat player "+(x+1));
                surface.write("Dealer beat player "+(x+1));
                players[x].setMoney(players[x].getMoney()-players[x].getBet());
            }
            else{
                System.out.println("Player "+(x+1)+ " beat dealer ");
                surface.write("Player "+(x+1)+ " beat dealer ");
                players[x].setMoney(players[x].getMoney()+players[x].getBet());
            }
           System.out.println("Player "+(x+1)+" has "+players[x].getMoney());
        }//end for
        gameOver=true;
    }//end checkEndGame

    public void setNextTurn(){
        surface.setColor(currentPlayer,false);
        if(currentPlayer+1<players.length)
            currentPlayer++;
        else{
            currentPlayer=-1;
            dealersTurn();
        }
        surface.setColor(currentPlayer,true);
        if(currentPlayer!=-1){
            players[currentPlayer].setTurn(true);
            System.out.println("Current Turn: "+players[currentPlayer].getId());
        }

    }//end setNextTurn

    public boolean isNextTurn(){
        return currentPlayer==-1?false:true;
    }//end is next turn

    @Override
    protected void paintComponent(Graphics g){
        //surface.updateSurface(dealer, players);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Game Over: "+gameOver);
        
        Runnable run = null;
        if(e.getSource()==hit && !gameOver && currentPlayer != -1){
             System.out.println("H was pressed");
                run = new Runnable(){ public void run() { hit(players[currentPlayer]); } };
        }
        else if(e.getSource()==stay && !gameOver && currentPlayer != -1){
             System.out.println("S was pressed");
               run = new Runnable(){ public void run() {  stay(players[0]); } };
        }
        else if(e.getSource() == game){
            gameOver=false;
            run = new Runnable(){ public void run() { dealCards(); } };
            System.out.println("Dealing");
        }
        if(run != null)
            EventQueue.invokeLater(run);
    }//end actionPerfomed

    public void keyTyped(KeyEvent e) {
        Runnable run = null;
        if(!gameOver){
             if(e.getKeyChar()=='h' && isNextTurn()){
                System.out.println("H was pressed");
                run = new Runnable(){ public void run() { hit(players[currentPlayer]); } };
            }
            else if(e.getKeyChar()=='s'){
                System.out.println("S was pressed");
                run = new Runnable(){ public void run() { stay(players[0]); } };
            }//end else if
            else if(e.getKeyChar()=='b'){
                run = new Runnable(){ public void run() { System.out.println("B was pressed"); } };
            }//end else if
        }//end gameOver
        else if(gameOver){
            if(e.getKeyChar()=='d'){
                gameOver=false;
                run = new Runnable(){ public void run() { dealCards(); } };
                System.out.println("Dealing");
            }
        }//end else if
        if(run != null)
            EventQueue.invokeLater(run);
    }//end keyTyped

    public void keyPressed(KeyEvent e) {
        //System.out.println(e);
        
    }

    public void keyReleased(KeyEvent e) {
       //System.out.println(e);
    }
}//end BlackJack
