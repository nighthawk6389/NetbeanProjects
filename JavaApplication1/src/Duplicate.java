import java.util.*;
public class Duplicate{
    public static void main(String args []){
        SortedSet set = new TreeSet(comp);
        
        for(String a : args){ 
            if(!set.add(a))
                System.out.println("Duplicate Detected: "+a);
        }
        System.out.println(set.size()+" Distinct Words: "+set);
    }

    static Comparator comp = new Comparator(){
        public int compare(Object o1, Object o2) {
        String one=(String)o1;
        String two=(String)o2;
        return o1.toString().compareToIgnoreCase(o2.toString());
        }
    };
}

