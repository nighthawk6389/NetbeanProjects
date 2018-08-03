package dataStructures.hw3;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mavis Beacon
 */
public class HW_3 extends JPanel implements ActionListener{
    
    
    private JButton convert, tree, eval,infixTree;
    private JTextField infixInput,postfixOutput,evalValue,infixTreeValue;
    private JScrollPane scrollPane;
    private Node node;
    
    public static void main(String args []){
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(900, 700);
        
        HW_3 hw = new HW_3();
        frame.getContentPane().add(hw);
        
    }
    
    public HW_3(){
        
        this.setLayout(new BorderLayout());
        
        JPanel infixToPostfix = new JPanel();
        infixToPostfix.setLayout(new FlowLayout());
        
        infixInput = new JTextField(20);
        convert = new JButton("Convert");
        convert.addActionListener(this);
        postfixOutput = new JTextField(20);
        postfixOutput.setEditable(false);
        postfixOutput.setBackground(Color.lightGray);
        
        infixToPostfix.add(infixInput);
        infixToPostfix.add(convert);
        infixToPostfix.add(postfixOutput);

       
        JPanel treeButton = new JPanel();
        tree = new JButton("Create tree");
        tree.addActionListener(this);
        treeButton.add(tree);
        
        
        JPanel north = new JPanel(new GridLayout(2,1));
        north.add(infixToPostfix);
        north.add(treeButton);
        
        
        JPanel south = new JPanel();
        eval = new JButton("Eval tree");
        eval.addActionListener(this);
        evalValue = new JTextField(10);
        evalValue.setEditable(false);
        evalValue.setBackground(Color.lightGray);
        infixTree = new JButton("Create Infix From Tree");
        infixTree.addActionListener(this);
        infixTreeValue = new JTextField(20);
        infixTreeValue.setEditable(false);
        infixTreeValue.setBackground(Color.lightGray);
        south.add(eval);
        south.add(evalValue);
        south.add(new JLabel("           "));
        south.add(infixTree);
        south.add(infixTreeValue);
        
        this.add(north,BorderLayout.NORTH); 
        this.add(south,BorderLayout.SOUTH);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == convert){
            String infix = infixInput.getText();
            String postfix = InfixToPostfixParser.convertToPostfixStack(infix);
            postfixOutput.setText(postfix);
        } 
        else if ( e.getSource() == tree){
            String postfix = postfixOutput.getText();
            System.out.println(postfix);
            if(postfix == null || postfix.equals("") || postfix.equals(" ")){
                postfixOutput.setText("PLEASE CONVERT FIRST");
                return;
            }
            
            if(scrollPane != null){
                this.remove(scrollPane);
            }
            
            node = InfixToPostfixParser.createExpressionTree(postfix);
            MyTree t = new MyTree();
            t.root = node;
            t.computeNodePositions(); //finds x,y positions of the tree nodes
            t.maxheight=t.treeHeight(t.root); //finds tree height for scaling y axis
            DisplayPanel p = new DisplayPanel(t);
            p.setPreferredSize(new Dimension(300, 300));
            scrollPane = new JScrollPane(p);   
            this.add(scrollPane,BorderLayout.CENTER);
           
            this.revalidate();
            this.repaint();
            
        }
        else if( e.getSource() == eval ){
            if( node == null )
                return;
            double value = InfixToPostfixParser.EvalTree(node);
            evalValue.setText(value+"");
        }
        else if( e.getSource() == infixTree){
            if( node == null )
                return;
            String infix = InfixToPostfixParser.getInfixExpressionFromTree(node);
            infixTreeValue.setText(infix);
        }
    }
    
    
    
    
    static class InfixToPostfixParser {
    
    private static String OPERATOR = "OPERATOR";
    private static String OPERAND = "OPERAND";
    private static final char [] operators = {'+','-','*','/','^'};
    private static final ArrayList<Character> operatorsList = new ArrayList<Character>();
    private static final Map<Character,Integer> precedence = new HashMap<Character,Integer>();
    static{
        for(int i = 0; i < operators.length; i++){
            operatorsList.add(operators[i]);
        }
        precedence.put('(',0);
        precedence.put('+',1);
        precedence.put('-',1);
        precedence.put('*',2);
        precedence.put('/',2);
        precedence.put('^',3);
    }
    
    public static String convertToPostfixStack(String infix){
        
        char [] infixArray = infix.toCharArray();
        LinkedList<Character> stack = new LinkedList<Character>();
        String postfix = "";
        boolean prevParen = false;
        
        for(char c : infixArray){
            
            //Operator
            if( operatorsList.contains(c) ){
                Character topOfStack = stack.peek();
                postfix += " ";
                
                if( c == '-' && ( postfix.equals(" ") || prevParen ) ){
                    postfix += c;
                    continue;
                } 
                
                if(topOfStack == null){
                    stack.addFirst(c);
                    continue;
                }
                
                int valueOfChar = precedence.get(c);
                System.out.println(valueOfChar + " " + precedence.get(stack.peek())); 
                while( stack.peek() != null && precedence.get(stack.peek()) > valueOfChar){
                    postfix += stack.poll()+" ";
                }
                stack.addFirst(c);
                continue;
            }
            
            if( c == '('){
                stack.addFirst(c);
                prevParen = true;
                continue;
            }
            
            if( c == ')'){
                Character topOfStack = stack.poll();
                while( topOfStack != '(' && topOfStack != null){
                    postfix += " "+ topOfStack;
                    topOfStack = stack.poll();
                }
                continue;
            
            }
            
            //Regular char
            postfix += c;
            prevParen = false;
        }
        
        for(Character ch : stack){
            postfix += " "+ch;
        }
        
        return postfix;
    }
    
    public static Node createExpressionTree(String postfix){
        
        LinkedList<Node> stack = new LinkedList<Node>();
        Scanner scanner = new Scanner(postfix);
        
        while(scanner.hasNext()){
            try{
            String next = scanner.next();
            if(next.length() == 1){
                Character c = new Character(next.charAt(0));
                if(operatorsList.contains(c)){
                    Node node = new Node(c);
                    node.type = OPERATOR; 
                    Node left = stack.poll();
                    Node right = stack.poll();
                    node.left = right; //Reversed on purpose
                    node.right = left; //Reversed on purpose
                    stack.addFirst(node); 
                    continue;
                }
            }
            
            Node n = new Node(Double.parseDouble(next));
            n.type = OPERAND;
            stack.addFirst(n);
            } catch(Exception ex){
                System.out.println(ex.toString());
                
            }
        }
        
        return stack.getFirst();
    }
    
    protected static String getInfixExpressionFromTree( Node T ){
        if( T == null) return "";
        else if( T.type.equals(OPERAND)) return (Double.toString((Double)(T.data)));
        else { // must be an operator...
            String infix = "";
            char op = (Character)(T.data);
            String operand1 = getInfixExpressionFromTree( T.left);
            String operand2 = getInfixExpressionFromTree( T.right);
            return (infix = "(" + operand1 + op + operand2 + ")");
        }
    }
    
    
    protected static double EvalTree( Node node ){
        if( node == null) 
            return 0;
        else if( node.type.equals(OPERAND)){ 
            return ((Double)(node.data)); 
        }
        else { // must be an operator...
            char op = (Character) node.data;
            double operand1 = EvalTree( node.left);
            double operand2 = EvalTree( node.right);
            return( applyOperator(operand1, op, operand2));
        }
    }

    private static double applyOperator(double operand1, char op, double operand2) {
        
        double value = 0;
        switch(op){
            case '+' : value = operand1 + operand2; break;
            case '-' : value = operand1 - operand2; break;
            case '*' : value = operand1 * operand2; break;
            case '/' : value = operand1 / operand2; break;
            default : System.out.println("Operator not found"); break;
        }
        return value;
    }
    
}
    
}
