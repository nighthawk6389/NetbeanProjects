
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package WordGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Mavis Beacon
 */
public class WordControl extends JPanel implements KeyListener,ActionListener {
   Word wordB=new Word(false,this);
    Word word=new Word(true,this);
    Word wordA=new Word(false,this); 
    String theWord="";
    JButton reset=new JButton("New Game");
    JButton go=new JButton("     Enter     ");
    JButton hintButton=new JButton("Give Me A Hint!");
    JTextField counter, hintField, totalWordsField,highField;
    List <String> list=new ArrayList<String>();
    List <String> chosenWords=new ArrayList<String>();
    List<String> names;
    List<Integer> scores;
    int lastCount=0;
    int lastCountOriginal=0;
    int guesses=0;
    int hintCounter=5;
    int wordsRight=0;
        
    public WordControl(){
        this.setLayout(new BorderLayout());
        //this.setBackground();

        JPanel title=new JPanel();
        title.setLayout(new FlowLayout());
        JLabel text=new JLabel("Dictionary Game");
        text.setFont(new Font(Font.SANS_SERIF,2,25));
        title.add(text);

        JPanel hold=new JPanel();
        hold.setLayout(new GridLayout(5,1));

        counter=new JTextField(2);
        counter.setEditable(false);
        counter.setText(0+"");
        counter.setForeground(Color.red);

        hintField=new JTextField(1);
        hintField.setEditable(false);
        hintField.setText(hintCounter+"");
        hintField.setForeground(Color.blue);

        highField=new JTextField(20);
        highField.setEditable(false);
        highField.setBackground(Color.white);

        /////////////////////
        String highText=getHighScore();
        System.out.println("HighText: "+highText);
        /////////////////////

        JPanel top=new JPanel();
        top.add(wordB);

        JPanel middle=new JPanel();
        this.setLayout(new FlowLayout());
        JLabel label=new JLabel("____");
        label.setForeground(this.getBackground());
        middle.add(label);
        middle.add(reset);
        middle.add(word);
        middle.add(go);
        middle.add(counter);
        
        reset.addActionListener(this);
        go.addActionListener(this);
        go.addKeyListener(this);
        hintButton.addActionListener(this);

        JPanel bottom=new JPanel();
        bottom.add(wordA);

        JPanel hintPanel= new JPanel();
        hintPanel.setLayout(new FlowLayout());
        hintPanel.add(new JLabel("__________________"));
        hintPanel.add(hintButton);
        hintPanel.add(hintField);
        hintPanel.add(new JLabel("________________"));
       
        chosenWords=fillList("C:/american-words.txt");
        list=fillList("C:/bigDict.txt");
        //word.setWord(getRandomWord());
        theWord=getRandomWord();
        System.out.println(theWord);
        wordB.setWord(getWord(true,word));
        wordA.setWord(getWord(false,word));
        
        JPanel south=new JPanel();
        JLabel totalWords=new JLabel("Words Right:");
        totalWordsField=new JTextField(3);
        totalWordsField.setEditable(false);
        south.add(totalWords);
        south.add(totalWordsField);

        JPanel highScore=new JPanel();
        highScore.setLayout(new GridLayout(scores.size(),1));
        JLabel titleScore=new JLabel("High Scores");
        titleScore.setFont(new Font(Font.SANS_SERIF,0,20));
        highScore.add(titleScore);
        Iterator it=names.iterator();
        Iterator it2=scores.iterator();
        while(it.hasNext() && it2.hasNext()){
            highScore.add(new JLabel(it.next()+" "+it2.hasNext()));
        }

        hold.add(top);
        hold.add(middle);
        hold.add(bottom);
        hold.add(hintPanel);
        hold.add(south);
        //hold.add(highScore);

        this.add(title,BorderLayout.NORTH);
        this.add(hold,BorderLayout.CENTER);
    }//end wordControl

    private String getWord(boolean before, Word word){
         int count=0;
         int rand=0;
         if(before)
            rand=(int)(Math.random()*(lastCount));
         else
             rand=(int)(Math.random()*(list.size()-lastCount));
         int num=lastCount;
         num= (before==true?num-rand:num+rand);
         System.out.println("Get Word"+list.get(num));
        return new String(list.get(num));
    }
    private String getRandomWord(){
        int num=0;
        while(num<5 || num>chosenWords.size())
            num=(int)(Math.random()*chosenWords.size());
        System.out.println("Num= "+num);
        String w=chosenWords.get(num);
        lastCountOriginal=num;
        lastCount=list.indexOf(w);
        if(w.contains("'") || lastCount==-1 || w.endsWith("s"))
            return getRandomWord();
        else
            return new String(w);
    }
    private boolean checkIfWord(String s){
        System.out.println("Check if Word");
        return list.contains(s);
    }

