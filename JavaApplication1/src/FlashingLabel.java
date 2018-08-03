import javax.swing.*;
import java.awt.*;

public class FlashingLabel extends JPanel implements Runnable{
    JLabel label;
    boolean on=true;
    private boolean displayMe=true;
    
    public FlashingLabel(String st){
        //setBackground(Color.ORANGE); 
        setLayout(new BorderLayout());

        label=new JLabel(st);
        add(label,BorderLayout.CENTER);
        //add(label,BorderLayout.CENTER);
        //label.setHorizontalAlignment(SwingConstants.CENTER);
        //label.setHorizontalAlignment(SwingConstants.CENTER);
        new Thread(this).start();
       // System.out.println("done");
    }

    @Override
    public void run() {
        while(displayMe){
           label.setVisible(on);
        try{
            Thread.sleep(500);  
        }
        catch(InterruptedException ex) {
        }
        on= !on;
        //repaint();
        //System.out.println(label.getLocation().x);
        }
    }

    public void setText(String st){
        label.setText(st);
    }

    public void setDisplayMe(boolean displayMe){
        this.displayMe=displayMe;
    }
    public boolean getDisplayMe(){
        return this.displayMe;
    }

}
