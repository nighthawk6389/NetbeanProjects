import java.awt.Event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Hun extends JFrame implements ActionListener{
     private JButton element;
     private JTextField ar,res;
     int list[]=new int[100];
     
    public static void main(String args []){
        Hun frame=new Hun();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }
    
    public Hun(){
     JPanel p1= new JPanel();
     p1.add(new JLabel("Array Index"));
     p1.add(ar=new JTextField(4));
     p1.add(new JLabel("Array Element"));
     p1.add(res=new JTextField(4));
     res.setEditable(false);
     
     JPanel p2= new JPanel();
     p2.add(element=new JButton("Show Element"));
     
     getContentPane().setLayout(new BorderLayout());
     getContentPane().add(p1,BorderLayout.CENTER);
     getContentPane().add(p2,BorderLayout.SOUTH);
     
     element.addActionListener(this);
     
     for(int x=0;x<100;x++){
         list[x]=(int)(Math.random()*100);
     }
        
    }
    public void actionPerformed(ActionEvent e){
        int n1=(Integer.parseInt(ar.getText().trim()));
        try{
        if(e.getSource()==element){
           res.setText(String.valueOf(list[n1-1])); 
         //JOptionPane.showMessageDialog(null,list[n1-1],"Element",JOptionPane.INFORMATION_MESSAGE);
        }
        }
        catch(IndexOutOfBoundsException ex){
            System.out.println(ex);
        }
    }
}
