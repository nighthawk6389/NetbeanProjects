/*
 * PigPen.java
 *
 * Created on July 13, 2009, 1:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package codes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Ilan Elkobi
 */
public class PigPen implements ActionListener{
    List <Integer> array=new ArrayList<Integer>();
    List <Character> changedAlpha=new ArrayList<Character>();
    JTextField field;
    /** Creates a new instance of PigPen */
    public PigPen() {
    }
    public static void main(String args []){
    new PigPen().decode("suck");
    }
    public void decode(){
        createSet();
        createPigPen();
    }//end decode
    public void decode(String keyword){
        char chars[]=keyword.toCharArray();
        for(char let:chars){
            changedAlpha.add(let);
        }//end for
        createSet();
        createPigPen();
    }
    private void createSet(){
        List <Character> regAlpha=LetterSwap.populateRegular();
        
        Iterator <Character>it=regAlpha.listIterator();
        char next=' ';
        while(it.hasNext()){
            next=(char)it.next();
            if(!changedAlpha.contains(next))
                changedAlpha.add(next);
        }//end while
    }//end changedSet
    
    private void createPigPen(){
        JFrame frame1 = new JFrame();
        frame1.setVisible(true);
        frame1.setSize(870,400);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        JPanel panel=new JPanel();
        
        JButton button1=new JButton("_|");
        button1.addActionListener(this);
        button1.setName("1");
        panel.add(button1);
        
         JButton button2=new JButton("|_|");
        button2.addActionListener(this);
        button2.setName("2");
        panel.add(button2);
        
         JButton button3=new JButton("|_");
        button3.addActionListener(this);
        button3.setName("3");
        panel.add(button3);
        
         JButton button4=new JButton("=|");
        button4.addActionListener(this);
        button4.setName("4");
        panel.add(button4);
        
         JButton button5=new JButton("|=|");
        button5.addActionListener(this);
        button5.setName("5");
        panel.add(button5);
        
         JButton button6=new JButton("|=");
        button6.addActionListener(this);
        button6.setName("6");
        panel.add(button6);
        
         JButton button7=new JButton("-|");
        button7.addActionListener(this);
        button7.setName("7");
        panel.add(button7);
        
         JButton button8=new JButton("|-|");
        button8.addActionListener(this);
        button8.setName("8");
        panel.add(button8);
        
         JButton button9=new JButton("|-");
        button9.addActionListener(this);
        button9.setName("9");
        panel.add(button9);
        
        JButton button10=new JButton("_.|");
        button10.addActionListener(this);
        button10.setName("10");
        panel.add(button10);
        
         JButton button11=new JButton("|_.|");
        button11.addActionListener(this);
        button11.setName("11");
        panel.add(button11);
        
         JButton button12=new JButton("|_.");
        button12.addActionListener(this);
        button12.setName("12");
        panel.add(button12);
        
         JButton button13=new JButton("=.|");
        button13.addActionListener(this);
        button13.setName("13");
        panel.add(button13);
        
         JButton button14=new JButton("|=.|");
        button14.addActionListener(this);
        button14.setName("14");
        panel.add(button14);
        
         JButton button15=new JButton("|=.");
        button15.addActionListener(this);
        button15.setName("15");
        panel.add(button15);
        
         JButton button16=new JButton("-.|");
        button16.addActionListener(this);
        button16.setName("16");
        panel.add(button16);
        
         JButton button17=new JButton("|-.|");
        button17.addActionListener(this);
        button17.setName("17");
        panel.add(button17);
        
         JButton button18=new JButton("|-.");
        button18.addActionListener(this);
        button18.setName("18");
        panel.add(button18);
        
        JButton button19=new JButton("\\/");
        button19.addActionListener(this);
        button19.setName("19");
        panel.add(button19);
        
         JButton button20=new JButton(">");
        button20.addActionListener(this);
        button20.setName("20");
        panel.add(button20);
        
         JButton button21=new JButton("<");
        button21.addActionListener(this);
        button21.setName("21");
        panel.add(button21);
        
         JButton button22=new JButton("/\\");
        button22.addActionListener(this);
        button22.setName("22");
        panel.add(button22);
        
         JButton button23=new JButton("\\./");
        button23.addActionListener(this);
        button23.setName("23");
        panel.add(button23);
        
         JButton button24=new JButton(".>");
        button24.addActionListener(this);
        button24.setName("24");
        panel.add(button24);
        
         JButton button25=new JButton("<.");
        button25.addActionListener(this);
        button25.setName("25");
        panel.add(button25);
        
         JButton button26=new JButton("/.\\");
        button26.addActionListener(this);
        button26.setName("26");
        panel.add(button26);
        
        JButton submit=new JButton("Done");
        submit.addActionListener(this);
        panel.add(submit);
        
        field=new JTextField(50);
        panel.add(field);
        
        frame1.getContentPane().add(panel);
        
    }//end createPigPen

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getActionCommand().equals("Done")){
            getString();
        }
        else{
            JButton button=(JButton)e.getSource();
            String name=button.getName();
            int num=Integer.parseInt(name);
            array.add(num-1);
            field.setText(field.getText()+e.getActionCommand()+"   ");
        }//end else
    }//end actionPerformed
    
    private void getString(){
        String decoded="";
        Iterator<Integer> it=array.listIterator();
       
        while(it.hasNext()){
            decoded+=changedAlpha.get(it.next());
        }
        JOptionPane.showMessageDialog(null,decoded);
    }//end getString
}//end PigPen
