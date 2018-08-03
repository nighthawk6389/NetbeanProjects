package dataStructures.hw4;


import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Mavis Beacon
 */
public class Huffman extends JPanel implements ActionListener{
    
    JTextArea textArea;
    JButton countFreq, buildTree, displayString;
    JScrollPane scrollPane;
    JTextArea binaryTextArea;
    HuffmanNode root;
    
    public static void main(String args [] ){
        
        String message = "Hello";
        int [] array = Huffman.countFreq(message);
        System.out.println(Arrays.toString(array));
        Huffman.computeHuffman(array);
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(900, 700);
        
        Huffman huffman = new Huffman();
        frame.getContentPane().add(huffman);
        huffman.revalidate();
        frame.invalidate();
        frame.validate();
        
    }
    
    public Huffman(){
        
        this.setLayout(new BorderLayout());
        
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north,BoxLayout.Y_AXIS));
        //north.setLayout(new GridLayout(3,1));
        
        JPanel topNorthLabel = new JPanel();
        topNorthLabel.add(new JLabel("Enter a message below to be encoded"));
        
        JPanel topNorth = new JPanel();
        textArea = new JTextArea(5,50);
        textArea.setText("Enter text here to be encoded...");
        topNorth.add(textArea);
        
        JPanel bottomNorth = new JPanel();
        countFreq = new JButton("Count Frequencies Of Letters");
        countFreq.addActionListener(this);
        buildTree = new JButton("Build Huffman Tree");
        buildTree.addActionListener(this);
        displayString = new JButton("Display Binary String");
        displayString.addActionListener(this);
        bottomNorth.add(countFreq);
        bottomNorth.add(buildTree);
        
        JPanel binaryStringPanel = new JPanel();
        binaryTextArea = new JTextArea(2,40);
        binaryStringPanel.add(displayString);
        //binaryStringPanel.add(binaryTextArea);
        
        north.add(topNorthLabel);
        north.add(topNorth);
        north.add(bottomNorth);
        north.add(binaryStringPanel);
        north.add(binaryTextArea);
        
        this.add(north,"North");
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if( e.getSource() == countFreq){
            String message = textArea.getText();
            if(message == null){
                JOptionPane.showMessageDialog(null, "Please enter a valid mesage");
                return;
            }
            int [] array = Huffman.countFreq(message);
            String total = Huffman.outputFreqArray(array);
            JOptionPane.showMessageDialog(null, new JTextArea(total));
        }
        else if( e.getSource() == buildTree){
            String message = textArea.getText();
            if(message == null){    
                JOptionPane.showMessageDialog(null, "Please enter a valid mesage");
                return;
            }
            
            if(scrollPane != null){
                this.remove(scrollPane);
            }
            
            int [] array = Huffman.countFreq(message);
            root = computeHuffman(array);
            MyTree tree = new MyTree();
            tree.root = root;
            tree.computeNodePositions(); //finds x,y positions of the tree nodes
            tree.maxheight=tree.treeHeight(tree.root); //finds tree height for scaling y axis
            DisplayPanel p = new DisplayPanel(tree);//get a display panel 
            p.setPreferredSize(new Dimension(300, 300));
            scrollPane = new JScrollPane(p);   
            this.add(scrollPane,BorderLayout.CENTER);
           
            this.revalidate();
            this.repaint();
        }
        else if( e.getSource() == displayString){
            String message = textArea.getText();
            char [] array = message.toCharArray();
            
            if(root == null){
                System.out.println("Please build tree first");
                return;
            }
            if(message == null){
                System.out.println("Please enter a valid message");
                return;
            }
            
            String [] huffArray = new String[128];
            Huffman.createHuffmanCodeArray(huffArray, root);
            
            StringBuilder buff = new StringBuilder();
            for(char c : array){
                char cLower = Character.toLowerCase(c);
                System.out.println(cLower);
                String code = huffArray[ cLower ];
                buff.append(code);
            }
            
            binaryTextArea.setText(buff.toString());
        }
    }
    
    public static int [] countFreq(String message){
        int [] array = new int [128];
        char [] charArray = message.toCharArray();
        for(char c : charArray){
            char cLower = Character.toLowerCase(c);
            //if( Character.i ){
                int index = (int)cLower;
                array[index] = array[index] + 1;
            //}
        }
        return array;
    }
    
    private static String outputFreqArray(int[] array) {
        String total = "";
        System.out.println(" CHAR\tASCII\tFREQ ");
        total += "   CHAR\tASCII\tFREQ \n";
        for(int i = 0; i < array.length; i++){
            if(array[i] == 0)
                continue;
            System.out.println("" + ( (char)(i) ) + "\t" + (i) + "\t" + array[i] + " ");
            total += "   " + ( (char)(i) ) + "\t" + (i) + "\t" + array[i] + " \n";
        }
        return total;
    }   
    
    public static HuffmanNode computeHuffman(int [] freqArray){
        
        MyBinaryHeap heap = new MyBinaryHeap(freqArray.length);
        int counter = 0;
        for(int i = 0; i < freqArray.length ; i++){
            if( freqArray[i] == 0)
                continue;
            heap.insert( new HuffmanNode( (char)(i) , freqArray[i] ) );
            counter++;
        }
        
        //HuffmanCode
        HuffmanNode x,y;
        for(int i = 0; i < counter - 1; i++){
            HuffmanNode z=new HuffmanNode();
            z.left=x=((HuffmanNode)(heap.deleteMin()));
            z.right=y=((HuffmanNode)(heap.deleteMin()));
            z.freq=x.freq+y.freq;
            heap.insert(z);

        }
        
        HuffmanNode top = (HuffmanNode)heap.deleteMin();
        
        Huffman.outputHuffmanCode(top, "", false);
                        
        return top;
    }
    
    public static void outputHuffmanCode(HuffmanNode node,String code, boolean outputToConsole){

        //Recursive method that keeps calling the nodes of the tree until its null. The code
        //is continually added as it goes down the tree and outputted when it finds
        //a leaf
        if(node.left!=null){
            outputHuffmanCode(node.left,code+"0",outputToConsole);
            node.code=code+"0";
        }
        if(node.right!=null){
            outputHuffmanCode(node.right,code+"1",outputToConsole);
            node.code=code+"1";
        }

        if(node.right==null || node.left==null){
            if(outputToConsole)
                System.out.println(node.c+"="+code);
            node.code=code;
        }

    }
    
    public static void createHuffmanCodeArray(String [] array, HuffmanNode node){
        
        if(node.code == null || node.code == " "){
            System.out.println("CODE NOT SET. PLEASE RUN OUTPUT");
            return;
        }
        
        if(node.left!=null){
            createHuffmanCodeArray(array,node.left);
        }
        if(node.right!=null){
            createHuffmanCodeArray(array,node.right);
        }

        if(node.right==null || node.left==null){
            array[ node.c  ] = node.code;
        }        
        
    }
    
}

