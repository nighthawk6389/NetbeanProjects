import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RandomArray extends JPanel implements ActionListener{
    JLabel inLabel= new JLabel("Array Index");
    JLabel elLabel= new JLabel("Array Element");
    JTextField j1= new JTextField(10);
    JTextField j2= new JTextField(10);
    JButton button= new JButton("Show Element");
    int array[]= new int[100];
            
    public RandomArray(){
        button.addActionListener(this);
        setLayout(new FlowLayout());
        add(inLabel);
        add(j1);
        add(elLabel);
        add(j2);
        add(button);
        j2.setEditable(false);
        
        for(int x=0;x<array.length;x++){
            array[x]=(int)(Math.random()*100);
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        String n =j1.getText().trim();
        int num=Integer.parseInt(n);
        try{
            num=array[num];
            j2.setText(num+" ");
        }
        catch(ArrayIndexOutOfBoundsException ex){
            j2.setText("Out of Bound");
        }
    }
}
