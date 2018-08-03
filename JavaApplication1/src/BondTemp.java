import java.io.Serializable;
import java.util.Calendar;

/*
 * Bond.java
 *
 * Created on September 26, 2008, 12:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Bond implements Serializable{
    String name; 
    String company;
    DateOf purchased;
    DateOf due;
    DateOf called;
    double yield;
    int worthAtBuy;
    int worthAtSell;
    int worthNow;
    static int order[]=new int[9];
    static boolean made=false;
    
    /** Creates a new instance of Bond */
    public Bond() {
        name="Default";
        company="Default";
        purchased= new DateOf(6,3,89);
        due= new DateOf(6,3,89);
        called=new DateOf(6,3,89);
        yield=0;
        worthAtBuy=1;
        worthAtSell=2;
        worthNow=3;
        if(!made)
            populateOrder();
    }
    public Bond(String name, String company, DateOf purchased, DateOf due, double yield, int worthAtBuy){
        this.name=name; 
        this.company=company;
        this.purchased=purchased;
        this.due= due;
        this.called=new DateOf();
        this.yield=yield;
        this.worthAtBuy=worthAtBuy;
        this.worthAtSell=-1;
        this.worthNow=worthAtBuy;
        //System.out.println("ARRAY WORTH: "+this.worthNow);
        if(!made)
            populateOrder();
    }
    public Bond(Bond bond){
        this.name=bond.getName() ;
        this.company=bond.getCompany();
        this.purchased=bond.getPurchased();
        this.due= bond.getDue();
        this.called=bond.getCalled();
        this.yield=bond.getYield();
        this.worthAtBuy=bond.getWAB();
        this.worthAtSell=bond.getWAS();
        this.worthNow=bond.getWN();
        if(!made)
            populateOrder();
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getCompany(){
        return company;
    }
    public void setCompany(String company){
        this.company=company;
    }
    public DateOf getPurchased(){
        return purchased;
    }
    public void setPurchased(DateOf purchased){
        this.purchased=purchased;
    }
    public DateOf getDue(){
        return due;
    }
    public void setDue(DateOf due){
        this.due=due;
    }
    public DateOf getCalled(){
        return called;
    }
    public void setDateOfCalled(DateOf called){
        this.called=called;
    }
    public double getYield(){
        return yield;
    }
    public void setYield(double yield){
        this.yield=yield;
    }
    public int getWAB(){
        return worthAtBuy;
    }
    public void setWorthAtBuy(int worthAtBuy){
        this.worthAtBuy=worthAtBuy;
    }
    public int getWAS(){
        return worthAtSell;
    }
    public void setWorthAtSell(int worthAtSell){
        this.worthAtSell=worthAtSell;
    }
    public int getWN(){
        changeWorthNow();
        return worthNow;
    }
    public void setWorthNow(int worthNow){
        this.worthNow=worthNow;
    }
    public void populateOrder(){
        if(!made){
            System.out.println("in populate");
            for(int x=0;x<9;x++){
                order[x]=x;
            }
            made=true;
        }
    }
    public static boolean setOrder(int oldNumber,int newNumber){
        System.out.println("Set");
        int temp=order[oldNumber];
        order[oldNumber]=order[newNumber];
        order[newNumber]=temp;
        return true;
    }
    private void changeWorthNow(){
        DateOf today=new DateOf(Calendar.getInstance());
        
        today.add(DateOf.YEAR,1);
        today.setDate(today.month+1,today.day,today.year);
        //System.out.println("After "+today);
        //System.out.println("purchased: "+purchased);
        int greater=today.getYear()-purchased.getYear();
        //System.out.println("greater: "+greater);
        if(greater>0)
            if(today.month<purchased.month){
                    greater--;
            }
            else if(today.month==purchased.month){
                if(today.day<purchased.day)
                    greater--;
            }
        //System.out.println("greaterAfter: "+greater);
        double amount=worthAtBuy;
        for(int x=0;x<greater;x++){
            amount+=yield/100*amount;
        }
        //System.out.println("Amount: "+amount);
        setWorthNow((int)amount);
    }
    public Object[] toArray(){
        
        Object []array= new Object[9];
        System.out.println("To Array "+order[0]); 
        array[order[0]]=this.getName();
        array[order[1]]=this.getCompany();
        array[order[2]]=this.getPurchased();
        array[order[3]]=this.getDue();
        array[order[4]]=this.getCalled();
        array[order[5]]=this.getYield();
        array[order[6]]=this.getWAB();
        array[order[7]]=this.getWAS();
        array[order[8]]=this.getWN();
        
        return array;
    }
}

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
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.swing.*;
/*
 * BondControl.java
 *
 * Created on August 31, 2008, 3:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Ilan Elkobi
 */
