package dataStructures.hw3;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mavis Beacon
 */
public class InfixToPostfixParserGENERAL {
    
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
        
    
    
    
    public static void main(String args []){
        
        InfixToPostfixParserGENERAL parser = new InfixToPostfixParserGENERAL();
        String postfix = InfixToPostfixParserGENERAL.convertToPostfixStack("79.5*(8.3+(4.2*3))");
        Node node = InfixToPostfixParserGENERAL.createExpressionTree(postfix);
        
        MyTree t = new MyTree();
        t.root = node;
        t.computeNodePositions(); //finds x,y positions of the tree nodes
        t.maxheight=t.treeHeight(t.root); //finds tree height for scaling y axis
        //DisplaySimpleTree dt = new DisplaySimpleTree(t);//get a display panel
        //dt.setVisible(true);
        double value = InfixToPostfixParserGENERAL.EvalTree(node);
        System.out.println(value);
        String infix = InfixToPostfixParserGENERAL.getInfixExpressionFromTree(node);
        System.out.println(infix);
        
    }
    
    public String convertToPostfixRecursive(String infix){
        
        System.out.println("NOT WORKING YET. OPERATOR IF IS MESSED UP");
        return null;
       
        /* 
        char [] array = infix.toCharArray();
        LinkedList<String> stack = new LinkedList<String>();
        for(int i = 0; i < array.length ; i++){
            char currentChar = array[i];
            
            //If char is a (
            if( currentChar == '('){
                String subInfix = "";
                int openParen = 1;
                while( openParen > 0 && i < array.length){
                    char newCurrentChar = array[++i];
                    if(newCurrentChar == '('){
                        openParen++;
                    }
                    if(newCurrentChar == ')'){
                        openParen--;
                    }
                    subInfix += newCurrentChar;
                }
                String subInfixWithoutLastParen = subInfix.substring(0, subInfix.length()-1);
                //String parenPostfix = convertToPostfix(subInfixWithoutLastParen);
                //stack.addLast(parenPostfix);
                continue;
                
            }
            
            //If char was operator
            if( operatorsList.contains(new Character(currentChar)) ){
                char operator = currentChar;
                if( i + 1 >= array.length){
                    System.out.println("Badly formatted expression. Expression ended with operator: " + operator);
                    return null;
                }
                char secondOperand = array[++i];
                String secondOperandString = "";
                if( secondOperand == '(')
                    
                stack.addLast(secondOperand+"");
                stack.addLast(operator+"");
                continue;
            }
            //Regular char
            stack.addLast(currentChar+"");
        }
        
        System.out.println(stack);
        return stack.toString();
         * 
         */
        
    }
    
    public static String convertToPostfixStack(String infix){
        
        char [] infixArray = infix.toCharArray();
        LinkedList<Character> stack = new LinkedList<Character>();
        String postfix = "";
        
        for(char c : infixArray){
            
            //Operator
            if( operatorsList.contains(c) ){
                Character topOfStack = stack.peek();
                postfix += " ";
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
        }
        
        for(Character ch : stack){
            postfix += " "+ch;
        }
        
        return postfix;
    }
    
    public static Node createExpressionTree(String postfix){
        
        LinkedList<Node> stack = new LinkedList<Node>();
        Scanner scanner = new Scanner(postfix);
        scanner.useDelimiter(" ");
        
        while(scanner.hasNext()){
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
        }
        
        return stack.getFirst();
    }
    
    protected static String getInfixExpressionFromTree( Node T ){
        if( T == null) return "";
        else if( T.type.equals(OPERAND)) return (Double.toString((double)T.data));
        else { // must be an operator...
            String infix = "";
            char op = (char)T.data;
            String operand1 = getInfixExpressionFromTree( T.left);
            String operand2 = getInfixExpressionFromTree( T.right);
            return (infix = "(" + operand1 + op + operand2 + ")");
        }
    }
    
    
    protected static double EvalTree( Node T ){
        if( T == null) return 0;
        else if( T.type.equals(OPERAND)) return ((double)T.data);
        else { // must be an operator...
            char op = (char)T.data;
            double operand1 = EvalTree( T.left);
            double operand2 = EvalTree( T.right);
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
