import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Calc extends JFrame implements ActionListener{
        private JTextField num1,num2,res;
        private JButton add,sub;
        
    public static void main(String [] args ){
        try{
        Calc frame1= new Calc();
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        catch(ArithmeticException ex){
            System.out.println(ex);
        }
    }

         
        public Calc(){
            getContentPane().setLayout(new BorderLayout());
            
            JPanel p1= new JPanel();
            p1.add(new JLabel("num1"));
            p1.add(num1=new JTextField(3));
            p1.add(num2=new JTextField(3));
            p1.add(res=new JTextField(3));
            res.setEditable(false);
            
            JPanel p2= new JPanel();
            p2.add(add=new JButton("Add"));
            p2.add(sub=new JButton("Subtract"));
            
            getContentPane().add(p1,BorderLayout.CENTER);
            getContentPane().add(p2,BorderLayout.SOUTH);
            
            add.addActionListener(this);
            sub.addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent e) throws ArithmeticException {
      
            if(e.getSource() == add)
                calculate('+');
            else if(e.getSource() == sub)
                calculate('-');
            
        }
                
        private void calculate(char op){
            try{
            int n1=(Integer.parseInt(num1.getText().trim()));
            int n2=(Integer.parseInt(num2.getText().trim()));
            int result=0;
            
                switch(op){
                case '+': result = n1 + n2;break;
                case '-': result= n1-n2;break;
            }
            res.setText(String.valueOf(result));
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
}
           
      


