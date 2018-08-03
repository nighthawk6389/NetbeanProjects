import java.io.*;
import java.util.*;
public class ReadFile{
    public static void main(String args []){
        String next=null;
        if(args.length >2)
            System.out.println("Incorrect Usage");
        File file= new File(args[0]);
        int size=(int)file.length()/5;
        int listSize=0;
        List list= new ArrayList(size);
        BufferedReader in = null;
        try{
            in=new BufferedReader(new FileReader(args[0]));
            while((next=in.readLine())!=null){
                list.add(next);
                listSize++;
            }
        }
        catch(IOException ex){
            System.out.println("Error: "+ex);
        }
        finally{
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        Random rand= new Random();
        int num= Integer.parseInt(args[1]);
        for(int x=num;x>=0;x--){
            System.out.println(list.get(rand.nextInt(listSize)));
        }
    }
}
