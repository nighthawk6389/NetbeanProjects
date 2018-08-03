package BondList;

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
        //SaveToExcel j = new SaveToExcel();
        //j.write(file.toString()+".xml",array);
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

 