class HuffmanNode implements Comparable {
    //Each Huffman Node stores a left node, right node, the charachter it represents(c)
    //and the amount of times it appears(freq)

    HuffmanNode left;
    HuffmanNode right;
    int freq;
    char c = '-'; 
    String code;
    int xpos;  //stores x and y position of the node in the tree
    int ypos;

    public HuffmanNode(){
        left=right=null;
        freq=0;
    }
    public HuffmanNode(char c, int freq){
        left=right=null;
        this.freq=freq;
        this.c = c;
    }
    public HuffmanNode(HuffmanNode left,HuffmanNode right,int freq){
        this.left=left;
        this.right=right;
        this.freq=freq;
    }
    @Override
    public int compareTo(Object i2){
        if(((HuffmanNode)(i2)).freq==this.freq)
            return 0;
        else if(((HuffmanNode)(i2)).freq>this.freq)
            return -1;
        else
            return 1;

    }
    @Override
    public String toString(){
        return c + " with Freq = " + freq;
    }
}//end HuffmanNode


class MyBinaryHeap
{
public MyBinaryHeap( int capacity )
{
    currentSize = 0;
    array = new Comparable[ capacity + 1 ];
}
public void insert( Comparable x )
{
    // percolate up
    int hole = ++currentSize;
    for( ; hole > 1 && x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 )
        array[ hole ] = array[ hole / 2 ];
    array[ hole ] = x;
}
public Comparable findMin( )
{
    if( isEmpty( ) )
        return null;
    return array[ 1 ];
}
public Comparable deleteMin( )
{
    if( isEmpty( ) )
        return null;
    Comparable minItem = findMin( );
    array[ 1 ] = array[ currentSize-- ];
    percolateDown( 1 );
    return minItem;
}
public void buildHeap( )
{
    for( int i = currentSize / 2; i > 0; i-- )
        percolateDown( i );
}
public boolean isEmpty( )
{ 
    return currentSize == 0; 
}
private void percolateDown( int hole )
{
    int child;
    Comparable tmp = array[ hole ];
    for( ; hole * 2 <= currentSize; hole = child ) {
        child = hole * 2;
        if( child != currentSize && array[ child + 1 ].compareTo( array[ child ] ) < 0 )
            child++;
        if( array[ child ].compareTo( tmp ) < 0 )
            array[ hole ] = array[ child ];
        else break;
    }
    array[ hole ] = tmp;
}
private int currentSize; // Number of elements in heap
private Comparable [ ] array; // The heap array

}
