package BondList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;  
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*
 * Specify.java
 *
 * Created on September 5, 2008, 4:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Specify extends JPanel implements ActionListener{
    Bond bond;
    Point points[];
    int x;
    int y;
    int years;
    double amount=0;
    double WAB=0;
    double interest;
    Dimension d;
    JLabel name;
    JButton changeYield,changeYears,changeWAB,getWorth,calculate;
    JTextField yearsField,yieldField,amountField,initialField;
    /** Creates a new instance of Specify */
    public Specify() {
        bond=null;
        x=0;
        y=0;
        years=10;
        points=null;
    }
    public Specify(Bond bond){
        this.bond=new Bond(bond);
        years=10;
        interest=bond.getYield();
        WAB=bond.getWAB();
        changeYield=new JButton("Change Yield");
        changeYield.addActionListener(this);
        //changeYears= new JButton("Change Years");
        //changeYears.addActionListener(this);
        changeWAB= new JButton("Change Worth At Buy");
        changeWAB.addActionListener(this);
        getWorth=new JButton("Get Worth in X years");
        getWorth.addActionListener(this);
        
        setLayout(new BorderLayout());
        
        JPanel panel= new JPanel();
        panel.setLayout(new FlowLayout());
        name= new JLabel(bond.getName());
        name.setFont(new Font("Times New Roman",1,30));
        panel.add(name,"North");
        panel.setBackground(Color.WHITE);
        JPanel panel2=new JPanel();
        panel2.add(changeYield);
        //panel2.add(changeYears,"East");
        panel2.add(changeWAB);
        panel2.add(getWorth);
        panel2.setBackground(Color.LIGHT_GRAY);
        
        
        add(panel,"North");
        add(panel2,"South");
    }
    private void setInterest(double interest){
        this.interest=interest;
    }
    private void setYears(int years){
        this.years=years;
    }
    private void setWAB(double WAB){
        this.WAB=WAB;
    }
    private void displayWorth(){
        JFrame frame1=new JFrame();
        frame1.setSize(400,200);
        frame1.setLocation(200,200);
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        JPanel holdWorth =new JPanel();
        holdWorth.setLayout(new GridLayout());
        
        yearsField=new JTextField(10);
        yieldField=new JTextField(10);
        yieldField.setText(new Double(bond.getYield()).toString());
        initialField=new JTextField(10);
        initialField.setText(new Integer(bond.getWAB()).toString());
        amountField=new JTextField(10);
        amountField.setEditable(false);
        amountField.setFont(new Font("Verdana",0,14));
        amountField.setForeground(Color.RED);
        calculate= new JButton("Calculate");
        calculate.addActionListener(this);
        
        JPanel leftHold =new JPanel();
        leftHold.setLayout(new GridLayout(7,1));
        leftHold.add(new JLabel("Enter the Amount of years"));
        leftHold.add(yearsField);
        leftHold.add(new JLabel("Enter the Yield(%)"));
        leftHold.add(yieldField);
        leftHold.add(new JLabel("Enter the Initial Amount"));
        leftHold.add(initialField);
        leftHold.add(calculate);
        
        JPanel rightHold = new JPanel();
        rightHold.setLayout(new GridLayout(3,1));
        JLabel labelJoe=new JLabel("     Amount Worth after Years");
        labelJoe.setHorizontalTextPosition(JLabel.CENTER);
        rightHold.add(labelJoe);
        rightHold.add(amountField);
        
        holdWorth.add(leftHold,"Center");
        holdWorth.add(rightHold,"East");
        
        frame1.getContentPane().add(holdWorth);
    }
    private void calculateWorth(){
        try{
        double yield=Double.parseDouble(yieldField.getText().trim());
        int amountOfYears=Integer.parseInt(yearsField.getText().trim());
        int initial=Integer.parseInt(initialField.getText().trim());
        
        double total=initial;
        //System.out.println("Yeild: "+yield);
        for(int z=0;z<amountOfYears;z++){
            total+=(yield/100*total);
        }
        //System.out.println(total);
        amountField.setText(new Integer((int)total).toString()+" $");
        }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Please Input Only Numbers And Fill all Fields");
            }
        
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        y=getHeight();
        x=getWidth();
        
        
        //g.drawString(bond.getName(),x/2-bond.getName().length()/2,50);
        //System.out.println((new Double(bond.getYield()).toString().length()/2));
        g.setColor(Color.black);
        g.setFont(new Font("Times New Roman",1,14));
        g.drawString("Yield = "+new Double(interest).toString()+"%",x/2-(new Double(interest).toString().length()/2)-105,80);
        g.drawString("Worth At Buy = "+new Double(WAB).toString()+"$",x/2-(new Double(WAB).toString().length()/2)+15,80);
        g.drawLine(10,y-(y/2)+20,x-10,y-(y/2)+20);
        
        amount=WAB;
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman",3,14));
        g.drawString("Years:",15,y-(y/2)+10);
        g.drawString("Worth:   ",15,y-(y/2)+40);
        g.drawLine((x/11)-10,y-(y/2)-0,(x/11)-10,y-(y/2)+40);
        g.setFont(new Font("Times New Roman",1,16));
        for(int z=1;z<years+1;z++){
            amount+=interest/100*amount;
            g.drawString(new Integer(z).toString(),z*(x/11)+(x/30),y-(y/2)+10);
            g.setColor(Color.red);
            g.drawString(new Integer((int)amount).toString()+" $",z*(x/11)+(x/30)-4*(new Integer((int)amount).toString().length()),y-(y/2)+40); 
            g.setColor(Color.black);
            g.drawLine(z*(x/11)+(x/12),y-(y/2)-0,z*(x/11)+(x/12),y-(y/2)+40);
            
        }
        //g.drawString(new Numbers((int)amount).displayNumber(),150,200);
        
    }
    public static void main(String args []){
        JFrame frame = new JFrame();
        Specify s= new Specify(new Bond());
        frame.getContentPane().add(s);
        frame.setSize(800,250);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==changeYield){
            String string= JOptionPane.showInputDialog(null,"Input New Yield(%) (Note: This will NOT change the actual yield of this bond)");
            if(string!=null){
                try{
                    double num= Double.parseDouble(string);
                    setInterest(num);
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Please Use Only Numbers");
                }
            }
        
        }
        else if(e.getSource()==changeYears){
            String string= JOptionPane.showInputDialog(null,"Input New Worth At Buy (Note: This will NOT change the actual year value of this bond)");
            if(string!=null){
                try{
                    int num= Integer.parseInt(string);
                    //System.out.println(num);
                    setYears(num);
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Please Use Only Numbers");
                }
            }
        }
        else if(e.getSource()==changeWAB){
            String string= JOptionPane.showInputDialog(null,"Input New Amount of Years (Note: This will NOT change the actual year value of this bond)");
            if(string!=null){
                try{
                    double num= Double.parseDouble(string);
                    //System.out.println(num);
                    setWAB(num);
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Please Use Only Numbers");
                }
            }
        }
        else if(e.getSource()==getWorth){
            displayWorth();
        }
        else if(e.getSource()==calculate){
            calculateWorth();
        }
        repaint();
    }
}
