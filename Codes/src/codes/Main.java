/*
 * Main.java
 *
 * Created on July 13, 2009, 12:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package codes;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Ilan Elkobi
 */
public class Main implements ActionListener{
    JButton encrypt,decode,messageText,startOver;
    JPanel allHold,panel,panel2,panel3,panel4,right;
    JFrame frame;
    JTextField field,key,month,year;
    String which;
    /** Creates a new instance of Main */
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Main main=new Main();
    }
    
    public Main() {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(870,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        allHold=new JPanel();
        allHold.setLayout(new BorderLayout());
        
        panel=new JPanel();
        panel.add(encrypt=new JButton("Encrypt"));
        panel.add(decode=new JButton("Decode"));
        encrypt.addActionListener(this);
        decode.addActionListener(this);
        
        right=new JPanel();
        right.setLayout(new BorderLayout());
        startOver=new JButton("Start Over");
        startOver.addActionListener(this);
        right.add(startOver,"West");
        
        panel2=new JPanel();
        panel3=new JPanel();
        
        allHold.add(panel,"Center");
        allHold.add(right,"East");
        
        frame.getContentPane().add(allHold);
    }
    

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==encrypt){
            panel2=setMessageText("encrypt");
            which="encrypt";
            encrypt.setEnabled(false);
            decode.setEnabled(false);
            panel.add(panel2);
        }//end if encypt
        
        else if(e.getSource()==decode){
            panel2=setMessageText("decode");
            which="decode";
            encrypt.setEnabled(false);
            decode.setEnabled(false);
            panel.add(panel2);
        }//end else if decode
        
        else if(e.getSource()==messageText){
            panel3=getOptions();
            //field.setEditable(false);
            //key.setEditable(false);
            //messageText.setEnabled(false);
            panel.add(panel3);
        }//end else if messageText
        
        else if(e.getActionCommand().equals("Use Letter Swap")){
            String message=getLetterSwapMessage();
            output(message);
        }//end else if letter swap
        
        else if(e.getActionCommand().equals("Use PigPen")){
            PigPen pen=new PigPen();
            String message="";
            pen.decode(key.getText());
        }//end else if pigPen
        
        else if(e.getActionCommand().equals("Use Snail")){
            Snail snail=new Snail();
            String message=snail.decode(key.getText(),field.getText());
            output(message);
        }//end else if snail
        
        else if(e.getActionCommand().equals("Use Calendar Code")){
            panel4=getCalendarOptions();
            panel.add(panel4);
        }//end else if calendar code
        
        else if(e.getActionCommand().equals(which)){
            String message=getCalendarCodeMessage();
            output(message);
        }//end else which
        
        else if(e.getSource()==startOver){
            panel.removeAll();
            panel=new JPanel();
            allHold.add(panel,"Center");
            panel.add(encrypt);
            panel.add(decode);
            encrypt.setEnabled(true);
            decode.setEnabled(true);
        }//end else if startover
        
      encrypt.setText("Enrypt");
      encrypt.setText("Encrypt");
    }//end actionPerformed
    
    private JPanel setMessageText(String type){
        JPanel insert=new JPanel();
        field=new JTextField(30);
        key=new JTextField(10);
        messageText=new JButton("Next: Choose Type of "+type);
        messageText.addActionListener(this);
        
        JPanel insertSub1=new JPanel();
        insertSub1.add(new JLabel("Code: "));
        insertSub1.add(field);
        
        JPanel insertSub2=new JPanel();
        insertSub1.add(new JLabel("Key: "));
        insertSub2.add(key);
        
        insert.add(insertSub1);
        insert.add(insertSub2);
        insert.add(messageText);
        
        return insert;
    }//end setMessageText
    
    private JPanel getOptions(){
        JPanel insert=new JPanel();
        JButton button1=new JButton("Use Letter Swap");
        button1.addActionListener(this);
        JButton button2=new JButton("Use PigPen");
        button2.addActionListener(this);
        JButton button3=new JButton("Use Snail");
        button3.addActionListener(this);
        JButton button4=new JButton("Use Calendar Code");
        button4.addActionListener(this);
        insert.add(button1);
        insert.add(button2);
        insert.add(button3);
        insert.add(button4);
        return insert;
    }//end getOptions
    
    private JPanel getCalendarOptions(){
        JPanel insert=new JPanel();
        insert.add(new JLabel("Month Using: "));
        insert.add(month=new JTextField(2));
        insert.add(new JLabel("Year Using: "));
        insert.add(year=new JTextField(4));
        JButton button1=new JButton(which);
        button1.addActionListener(this);
        insert.add(button1);
        return insert;
    }//end getCalendarOptions
    
    private String getLetterSwapMessage(){
            LetterSwap swap=new LetterSwap();
            String code=field.getText();
            String keyText=key.getText();
            boolean reverse=false;
            String message="";
            if(which.equals("encrypt"))
                reverse=true;
            
            if(keyText.toCharArray().length==1)
               message=swap.decode(keyText.toCharArray()[0],code,reverse);
            else
                message=swap.decode(keyText,code,reverse);
            return message;
    }//end getLetterSwapMessage
    
    private String getCalendarCodeMessage(){
         CalendarCode cal=new CalendarCode();
            String code=field.getText();
            String keyText=key.getText();
            String part="";
            int pre=0;
            int index=0;
            int monthInt=Integer.parseInt(month.getText());
            int yearInt=Integer.parseInt(year.getText());
            String message="";
            
            boolean reverse=false;
            if(which.equals("encrypt"))
                reverse=true;
            
            if(code.indexOf(",") == -1){
                char [] codeChar=code.toCharArray();
                message=cal.decode(codeChar,monthInt,yearInt,reverse);
            }//end if code.index
            else{
                List<String> list=new ArrayList<String>();
                while(code.indexOf(",") != -1){
                    index=code.indexOf(",");
                    part=code.substring(pre,index);
                    list.add(part);
                    code=code.substring(index);
                    pre=index;
                }//end while
                String [] codeChar=list.toArray(new String[0]);
                
                message=cal.decode(codeChar,monthInt,yearInt,reverse);
            }//end else
            return message;
    }//end getCalendarCodeMessage
    private void output(String message){
        JOptionPane.showMessageDialog(null,message);
    }//end output
}
