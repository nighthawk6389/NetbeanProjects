/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package WordGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Mavis Beacon
 */
public class Word extends JPanel implements KeyListener,Runnable {
    final static int size=5;
    char [] letters=new char[5];
    int comp=0;
    Color background=Color.blue;
    Color defaultColor=new Color(240,240,240);
    boolean clearWord=false;

    public Word(boolean edit,KeyListener list){
        this("     ",edit,list);
    }//end word

    public Word(Word w,boolean edit,KeyListener list){
        this(w.getWord(),edit,list);
    }//end word

    public Word(String word,boolean edit,KeyListener list){
        this.setLayout(new GridLayout(1,size));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        for(int x=0;x<letters.length;x++){
            letters[x]=word.charAt(x);
            JTextField field=new JTextField(3);
            field.setFont(new Font(Font.SANS_SERIF,0,25));
            field.setHorizontalAlignment(JTextField.CENTER);
            field.setText(letters[x]+"");
            field.setEditable(false);
            if(edit==false)
                field.setForeground(new Color(50,50,50));
            else{
                field.setBackground(Color.white);
                field.setForeground(Color.black);
                field.addKeyListener(this);
                field.addKeyListener(list);
                field.setFocusable(true);
                defaultColor=Color.white;
            }
            this.add(field);

        }//end for
    }//end word

    public String getWord(){
       String word="";
       for(int x=0;x<letters.length;x++)
           word+=letters[x];
       return word;
    }//end getWord

   public void setWord(Word word){
        setWord(word.getWord());
    }//end setWord

       public void setWord(String word){
        letters=word.toCharArray();
        comp=0;
        write();
    }//end setWord

   private void write(){
       for(int x=0;x<letters.length;x++){
            JTextField field=(JTextField)this.getComponent(x);
            field.setText(letters[x]+"");
       }
       //comp=letters.length;
       ((JTextField)(this.getComponent(comp))).setBackground(new Color(200,250,250));
       new Thread(this).start();
   }//end write

   public void clearWord(){
       setAllBackground(Color.white);
       for(int x=0;x<letters.length;x++){
            JTextField field=(JTextField)this.getComponent(x);
            field.setText(" ");
            letters[x]=' ';
       }
       comp=0;
       ((JTextField)(this.getComponent(comp))).setBackground(new Color(200,250,250));
   }//end clearWord

   public void setClearWord(boolean bool){
       clearWord=true;
   }//end clear word

    public void setAllBackground(Color c){
         for(int x=0;x<letters.length;x++){
            JTextField field=(JTextField)this.getComponent(x);
            field.setBackground(c);
       }//end for
         background=c;
    }//end setAllBackround
   
    @Override
   public String toString(){
        return getWord();
   }

    public int compare(Word word){
        return (letters.toString()).compareTo(word.getWord());
    }//end compare

   public void run() {
        int total=letters.length;
        int count=0;
        while(count<total){
            JTextField field=(JTextField)this.getComponent(count);
            field.setBackground(background);
            count++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Word.class.getName()).log(Level.SEVERE, null, ex);
            }
            field.setBackground(defaultColor);
        }//end while
        background=Color.blue;
        if(clearWord){
            clearWord=false;
            clearWord();
        }
    }//end run

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        setAllBackground(Color.white);
        if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
            comp=comp==0?0:comp-1;
            ((JTextField)(this.getComponent(comp))).setText(" ");
            ((JTextField)(this.getComponent(comp))).setBackground(new Color(200,250,250));
            letters[comp]=' ';
        }
        else if(comp<size){
            ((JTextField)(this.getComponent(comp))).setText(e.getKeyChar()+"");
            ((JTextField)(this.getComponent(comp))).setBackground(new Color(200,250,250));
            letters[comp]=e.getKeyChar();
            comp=(comp<size?comp+1:comp);
        }
    }

    public void keyReleased(KeyEvent e) {
    }

}//end word
