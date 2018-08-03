import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import java.awt.*;
/*
 * Practice.java
 *
 * Created on September 7, 2008, 9:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Ilan Elkobi
 */
public class Practice extends JFrame implements ActionListener{
    JButton save,open;
    JTextArea area;
    JFileChooser chooser= new JFileChooser();
    JList list= new JList();
    /** Creates a new instance of Practice */
    public static void main(String args []){
       // JFrame frame = new JFrame();
        Practice frame = new Practice();
        //frame.getContentPane().add(practice);
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    
    public Practice() {
        setLayout(new BorderLayout());
        
        save= new JButton("Save");
        open= new JButton("Open");
        area=new JTextArea();
        
        save.addActionListener(this);
        open.addActionListener(this);
        
        JPanel panel1= new JPanel();
        JScrollPane pane= new JScrollPane(area);
        pane.setPreferredSize(new Dimension(400,400));
        panel1.add(pane);
        
        JPanel panel2= new JPanel();
        panel2.add(save);
        panel2.add(open);
        
        chooser.setCurrentDirectory(new File("."));
        
        add(panel1,"Center");
        add(panel2,"South");
    }

    public void actionPerformed(ActionEvent e) { 
        if(e.getSource()==open)
            openFile();
        else if(e.getSource()==save)
            saveFile();
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
             area.append((String)reader.readObject());
            }
        catch(FileNotFoundException ex){
            System.out.println("No file");
        }
        catch(IOException ex){
            System.out.println("You suck");
        }
        catch(ClassNotFoundException ex){
            System.out.println("Again");
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
            //save();
        }
    }
    private void save(File file){
        String hey="hey";
        ObjectOutputStream writer =null;
        try{
        writer=new ObjectOutputStream(new FileOutputStream(file));
        writer.writeObject(hey);
        }
        catch(IOException ex){
            System.out.println("Problem Save");
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
}
       