public class BondControl extends JPanel implements ActionListener, ItemListener, ListSelectionListener, MouseListener{
    JButton sortButton = new JButton("Sort");
    JButton addBond= new JButton("Add a Bond");
    JButton editBond = new JButton("Edit Bond");
    JButton deleteBond= new JButton("Delete a Bond");
    JTextArea area = new JTextArea();
    JComboBox box=  new JComboBox();
    JComboBox box1=  new JComboBox();
    JComboBox box2=  new JComboBox();
    JTextField field = new JTextField(20);
    JMenuItem save,open,changeOrder,saveExcel;
    Bond array[];
    String names[];
    JList list;
    final int amountPer=9;
    DefaultListSelectionModel model= new DefaultListSelectionModel();
    JFileChooser chooser= new JFileChooser();
    JLabel label;
    
    /** Creates a new instance of BondControl */
    public static void main(String args []){
        JFrame frame = new JFrame();
        frame.add(new BondControl());
        frame.setVisible(true);
        frame.setSize(870,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public BondControl() {
        setLayout(new BorderLayout());
         
        names=new String[amountPer];
        names[0]="Name";
        names[1]="Company";
        names[2]="Purchased";
        names[3]="Due";
        names[4]="Called";
        names[5]="Yield";
        names[6]="WorthAtBuy";
        names[7]="WorthAtSell";
        names[8]="WorthNow";
        
        label= new JLabel("   "+names[0]+"                          "+names[1]+"          "+names[2]+"                  "+names[3]+"                   "+names[4]+"                     "+names[5]+"(%)                   "+names[6]+"            "+names[7]+"       "+names[8]);
        label.setForeground(Color.RED);
        
        array= new Bond[3];
        array[2]= new Bond("First Bond","First Company",new DateOf(7,3,1989),new DateOf(5,3,1979),5,10);
        array[1]= new Bond("Second Bond","Second Company",new DateOf(6,3,1989),new DateOf(9,4,1979),500,100);
        array[0]= new Bond("Third Bond","Third Company",new DateOf(6,4,1989),new DateOf(5,4,1989),50,1);
        System.out.println("ARRAY WORTH in Control: "+array[0].getWN());
        
        list= new JList(array);
        list.setBackground(Color.WHITE);
        list.setLayout(new GridLayout(10,9,5,5));
        list.setLayoutOrientation(list.HORIZONTAL_WRAP);
        list.setVisibleRowCount(3);           
        list.setFixedCellWidth(100);
        list.setBorder(new LineBorder(Color.BLACK,3));   
        
        box.addItem(("Name"));
        box.addItem(("Company"));  
        box.addItem(("Purchased"));  
        box.addItem(("Due"));
        box.addItem(("Called")); 
        box.addItem(("Yield"));      
        box.addItem(("WorthAtBuy"));
        box.addItem(("WorthAtSell"));
        box.addItem(("WorthNow"));
        box.addItemListener(this);
        sortButton.addActionListener(this);
        addBond.addActionListener(this);
        editBond.addActionListener(this);
        deleteBond.addActionListener(this);
        field.addActionListener(this);
        field.setFont(new Font("Verdana",1,10));
        field.setForeground(Color.RED);
        list.addListSelectionListener(this);
        list.addMouseListener(this);
        //list.addMouseMotionListener(this);
       
        
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        save=new JMenuItem("Save");
        saveExcel=new JMenuItem("Save In Excel Format");
        open= new JMenuItem("Open");
        changeOrder= new JMenuItem("Change Order");
        menu.add(save);
        menu.add(saveExcel);
        menu.add(open);
        menu.add(changeOrder);
        bar.add(menu); 
        bar.setBorderPainted(true);
        save.addActionListener(this);
        open.addActionListener(this);
        saveExcel.addActionListener(this);
        changeOrder.addActionListener(this);
  
        JPanel panel1= new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.add(label,"North");   
        panel1.add(list,BorderLayout.CENTER); 
        
        
        JPanel panel2= new JPanel();
        panel2.setLayout(new FlowLayout());
        field.setEditable(false);   
        panel2.add(addBond);
        panel2.add(editBond);
        panel2.add(deleteBond);
        panel2.add(new JLabel("                      "));
        panel2.add(sortButton);
        panel2.add(box);
        panel2.add(new JLabel("                      "));
        panel2.add(field);
        
        JPanel panel3= new JPanel();
        panel3.setLayout(new GridLayout(1,2));
        panel3.add(bar);
        
        chooser.setCurrentDirectory(new File("."));
        
        add(panel1,BorderLayout.CENTER);
        add(panel2,BorderLayout.SOUTH);
        add(panel3,BorderLayout.NORTH);
        
        displayArray();
        
    }

    public void actionPerformed(ActionEvent e) throws NoSuchElementException{
        field.setText(" ");
        System.out.println("preformed");
        if(e.getSource()==sortButton){
           actionSort();
        }
        else if(e.getSource()==addBond){
            BondAdd add= new BondAdd(this);
        }
        else if(e.getSource()==editBond){
           actionEdit();
        }
        else if(e.getSource()==deleteBond){
            actionDelete();
        }
        else if(e.getSource()==changeOrder){
            newOrderPanel();
        }
        else if(e.getSource()==open){
            openFile();
        }
        else if(e.getSource()==save){
            saveFile();
        }
        else if(e.getSource()==saveExcel){
            saveToExcel();
        }
        else if(e.getActionCommand()=="Change Order"){
            setNewOrder();
        }
       
        displayArray();
    }
    
    private void displayArray(){
        list.setVisibleRowCount(array.length);
        
        Object toArray[]=new Object[array.length*amountPer];
        System.out.println("display"+ array.length);
        Object tempArray[]=new Object[9];  
        int index=0;
        for(int x=0;x<array.length;x++){
            tempArray=array[x].toArray();
            for(int y=0;y<tempArray.length;y++){
                toArray[index]=tempArray[y];
                //System.out.println(toArray[index]+" "+tempArra)
                index++;
                
            }
        }  
        list.setListData(toArray);
      
    }
     private void openFile(){
        if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
            open(chooser.getSelectedFile());
    }
     
    private void open(File file){
        String string;
        ObjectInputStream reader=null;
        try{
             reader = new ObjectInputStream(new FileInputStream(file));
             array=((Bond [])reader.readObject());
             displayArray();
        }
        catch(FileNotFoundException ex){
            System.out.println("No file");
        }
        catch(IOException ex){
            System.out.println("You suck"+ex.toString());
        }
        catch(ClassNotFoundException ex){
            System.out.println("Class not found");
        }
        finally{
            try{
                if(reader!=null)
                    reader.close();
            }
            catch(IOException ex){
                System.out.println("Problem with Closing");
            }
        }
    }
    private void saveFile(){
        if(chooser.showSaveDialog(this)==chooser.APPROVE_OPTION){
            save(chooser.getSelectedFile()); 
            
        }
    }
    private void save(File file){
        ObjectOutputStream writer =null;
        try{
        writer=new ObjectOutputStream(new FileOutputStream(file));
        writer.writeObject(array);  
        }
        catch(IOException ex){
            System.out.println("Problem Save "+ ex);
        }
        finally{
            try{
                if(writer!=null)
                    writer.close();
            }
            catch(IOException ex){
                System.out.println("Problem Closing");
            }
        }
        
    }
    private void saveToExcel(){
        File file=null;
        
        if(chooser.showSaveDialog(this)==chooser.APPROVE_OPTION){
            file=chooser.getSelectedFile();
        }
        
        try{
        SaveToExcel j = new SaveToExcel();
        j.write(file.toString()+".xml",array);
        }     
        catch(Exception e) {e.printStackTrace();}
    }
    private void actionSort(){
        System.out.println("action");
            String type= (String) box.getSelectedItem();
            try{  
            if(type.equals("Name"))
                sortName();
            else if(type.equals("Company"))
                sortCompany();
            else if(type.equals("Purchased"))
                sortPurchased();
            else if(type.equals("Due"))
                sortDue(); 
            else if(type.equals("Called"))
                sortCalled();
            else if(type.equals("Yield"))
                sortYield();
            else if(type.equals("WorthAtBuy"))
                sortWAB();
            else if(type.equals("WorthAtSell"))
                sortWAS();
            else if(type.equals("WorthNow"))
                sortWN();       
            else
                field.setText("Did Not Sort");
            }catch(NullPointerException ex){
                field.setText("Cannot Sort");
            }
    }
    public void actionAdd(Bond add){ 
            try{
            Bond newBond = new Bond(add);
            
            Bond tempArray[]= new Bond[array.length+1];
            tempArray=array.clone();
            array= new Bond[array.length+1];
            for(int x=0;x<tempArray.length;x++){
                array[x]=tempArray[x];
            }
            array[array.length-1]=newBond;
            System.out.println(array.length+" length");
            displayArray();  
            }
            catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Please Use Proper Format For Numbers");    
            }
            catch(NoSuchElementException ex){
                JOptionPane.showMessageDialog(null,"Please Input into All Fields");
            }
            catch(NullPointerException ex){
                JOptionPane.showMessageDialog(null,"Please Input into All Fields");
            }
    }

    private void actionEdit(){
         try{
             int index=0;
             if(list.getSelectedIndices().length==1)
                index=list.getSelectedIndices()[0];
             else
                 index=list.getSelectedIndices()[1];
            int numBond=index/9;
            Bond bond= (array[numBond]);
            System.out.println(index+" index");
           String type=(String) box.getSelectedItem();
           String newType=JOptionPane.showInputDialog(null,"Please input information for a new "+names[index%9]+" Of "+bond.getName());
           System.out.println(newType);
           if(newType==null)
                field.setText("Edit Canceled");
            else if(index%9==0)
                bond.setName(newType);
            else if(index%9==1)
                bond.setCompany(newType);
            else if(index%9==2)
                bond.setPurchased(new DateOf().extract(newType));
            else if(index%9==3)
                bond.setDue(new DateOf().extract(newType));
            else if(index%9==4)
                bond.setDateOfCalled(new DateOf().extract(newType));
            else if(index%9==5)
                bond.setYield(Double.parseDouble(newType));
            else if(index%9==6)
                bond.setWorthAtBuy(Integer.parseInt(newType));
            else if(index%9==7)
                bond.setWorthAtSell(Integer.parseInt(newType));
            else if(index%9==8)
                bond.setWorthNow(Integer.parseInt(type));
            else
                JOptionPane.showMessageDialog(null,"Error: Did not Edit. Please try again or contact Ilan");
           if(newType != null)
           JOptionPane.showMessageDialog(null,"Edit Successful!");
           array[numBond]= new Bond(bond); 
           }
           catch(NullPointerException n){
               JOptionPane.showMessageDialog(null,"Please Select A Field(ex. Name, Company) To Change");
           }
           catch(ArrayIndexOutOfBoundsException n){
               JOptionPane.showMessageDialog(null,"Please Select A Field(ex. Name, Company) To Change");
           }
           catch(NumberFormatException n){
               JOptionPane.showMessageDialog(null,"Please Input a Number In The Correct Format");
               field.setText("Please Input Number, Not Text");
           }
         catch(NoSuchElementException n){
             JOptionPane.showMessageDialog(null,"Please Input correct Date");
         }
    }
    public void actionDelete() throws ArrayIndexOutOfBoundsException{
       try{
           if(list.getSelectedIndex()==-1)
               throw new ArrayIndexOutOfBoundsException();
            int index=list.getSelectedIndex()/9;
            
            int answer=JOptionPane.showConfirmDialog(null,"Are you sure you want to delete "+array[index].getName()+"?"); 
        if(answer==0){
            System.out.println("True");
            deleteFrom(index);
        }
        else if(answer==2){
            System.out.println("Falsee");
        }
        else{
            System.out.println("Another");
        }
            }
            catch(ArrayIndexOutOfBoundsException e){
               JOptionPane.showMessageDialog(null,"Please select a Bond to delete");
            }
    }
    public void deleteFrom(int index){
        System.out.println(index);
        Bond temp[]= new Bond[array.length];
        temp=array.clone();
        array= new Bond[array.length-1];
        int value=0;
        for(int x=0;x<temp.length;x++){
            if(x==index)
                continue;
            System.out.println(value+" "+x);
            array[value++]=temp[x];
        }
        displayArray();
    }
    public void newOrderPanel(){
        System.out.println("In New Order");
        JFrame frame1 = new JFrame();
        frame1.setVisible(true);
        frame1.setSize(400,80);    
        frame1.setLocation(getWidth()/2,getHeight()/2);
        frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        JPanel choose= new JPanel();
       
         box1= new JComboBox();
         box2= new JComboBox();
        JButton doIt=new JButton("Change Order");
        
        box1.addItem(("Name"));
        box1.addItem(("Company"));  
        box1.addItem(("Purchased"));  
        box1.addItem(("Due"));
        box1.addItem(("Called"));
        box1.addItem(("Yield"));
        box1.addItem(("WorthAtBuy"));
        box1.addItem(("WorthAtSell"));
        box1.addItem(("WorthNow"));
        
        box2.addItem(("Name"));
        box2.addItem(("Company"));  
        box2.addItem(("Purchased"));  
        box2.addItem(("Due"));
        box2.addItem(("Called"));
        box2.addItem(("Yield"));
        box2.addItem(("WorthAtBuy"));
        box2.addItem(("WorthAtSell"));
        box2.addItem(("WorthNow"));
        
        box1.addItemListener(this);
        box2.addItemListener(this);
        doIt.addActionListener(this);
        
        choose.add(new JLabel("Switch"));
        choose.add(box1);
        choose.add(box2);
        choose.add(doIt);
        frame1.getContentPane().add(choose);
    }
    public void setNewOrder(){
        int oldNumber=box1.getSelectedIndex();
        int newNumber=box2.getSelectedIndex();
        Bond.setOrder(oldNumber,newNumber);
        
        String temp=names[oldNumber];
        names[oldNumber]=names[newNumber];
        names[newNumber]=temp;
        label.setText("   "+names[0]+"                          "+names[1]+"          "+names[2]+"                  "+names[3]+"                   "+names[4]+"                     "+names[5]+"                   "+names[6]+"            "+names[7]+"       "+names[8]);
        displayArray();
    }
    public void sortName(){
        String lowest;
        int index=0;;
        Bond temp;
        for(int x=0; x<array.length;x++){
           lowest=(String)(array[x]).getName();
            for(int y=x;y<array.length;y++){
                if(((String)(array[y]).getName()).compareToIgnoreCase(lowest)<0){
                    lowest=array[y].getName();
                    index=y;
                }         
            }
           if(lowest.compareToIgnoreCase(array[x].getName())<0){
               temp=new Bond(array[x]);
               array[x]=new Bond(array[index]);
               array[index]=new Bond(temp);
           }
        }
    }
     public void sortCompany(){
        Bond temp= new Bond();
        String lowest=(String)(array[0].getCompany());
        int index=0;
        
        for(int x=0; x<array.length;x++){
           lowest=(String)(array[x]).getCompany();
            for(int y=x;y<array.length;y++){
                if(((String)(array[y]).getCompany()).compareToIgnoreCase(lowest)<0){
                    lowest=array[y].getCompany();
                    index=y;
                }         
            }
           if(lowest.compareToIgnoreCase(array[x].getCompany())<0){
               temp=new Bond(array[x]);
               array[x]=new Bond(array[index]);
               array[index]=new Bond(temp);
           }
        }
    }
     public void sortPurchased(){
        Bond temp= new Bond();
        DateOf lowest=(array[0].getPurchased());
        int index=0;
        
        for(int x=0; x<array.length;x++){
           lowest=(array[x]).getPurchased();
            for(int y=x;y<array.length;y++){
                if((array[y]).getPurchased().compareTo(lowest)<0){
                    lowest=array[y].getPurchased();
                    index=y;
                }         
            }
           if(lowest.compareTo(array[x].getPurchased())<0){
               temp=new Bond(array[x]);
               array[x]=new Bond(array[index]);
               array[index]=new Bond(temp);
           }
        }
    }
     public void sortDue(){
        Bond temp= new Bond();
        DateOf lowest=(array[0].getDue());
        int index=0;
        
        for(int x=0; x<array.length;x++){
           lowest=(array[x]).getDue();
            for(int y=x;y<array.length;y++){
                if((array[y]).getDue().compareTo(lowest)<0){
                    lowest=array[y].getDue();
                    index=y;
                }         
            }
           if(lowest.compareTo(array[x].getDue())<0){
               temp=new Bond(array[x]);
               array[x]=new Bond(array[index]);
               array[index]=new Bond(temp);
           }
        }
    }
    public void sortCalled(){
        
        Bond temp= new Bond();
        DateOf lowest=(array[0].getCalled());
        int index=0;
        
        for(int x=0; x<array.length;x++){
           lowest=(array[x]).getCalled();
            for(int y=x;y<array.length;y++){
                if((array[y]).getCalled().compareTo(lowest)<0){
                    lowest=array[y].getCalled();
                    index=y;
                }         
            }
           if(lowest.compareTo(array[x].getCalled())<0){
               temp=new Bond(array[x]);
               array[x]=new Bond(array[index]);  
               array[index]=new Bond(temp);
           }
        }
    }
    public void sortYield(){
        Bond temp= new Bond();
        double lowest=(array[0].getYield());
        int index=0;
        
        for(int x=0; x<array.length;x++){
           lowest=(array[x]).getYield();
            for(int y=x;y<array.length;y++){
                if(((Double)(array[y]).getYield()).compareTo((Double)lowest)<0){
                    lowest=array[y].getYield();
                    index=y;
                }         
            }
           if(((Double)(lowest)).compareTo((Double)(array[x].getYield()))<0){
               temp=new Bond(array[x]);
               array[x]=new Bond(array[index]);
               array[index]=new Bond(temp);
           }
        }
    }
    public void sortWAB(){
        Bond temp= new Bond();
        int lowest=(array[0].getWAB());
        int index=0;
        
        for(int x=0; x<array.length;x++){
           lowest=(array[x]).getWAB();
            for(int y=x;y<array.length;y++){
                if(((Integer)(array[y]).getWAB()).compareTo((Integer)lowest)<0){
                    lowest=array[y].getWAB();
                    index=y;
                }         
            }
           if(((Integer)(lowest)).compareTo((Integer)(array[x].getWAB()))<0){
               temp=new Bond(array[x]);
               array[x]=new Bond(array[index]);
               array[index]=new Bond(temp);
           }
        }
    }
    public void sortWAS(){
        Bond temp= new Bond();
        int lowest=(array[0].getWAS());
        int index=0;
        
        for(int x=0; x<array.length;x++){
           lowest=(array[x]).getWAS();
            for(int y=x;y<array.length;y++){
                if(((Integer)(array[y]).getWAS()).compareTo((Integer)lowest)<0){
                    lowest=array[y].getWAS();
                    index=y;
                }         
            }
           if(((Integer)(lowest)).compareTo((Integer)(array[x].getWAS()))<0){
               temp=new Bond(array[x]);
               array[x]=new Bond(array[index]);
               array[index]=new Bond(temp);
           }
        }
    }
    public void sortWN(){
        Bond temp= new Bond();
        int lowest=(array[0].getWN());
        int index=0;
        
        for(int x=0; x<array.length;x++){
           lowest=(array[x]).getWN();
            for(int y=x;y<array.length;y++){
                if(((Integer)(array[y]).getWN()).compareTo((Integer)lowest)<0){
                    lowest=array[y].getWN();
                    index=y;
                }         
            }
           if(((Integer)(lowest)).compareTo((Integer)(array[x].getWN()))<0){
               temp=new Bond(array[x]);
               array[x]=new Bond(array[index]);
               array[index]=new Bond(temp);
           }
        }
    }

    public void itemStateChanged(ItemEvent e) {
    }
    public void valueChanged(ListSelectionEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        int row= ((int)list.getSelectedIndex()/9)*9;
        int rowArray[]={list.getSelectedIndex(),row};
        
        int index= (list.getSelectedIndices().length==1)?0 :1;
        if(list.getSelectedValues()[index] != null)
                    field.setText(list.getSelectedValues()[index].toString());
            
        if(e.getClickCount()==2){
            Bond bond= new Bond(array[(list.getSelectedIndex()/9)]);
        System.out.println(bond.getYield()+" "+"yield");    
        JFrame frame = new JFrame();
        Specify s= new Specify(bond);
        frame.getContentPane().add(s);
        frame.setSize(800,250);
        frame.setVisible(true);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }  

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
       
    }

    public void mouseExited(MouseEvent e) {
    }  
    
}

 
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
import java.io.Serializable;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
/*
 * DateOf.java
 *
 * Created on August 31, 2008, 1:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class DateOf implements Serializable {
    int month,day,year;
    static int DAY=1;
    static int MONTH=2;
    static int YEAR=3;
    /** Creates a new instance of DateOf */
    public DateOf() {
        month=day=year=00;
    }
    public DateOf(int month, int day, int year){
        this.month=month;
        this.day=day;
        this.year=year;
    }
    public DateOf(Calendar cal){
        this.month=cal.get(cal.MONTH);
        this.day=cal.get(cal.DAY_OF_MONTH);
        this.year=cal.get(Calendar.YEAR);
    } 
    public void setDate(int month, int day, int year){
        if(validateDate(month,day,year)){
        this.month= month;
        this.day=day;
        this.year=year;
        }
    }
    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }
    public int getYear(){
        return year;
    }
    public String toString(){
        return new String(month+"/"+day+"/"+year);
        
    }
    public int compareTo(DateOf date2){
     
      Integer monthInt= new Integer(month);
      Integer dayInt= new Integer(day);
      Integer yearInt= new Integer(year);
      
      Integer monthInt2= new Integer(date2.month);
      Integer dayInt2= new Integer(date2.day);
      Integer yearInt2= new Integer(date2.year);
      
      if(yearInt.equals(yearInt2)){
          if(monthInt.equals(monthInt2)){
              if(dayInt.equals(dayInt2)){
                  return 0;
              }
              else{
                  return dayInt.compareTo(dayInt2);
              }
          }
          else{
              return monthInt.compareTo(monthInt2);
          }
      }
      else{
          return yearInt.compareTo(yearInt2);
      }
    }
    public DateOf extract(String old){
        StringTokenizer token= new StringTokenizer(old,"/");
        int day;
        int month;
        int year;
        
        month=Integer.parseInt(token.nextToken());
        day=Integer.parseInt(token.nextToken());
        year=Integer.parseInt(token.nextToken());
        
        if(!validateDate(month,day,year))
            throw new NoSuchElementException();
        return new DateOf(month,day,year);
        
    }
    public boolean validateDate(int month, int day, int year){
        Integer monthLen=new Integer(month);
        if(monthLen.toString().length() > 2){
            JOptionPane.showMessageDialog(null,"The month must be entered in mm format(ex. 06 for June, 10 for September");
            return false;
        }
        
        Integer dayLen=new Integer(day);
        if(dayLen.toString().length() > 2){
            JOptionPane.showMessageDialog(null,"The day must be entered in dd format(ex. 06 for the 6th, 25 for the 25th");
            return false;
        }
        
        Integer yearLen=new Integer(year);
        if(yearLen.toString().length() != 4){
            JOptionPane.showMessageDialog(null,"The year must be entered in yyyy format(ex. 1989,2008");
            return false;
        }
        
        return true;
    }

    public void add(int field, int amount) {
        if(field==DateOf.DAY){
            this.day=this.day+amount;
        }
        else if(field==DateOf.MONTH){
            this.month=this.month+amount;
        }
        else if(field==DateOf.YEAR){
            this.year=this.year+amount;
        }
    }
}
import java.io.*;
import java.util.HashMap;
import org.dom4j.*;
/*
 * SaveToExcel.java
 *
 * Created on May 7, 2009, 5:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class SaveToExcel {
    private File outputFile=null;
    /** Creates a new instance of SaveToExcel */
    public SaveToExcel() {
    }
    
    public static void main(String[] args) {
       try{
           SaveToExcel j = new SaveToExcel();
           j.write("C:\\JavaExcel.xml");
       } catch(Exception e) {e.printStackTrace();}
    }

    
    public void write(String file) throws Exception{
        outputFile=new File(file);
        Document doc=DocumentHelper.createDocument();
        HashMap pmap= new HashMap();
        pmap.put("progid","Excel.sheet");
        doc.addProcessingInstruction("mso-application",pmap);
        doc.add(this.createElements());
        FileWriter out= new FileWriter(outputFile);
        doc.write(out);
        out.flush();
        out.close();
    }
    public void write(String file, Bond [] bond) throws Exception{
        outputFile=new File(file);
        Document doc=DocumentHelper.createDocument();
        HashMap pmap= new HashMap();
        pmap.put("progid","Excel.sheet");
        doc.addProcessingInstruction("mso-application",pmap);
        doc.add(this.createElements(bond));
        FileWriter out= new FileWriter(outputFile);
        doc.write(out);
        out.flush();
        out.close();
    }
    //This function creates the root of the SpreadsheetML and
    //populates it with necesary elements.
    private Element createElements() throws Exception {
        //Create all Elements
        Element workbook = this.createWorkbook();
        Element worksheet = this.createWorksheet("TestSheet");
        workbook.add(worksheet);
        Element table = this.createTable();
        worksheet.add(table);
        Element row1 = this.createRow("1");
        table.add(row1);
        Element cellA1 = this.createCell();
        row1.add(cellA1);
        Element dataA1 = this.createData("Number","121");
        cellA1.add(dataA1);
        Element row2 = this.createRow("2");
        table.add(row2);
        Element cellA2 = this.createCell();
        row2.add(cellA2);
        Element dataA2 = this.createData("String","121 is 11*11");
        cellA2.add(dataA2);
        return workbook;
    }
    private Element createElements(Bond [] bond) throws Exception {
        Element workbook = this.createWorkbook();
        Element worksheet = this.createWorksheet("List Of Bonds");
        workbook.add(worksheet);
        Element table = this.createTable();
        worksheet.add(table);
        
        Element [] row=new Element[bond.length];
        Element cell [][]=new Element[bond.length][9];
        
        for(int x=0;x<bond.length;x++){
        row[x] = this.createRow(new Integer(x).toString());
        table.add(row[x]);
        
        cell[x][0] = this.createCell();
        row[x].add(cell[x][0]);
        Element dataA1 = this.createData("String",bond[x].getName());
        cell[x][0].add(dataA1);
        
        cell[x][1] = this.createCell();
        row[x].add(cell[x][1]);
        Element dataA2 = this.createData("String",bond[x].getCompany());
        cell[x][1].add(dataA2);
        
        cell[x][2] = this.createCell();
        row[x].add(cell[x][2]);
        Element dataA3 = this.createData("String",bond[x].getPurchased().toString());
        cell[x][2].add(dataA3);
        
        cell[x][3] = this.createCell();
        row[x].add(cell[x][3]);
        Element dataA4 = this.createData("String",bond[x].getDue().toString());
        cell[x][3].add(dataA4);
        
        cell[x][4] = this.createCell();
        row[x].add(cell[x][4]);
        Element dataA5 = this.createData("String",bond[x].getCalled().toString());
        cell[x][4].add(dataA5);
        
        cell[x][5] = this.createCell();
        row[x].add(cell[x][5]);
        Element dataA6 = this.createData("Number",new Double(bond[x].yield).toString());
        cell[x][5].add(dataA6);
        
        cell[x][6] = this.createCell();
        row[x].add(cell[x][6]);
        Element dataA7 = this.createData("Number",new Integer(bond[x].getWAB()).toString());
        cell[x][6].add(dataA7);
        
        cell[x][7] = this.createCell();
        row[x].add(cell[x][7]);
        Element dataA8 = this.createData("Number",new Integer(bond[x].getWAS()).toString());
        cell[x][7].add(dataA8);
        
        cell[x][8] = this.createCell();
        row[x].add(cell[x][8]);
        Element dataA9 = this.createData("Number",new Integer(bond[x].getWN()).toString());
        cell[x][8].add(dataA9);
        }
        
        return workbook;
    }
    
    private Element createWorksheet(String name) {
        Element e = DocumentHelper.createElement("Worksheet");
        e.addAttribute("ss:Name", name);
        e.setQName(new QName("Worksheet",new Namespace(null,"urn:schemas-microsoft-com:office:spreadsheet")));
        return e;
    }
    
    private Element createTable() {
        return DocumentHelper.createElement("ss:Table");
    }
    
    private Element createRow(String index) {
        Element e = DocumentHelper.createElement("ss:Row");
        e.addAttribute("ss:Index",index);
        return e;
    }
    
    private Element createCell() {
        return DocumentHelper.createElement("ss:Cell");
    }
    
    private Element createData(String type, String data) {
        Element e = DocumentHelper.createElement("ss:Data");
        e.addAttribute("ss:Type",type);
        e.setText(data);
        return e;
    }
    
    private Element createWorkbook() throws Exception {
        Element root =  DocumentHelper.createElement("Workbook");
        //Set up the necessary namespaces
        root.add(new Namespace("x","urn:schemas-microsoft-com:office:excel"));
        root.add(new Namespace("ss","urn:schemas-microsoft-com:office:spreadsheet"));
        root.setQName(new QName("Workbook",new Namespace(null,"urn:schemas-microsoft-com:office:spreadsheet")));
        return root;
    }

}
