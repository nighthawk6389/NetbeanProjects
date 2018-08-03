public class array{
    public static void main(String args []){
        double [] array = {1,2,3,4,5};
        int y=0;
      
        for(int x= 0; x<=array.length-1;x++){
            y+=array[x];
        }
        System.out.println(y/array.length);
    }
}