    private List<String> fillList(String fileDir){
         Scanner in=null;
         List<String>temp=new ArrayList<String>();
        try {
            in = new Scanner(new BufferedReader(new FileReader(fileDir)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordControl.class.getName()).log(Level.SEVERE, null, ex);
        }
         int count=0;
         while(in.hasNext()){
             String w=in.next();
             if(w.length()==5)
                 temp.add(w);
         }//end while
         Collections.sort(temp);
         System.out.println(temp.size());
         return temp;
    }//end fillList
    
    private void clearWord(){
        word.clearWord();
    }
    
    private void go(){
        counter.setText(""+(++guesses));
        String s=word.getWord();
        if(s.equals(theWord))
            gameOver(true);
        else if(checkIfWord(word.getWord()))
            checkPlacement(s);
        else
            displayNoWord();

    }//end go
    
    private void gameOver(boolean won){
        if(won){
            word.setAllBackground(Color.GREEN);
            word.setClearWord(true);
            word.setWord("WON!!");
            totalWordsField.setText(++wordsRight+"");
        }
        guesses=0;
        hintCounter=5;
        counter.setText(guesses+"");
        hintField.setText(hintCounter+"");
        hintButton.setEnabled(true);
        theWord=getRandomWord();
        System.out.println(theWord);
        wordB.setWord(getWord(true,word));
        wordA.setWord(getWord(false,word));
        word.setClearWord(true);
        word.setWord("     ");
    }
    
    private void checkPlacement(String s){
        System.out.println("Check placement");
        int num=s.compareTo(theWord);
        if(num<0){
            if(s.compareTo(wordB.getWord()) > 0)
            wordB.setWord(s);
        }
        else if(num>0)
            if(s.compareTo(wordA.getWord()) < 0)
                wordA.setWord(s);
        else if(num==0){
            gameOver(true);
            System.out.println("LEAK IN WON");
        }
       word.clearWord();
    }//end checkPlacement
    
    private void displayNoWord(){
        word.setAllBackground(Color.RED);
        word.setWord("     ");
        word.setClearWord(true);
     
    }

    private void getHint(){
        int which=(int)(Math.random()*3);
        if(which==0)
            word.setWord(theWord.charAt(0)+"    ");
        else if(which==1){
            int num=list.indexOf(wordB.getWord());
            num=num+(lastCount-num)/2;
            wordB.setWord(list.get(num));
        }
        else if(which==2){
            int num=list.indexOf(wordA.getWord());
            num=num-(num-lastCount)/2;
            wordA.setWord(list.get(num));
        }
        else{
            word.setWord(scramble(theWord));
        }
        hintField.setText(--hintCounter+"");
        if(hintCounter==0)
            hintButton.setEnabled(false);
    }//end getHint

    public String scramble(String normal){
        String scrambled="";
        char [] array=normal.toCharArray();
        char temp=' ';
        int rand=0;
        for(int x=0;x<array.length;x++){
            rand=(int)(Math.random()*array.length);
            temp=array[x];
            array[x]=array[rand];
            array[rand]=temp;
        }
        for(char a:array)
           scrambled+=a;
       return scrambled;
    }//end scrambled

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==reset){
            gameOver(false);
        }
        else if(e.getSource()==go){
            go();
        }
        else if(e.getSource()==hintButton)
            getHint();
    }

    private String getHighScore(){
        File file=new File("C:\\Users\\Ilan\\Documents\\NetBeansProjects\\Games\\highScore.txt");
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(WordControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(file.getAbsoluteFile()+"  Good");

        Scanner in=null;
        try {
            in = new Scanner(new BufferedReader(new FileReader(file.getAbsoluteFile())));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        names=new ArrayList<String>();
        scores=new ArrayList<Integer>();
        while(in.hasNext()){
            if(in.hasNext())
                 names.add(in.next());
            if(in.hasNext())
                 scores.add(in.nextInt());
        }

        System.out.println("before Sort");
        System.out.println(names);
        System.out.println(scores);
        int temp=0;
        String tempString="";
        for(int x=0;x<scores.size();x++){
            int maxValue=scores.get(x);
            int maxIden=x;
            for(int y=x;y<scores.size();y++){
                if(scores.get(y)>maxValue){
                    maxIden=y;
                    maxValue=scores.get(y);
                }
            }
            if(maxIden!=x){
                temp=(Integer)scores.get(maxIden);
                scores.set(maxIden, scores.get(x));
                scores.set(x, temp);
                tempString=names.get(maxIden);
                names.set(maxIden, names.get(x));
                names.set(x, tempString);
            }//end if
        }//end x
        System.out.println("after Sort");
        System.out.println(names);
        System.out.println(scores);
        return names.get(0)+" "+scores.get(0);
    }//end getHighScore

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode()==KeyEvent.VK_ENTER)
          go();
    }

    public void keyReleased(KeyEvent e) {
    }

}//end class
