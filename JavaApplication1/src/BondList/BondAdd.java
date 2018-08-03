package BondList; 

import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;
import javax.swing.*;

public class BondAdd extends JFrame implements ActionListener{
    BondControl control;
    JTextField nameField;
    JTextField companyField;
    JTextField purchasedField;
    JTextField dueField;
    JTextField yieldField;
    JTextField worthAtBuyField;
    JTextField nameLabel,companyLabel,purchasedLabel,dueLabel,yieldLabel,worthAtBuyLabel;
    JButton add;
    JButton cancel;
   public boolean finished=false;
    
    public static void main(String args []){
        BondAdd frame= new BondAdd(new BondControl());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public BondAdd(BondControl control){
        this.control=control;
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(500,200);
        this.setLocation(400,200);
        
        JPanel panel= new JPanel();
        panel.setLayout(new GridLayout(6,2));
        
        JLabel name=new JLabel("Name");
        JLabel company= new JLabel("Company");
        JLabel purchased= new JLabel("Date of Purchase -- mm/dd/yyyy");
        JLabel due= new JLabel("Date Due -- mm/dd/yyyy");
        JLabel yield= new JLabel("Yield(%)");
        JLabel worthAtBuy = new JLabel("Worth At Buy (Has to be Whole Number)");
        
         nameField= new JTextField(10);
         companyField= new JTextField(10);
         purchasedField= new JTextField(15);
         dueField= new JTextField(15);
         yieldField= new JTextField(10);
         worthAtBuyField= new JTextField(10);
         /*
         nameField.addMouseListener(this);
         companyField.addMouseListener(this);
         purchasedField.addMouseListener(this);
         dueField.addMouseListener(this);
         yieldField.addMouseListener(this);
         worthAtBuyField.addMouseListener(this);
          **/
        
         nameField.setText("bond");
         companyField.setText("Company");
         
        add= new JButton("Add");
        cancel= new JButton("Clear");
        
        panel.add(name);
        panel.add(nameField);
        panel.add(company);
        panel.add(companyField);
        panel.add(purchased);
        panel.add(purchasedField);
        panel.add(due);
        panel.add(dueField);
        panel.add(yield);
        panel.add(yieldField);
        panel.add(worthAtBuy);
        panel.add(worthAtBuyField);
        
        JPanel panel3=new JPanel();
        panel3.setLayout(new GridLayout(6,1));
        
        panel3.add(nameLabel=new JTextField(3));
        nameLabel.setEditable(false);
        nameLabel.setForeground(Color.RED);
        
        panel3.add(companyLabel=new JTextField(3));
        companyLabel.setEditable(false);
        companyLabel.setForeground(Color.RED);
        
        panel3.add(purchasedLabel=new JTextField(3));
        purchasedLabel.setEditable(false);
        purchasedLabel.setForeground(Color.RED);
        
        panel3.add(dueLabel=new JTextField(3));
        dueLabel.setEditable(false);
        dueLabel.setForeground(Color.RED);
        
        panel3.add(yieldLabel=new JTextField(3));
        yieldLabel.setEditable(false);
        yieldLabel.setForeground(Color.RED);
        
        panel3.add(worthAtBuyLabel=new JTextField(3));
        worthAtBuyLabel.setEditable(false);
        worthAtBuyLabel.setForeground(Color.RED);
        
        JPanel panel2=new JPanel();
        panel2.add(add);
        panel2.add(cancel);
        add.addActionListener(this);
        cancel.addActionListener(this);
        
        JPanel panelWhole=new JPanel();
        panelWhole.setLayout(new BorderLayout());
        panelWhole.add(panel,"Center");
        panelWhole.add(panel2,"South");
        panelWhole.add(panel3,"East");
        getContentPane().add(panelWhole);
    }
    public Bond returnBond(){
        try{
         Bond send= new Bond(
                    nameField.getText().trim(),
                    companyField.getText().trim(),
                    new DateOf().extract(purchasedField.getText().trim()),
                    new DateOf().extract(dueField.getText().trim()),
                    Double.parseDouble(yieldField.getText().trim()),
                    Integer.parseInt(worthAtBuyField.getText().trim())
            );
            
        
         JOptionPane.showMessageDialog(null,"Your Bond Was Added Succesfully");
         return send;
        }
        catch(NumberFormatException n){
               JOptionPane.showMessageDialog(null,"Please Input a Number In The Correct Format");
           }
         catch(NoSuchElementException n){
             JOptionPane.showMessageDialog(null,"Please Input correct Date");
         }
        
        return null;
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==add){
            if(showIfValidated())
                control.actionAdd(returnBond());
           
        }
        else if(e.getSource()==cancel){
            nameField.setText("");
            companyField.setText("");
            purchasedField.setText("");
            dueField.setText("");
            yieldField.setText("");
            worthAtBuyField.setText("");
        }
        
    }
    private boolean showIfValidated(){
        boolean isGood=true;
            System.out.println(nameField.getText());
            if(nameField.getText().trim().length() <2 || nameField.getText().trim() ==" " || nameField.getText().trim() ==null){
                System.out.println("In Bad Name");
                nameLabel.setText("BAD");
                isGood=false;
            }
            else
                nameLabel.setText("Good");
            
            if(companyField.getText().trim().length() < 1){
                companyLabel.setText("Bad");
                 isGood=false;
            }
            else
                companyLabel.setText("Good");
            try{
            if(new DateOf().extract(purchasedField.getText().trim())==null){
                    purchasedLabel.setText("Bad");
                 isGood=false;
            }
            else
                purchasedLabel.setText("Good");
            }
            catch(NoSuchElementException ex){
                JOptionPane.showMessageDialog(null,"Please Enter The PURCHASED Date in dd/mm/yyyy format(ex. 06/03/1989)");
                purchasedLabel.setText("Bad");
                 isGood=false;
            }
            catch(NumberFormatException n){
               JOptionPane.showMessageDialog(null,"Please Enter The PURCHASED Date in dd/mm/yyyy format(ex. 06/03/1989)");
               purchasedLabel.setText("Bad");
                 isGood=false;
           }
            
            try{
            if(new DateOf().extract(dueField.getText().trim())==null){
                dueLabel.setText("Bad");
                isGood=false;
            }
            else
                dueLabel.setText("Good");
            }
            catch(NoSuchElementException ex){
                JOptionPane.showMessageDialog(null,"Please Enter The DUE Date in dd/mm/yyyy format(ex. 06/03/1989)");
                dueLabel.setText("Bad");
                 isGood=false;
            }
            catch(NumberFormatException n){
               JOptionPane.showMessageDialog(null,"Please Enter The DUE Date in dd/mm/yyyy format(ex. 06/03/1989)");
               dueLabel.setText("Bad");
                 isGood=false;
           }
            
            if(yieldField.getText().trim().length()<1){
                yieldLabel.setText("Bad");
                 isGood=false;
            }
            else
                yieldLabel.setText("Good");
      
            if(worthAtBuyField.getText().trim().length()<1){
                worthAtBuyLabel.setText("Bad");
                 isGood=false;
            }
            else
                worthAtBuyLabel.setText("Good");
            return isGood;
    }
}
