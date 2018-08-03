import java.util.*;
import java.io.*;  
public class Anagram{
    public static void main(String args []){
        int minGroupSize=Integer.parseInt(args[1]);
        Map m = new HashMap<String, List<String>>();
        try{
            Scanner s = new Scanner(new File(args[0]));
            while(s.hasNext()){
                String word= s.next();
                String alpha= alphabetize(word);
                List l=(List)m.get(alpha);
                if(l==null)
                    m.put(alpha, l=new ArrayList());
                l.add(word);
            }
        } 
        catch(IOException ex){
            System.out.println("Wrong");
        }
        for(Object l: m.values()){ 
            if(((List)l).size()>=minGroupSize)
                System.out.println(((List)l).size()+" : "+l);
        }
    }
    public static String alphabetize(String word){
        char a[]=word.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
    
