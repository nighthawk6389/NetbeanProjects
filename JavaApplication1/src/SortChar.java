import javax.swing.JOptionPane;

public class SortChar{
    public static void main(String args []){
        String text = JOptionPane.showInputDialog(null,"Enter","Enter",JOptionPane.QUESTION_MESSAGE);
        JOptionPane.showMessageDialog(null,sort(text),"String-Sort",JOptionPane.INFORMATION_MESSAGE);
        
    }
    public static StringBuffer sort(String text){
        StringBuffer temp= new StringBuffer(text);
        int index=0;
        char t='a';
        char low=text.charAt(0);
        Character a;
        Character b;
        for(int x=0;x<text.length();x++){
            low=temp.charAt(x);
            for(int y=x;y<text.length();y++){
                a =new Character(temp.charAt(y));
                b= new Character(low);
                if(a.compareTo(b) < 0){
                    low=text.charAt(y);
                    index=y;
                }
            }
            a= new Character(temp.charAt(x));
            b= new Character(low);
            if(a.compareTo(b) >0){
            t=temp.charAt(x);
            temp.setCharAt(x,temp.charAt(index));
            temp.setCharAt(index,t);
            }
        }       
        return temp;
    }
}

