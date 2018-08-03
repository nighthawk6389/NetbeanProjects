/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cardGames;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author Mavis Beacon
 */
public class BlackjackSurface extends Surface{
    //JPanel allPlayers;
    //JPanel dealer;
    Players dealer;
    Players [] players;
    boolean gameOver=true;
    BufferedImage background;
    Image [] images;
    
    private static int CARD_W = 80;
    private static int CARD_H = 80;
    private static int OFFSET = 320;
    private static int VERTICAL_OFFSET = 100;
    private static int STRING_OFFSET = OFFSET + 5;

    public static void main(String args []){
        final JFrame frame=new JFrame();
        frame.setSize(800, 572);    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        final Blackjack bj=new Blackjack();

        Runnable run = new Runnable(){ 
            public void run() { 
                frame.getContentPane().add(bj); frame.invalidate(); frame.validate(); frame.repaint();  
            } 
        };
        EventQueue.invokeLater(run);
        
    }

    public BlackjackSurface(){
        try {
            images = new Image[53];
            
            images[0] = ImageIO.read(new File("card_images/d1.gif"));
            images[1] = ImageIO.read(new File("card_images/d2.gif"));
            images[2] = ImageIO.read(new File("card_images/d3.gif"));
            images[3] = ImageIO.read(new File("card_images/d4.gif"));
            images[4] = ImageIO.read(new File("card_images/d5.gif"));
            images[5] = ImageIO.read(new File("card_images/d6.gif"));
            images[6] = ImageIO.read(new File("card_images/d7.gif"));
            images[7] = ImageIO.read(new File("card_images/d8.gif"));
            images[8] = ImageIO.read(new File("card_images/d9.gif"));
            images[9] = ImageIO.read(new File("card_images/d10.gif"));
            images[10] = ImageIO.read(new File("card_images/dj.gif"));
            images[11] = ImageIO.read(new File("card_images/dq.gif"));
            images[12] = ImageIO.read(new File("card_images/dk.gif"));
            images[13] = ImageIO.read(new File("card_images/h1.gif"));
            images[14] = ImageIO.read(new File("card_images/h2.gif"));
            images[15] = ImageIO.read(new File("card_images/h3.gif"));
            images[16] = ImageIO.read(new File("card_images/h4.gif"));
            images[17] = ImageIO.read(new File("card_images/h5.gif"));
            images[18] = ImageIO.read(new File("card_images/h6.gif"));
            images[19] = ImageIO.read(new File("card_images/h7.gif"));
            images[20] = ImageIO.read(new File("card_images/h8.gif"));
            images[21] = ImageIO.read(new File("card_images/h9.gif"));
            images[22] = ImageIO.read(new File("card_images/h10.gif"));
            images[23] = ImageIO.read(new File("card_images/hj.gif"));
            images[24] = ImageIO.read(new File("card_images/hq.gif"));
            images[25] = ImageIO.read(new File("card_images/hk.gif"));
            images[26] = ImageIO.read(new File("card_images/c1.gif"));
            images[27] = ImageIO.read(new File("card_images/c2.gif"));
            images[28] = ImageIO.read(new File("card_images/c3.gif"));
            images[29] = ImageIO.read(new File("card_images/c4.gif"));
            images[30] = ImageIO.read(new File("card_images/c5.gif"));
            images[31] = ImageIO.read(new File("card_images/c6.gif"));
            images[32] = ImageIO.read(new File("card_images/c7.gif"));
            images[33] = ImageIO.read(new File("card_images/c8.gif"));
            images[34] = ImageIO.read(new File("card_images/c9.gif"));
            images[35] = ImageIO.read(new File("card_images/c10.gif"));
            images[36] = ImageIO.read(new File("card_images/cj.gif"));
            images[37] = ImageIO.read(new File("card_images/cq.gif"));
            images[38] = ImageIO.read(new File("card_images/ck.gif"));
            images[39] = ImageIO.read(new File("card_images/s1.gif"));
            images[40] = ImageIO.read(new File("card_images/s2.gif"));
            images[41] = ImageIO.read(new File("card_images/s3.gif"));
            images[42] = ImageIO.read(new File("card_images/s4.gif"));
            images[43] = ImageIO.read(new File("card_images/s5.gif"));
            images[44] = ImageIO.read(new File("card_images/s6.gif"));
            images[45] = ImageIO.read(new File("card_images/s7.gif"));
            images[46] = ImageIO.read(new File("card_images/s8.gif"));
            images[47] = ImageIO.read(new File("card_images/s9.gif"));
            images[48] = ImageIO.read(new File("card_images/s10.gif"));
            images[49] = ImageIO.read(new File("card_images/sj.gif"));
            images[50] = ImageIO.read(new File("card_images/sq.gif"));
            images[51] = ImageIO.read(new File("card_images/sk.gif"));
            images[52] = ImageIO.read(new File("card_images/b1fv.gif"));
            
            background = ImageIO.read(new File("card_images/background.jpg"));

            repaint();
        } catch (Exception ex) {
            Logger.getLogger(BlackjackSurface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BlackjackSurface(Players dealer,Players [] players){
        this();
        this.dealer=dealer;
        this.players=players;
    }//end blackjac
 
    public BlackjackSurface(int players){
        this();
        //this.setBackground(Color.blue);
        this.setLayout(new GridLayout(1,players));
    }//end blackjackSurface

    public Component getComponentWithName(String name,int z){
        Component [] c;
        if(z==-1)
            c=this.getComponents();
        else
            c=this.getComponents();
        for(int x=0;x<c.length;x++){
            if(c[x].getName()!=null)
                if(c[x].getName().equals(name))
                    return c[x];
        }
        return null; 
    }//end getComponent

    public void updateSurface(Players dealer,Players []players){
        /*DrawCard c1=(DrawCard)this.getComponentWithName("player"+x,x);
        Card card;
        String text="";
        pl.getHand().setIndex(0);
        while((pl.getHand().hasNextCard()) != false){
            card=pl.getHand().getNextCard();
            c1.setCard(card.toString());
            text+=" "+card.toString();
        }//end while
         * */
        this.dealer=dealer;
        this.players=players;
        this.revalidate();
        repaint();
    }//end updateSurface
    
    public void animateHit(Players player){
        
        //Runnable 
        
    }

    public void setColor(int x,boolean bool){
        /*
        DrawCard c1=(DrawCard)this.getComponentWithName("player"+x,x);
        if(bool)
            c1.setForeground(Color.red);
        else
            c1.setForeground(Color.black);
         * */
    }//end setColor

    public void write(String s){
        gameOver=true;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(background, 0, 0, null);
        
        Cards cards=dealer.getHand();
        int mult=0;
        while(cards.hasNextCard()){
            Card next = cards.getNextCard();
            int arrayIndex = getImageArrayIndex(next);;
            if( !next.getVisible() )
                arrayIndex = 52;
            g.drawImage(images[arrayIndex], OFFSET+CARD_W*mult, 10+VERTICAL_OFFSET, this);
            mult++;
        }
        mult++;
        cards.setIndex(0);

       mult = 0;
       for(int x=0;x<players.length;x++){
           cards=players[x].getHand();
           g.setColor(Color.black);
           if(players[x].isTurn())
               g.setColor(Color.red);
           //System.out.println("Turn? "+players[x].isTurn());
           mult = mult + x;
           while(cards.hasNextCard()){ 
                int arrayIndex = getImageArrayIndex(cards.getNextCard()); 
                g.drawImage(images[arrayIndex], OFFSET+CARD_W*mult, 250+VERTICAL_OFFSET, this);
                
                mult++; 
           }
           cards.setIndex(0);
           //mult++;mult++;
       }//end for

       if(!gameOver)
           g.drawString("Game Over", 10, 150);
    }//end painComponent

    private int getImageArrayIndex(Card nextCard) {
        
        int index = 0;
        
        String type = nextCard.getType();
        if( type.equalsIgnoreCase(Cards.diamonds))
            index = 0;
        else if( type.equalsIgnoreCase(Cards.hearts) )
            index = 13;
        else if( type.equalsIgnoreCase(Cards.clubs) )
            index = 26;
        else if( type.equalsIgnoreCase(Cards.spades) )
            index = 39;
        
        index += nextCard.getNumber() - 1;
        
        return index;
        
    }

}//end blackjackSurface
