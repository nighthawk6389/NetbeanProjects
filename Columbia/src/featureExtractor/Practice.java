/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureExtractor;

/**
 *
 * @author Ilan
 */
public class Practice {
    
    
    
    public static void main(String args[]){
        
          
        ConstructParser c = new ConstructParser();   
        
        /* Check for assignment and equality spacing */
        
                String s = "if (frequency>test.frequency)";
                
                c.generic_paren_spacing(s, 3);
                
                return;
        
                /*String temp  = new String(s);
                
                int [] assignEqArray;
                int assignIndex = -1; // This needs to be -1
                int stretch_right = 0;
                
                do {
                    temp            = temp.substring(assignIndex + stretch_right + 1);
                    assignEqArray   = c.checkAssignmentOrEquality(temp);
                    assignIndex     = assignEqArray[0];
                    stretch_right   = assignEqArray[1];
                } while( assignIndex != -1 );
  
  
                System.out.println(c.assignment + " " + c.assignmentOpp + " " + c.equality + " " + c.equalityOpp); 
                * */
                
        /*
        System.out.println(c.if_ifParen + " " + c.if_parenCondition + " " + 
                c.if_conditionParen + " " + c.if_parenBracket + " " + c.if_bracketInline 
                + " " + c.if_bracketNewline);
        */
        /*
        System.out.println(c.for_forParen + " " + c.for_parenVariable + " " + 
                c.for_variableSemi + " " + c.for_semiCondition + " " + c.for_conditionSemi 
                + " " + c.for_semiIncrement + " " + c.for_incrementParen + " " + c.for_parenBracket
                + " " + c.for_bracketInline + " " + c.for_bracketNewline);
        */
    }

    
  
}
