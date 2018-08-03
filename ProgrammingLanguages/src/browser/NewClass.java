/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package browser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author Mavis Beacon
 */
public class NewClass extends JFrame {

    public static void main(String args []){
        JPanel panel=new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEADING));

        int x=600;

        JEditorPane editor1=new JEditorPane();
        editor1.setLayout(new FlowLayout());
        editor1.setPreferredSize(new Dimension(x,x));
        editor1.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        editor1.setFont(new Font("Arial",0,6));
        editor1.setContentType("text/html");
        editor1.setEditorKit(new HTMLEditorKit());
        editor1.setBackground(Color.white);
        editor1.setEditable(false);
        try {
            editor1.setPage("http://www.google.com");
        } catch (IOException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        JEditorPane editor2=new JEditorPane();
        editor2.setPreferredSize(new Dimension(x,x));
        editor2.setBackground(Color.green);

        JEditorPane editor3=new JEditorPane();
        editor3.setPreferredSize(new Dimension(x,x));
        editor3.setBackground(Color.blue);

        JEditorPane editor4=new JEditorPane();
        editor4.setPreferredSize(new Dimension(x,x));
        editor4.setBackground(Color.red);

        panel.add(editor1);
        panel.add(editor2);
        panel.add(editor3);
        panel.add(editor4);

        JFrame frame=new JFrame();
        frame.setVisible(true);
        frame.setSize(400, 600);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.getContentPane().add(panel);
    }

}
