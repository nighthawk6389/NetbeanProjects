public class IntType implements Comparable{
      private int number1[];
      private double answer;


      public IntType(int array[]){
              number1=array;
      }


      public int[] add(int number2[]){
              if(number1.length!=number2.length){
                      System.out.println("The arrays were not of thesame size");
                      return null;
              }
              int answer[]=new int[number1.length+1];
              answer[0]=0;
              for(int i=number1.length-1;i>=0;i--){
                      answer[i+1]+=number1[i]+number2[i];
                      if(answer[i+1]>=10){
                              answer[i+1]-=10;
                              answer[i]+=1;
                      }
              }
              number1=answer;
              return answer;
      }



      public int[] subtract(int number2[]){
              if(number1.length!=number2.length){
                      System.out.println("The arrays were not of the same size");
                      return null;
              }
              int answer[]=new int[number1.length];
              for(int i=number1.length-1;i>=0;i--){
                      answer[i]=number1[i]-number2[i];
                      if(answer[i]<0 && i>0){
                              answer[i]+=10;
                              number1[i-1]-=1;
                      }
                      if(answer[i]<0 && i==0){
                              answer[i]+=10;
                              number1[i-1]-=1;
                      }
              }
              number1=answer;
              return answer;
              }



      public int[] multiply(int number1[],int number2[]){
              int answer[]=new int[number1.length];
              int temp[]=new int[number1.length];
              int temp1[]=new int[number1.length];
              int temp2[]=new int[number1.length];
              int i=number1.length-1;

              temp[i]=number2[i]*number1[i];
              if(temp[i]>=10){
                      temp[i]-=temp[i]/10*10;
                      temp[i-1]+=temp[i]/10;
              }
              
               temp[i-1]+=number2[i]*number1[i-1];
              if(temp1[i]>=10){
                      temp1[i]-=temp1[i]/10*10;
                      temp1[i-1]+=temp1[i]/10;
              }

              temp[i-2]+=number2[i]*number1[i-2];
              if(temp2[i]>=10){
                      temp2[i]-=temp2[i]/10*10;
                      temp2[i-1]+=temp2[i]/10;
              }

              /*
              if(temp[i]>=20){
                      temp[i]-=20;
                      temp[i-1]+=2;
              }
              if(temp[i]>=30){
                      temp[i]-=30;
                      temp[i-1]+=3;
              }
              if(temp[i]>=40){
                      temp[i]-=40;
                      temp[i-1]+=4;
              }
              if(temp[i]>=50){
                      temp[i]-=50;
                      temp[i-1]+=5;
              }
              if(temp[i]>=50){
                      temp[i]-=50;
                      temp[i-1]+=5;
              }
              if(temp[i]>=60){
                      temp[i]-=60;
                      temp[i-1]+=6;
              }
              if(temp[i]>=70){
                      temp[i]-=70;
                      temp[i-1]+=7;
              }
              if(temp[i]>=80){
                      temp[i]-=80;
                      temp[i-1]+=8;
              }
              temp[i-1]+=number2[i]*number1[i-1];
              if(temp[i-1]>=10){
                      temp[i-1]-=10;
                      temp[i-2]+=1;
              }
              if(temp[i-1]>=20){
                      temp[i-1]-=20;
                      temp[i-2]+=2;
              }
              if(temp[i-1]>=30){
                      temp[i-1]-=30;
                      temp[i-2]+=3;
              }
              if(temp[i-1]>=40){
                      temp[i-1]-=40;
                      temp[i-2]+=4;
              }
              if(temp[i-1]>=50){
                      temp[i-1]-=50;
                      temp[i-2]+=5;
              }
              if(temp[i-1]>=50){
                      temp[i-1]-=50;
                      temp[i-2]+=5;
              }
              if(temp[i-1]>=60){
                      temp[i-1]-=60;
                      temp[i-2]+=6;
              }
              if(temp[i-1]>=70){
                      temp[i-1]-=70;
                      temp[i-2]+=7;
              }
              if(temp[i-1]>=80){
                      temp[i-1]-=80;
                      temp[i-2]+=8;
              }

              temp[i-2]+=number2[i]*number1[i-2];
              if(temp[i-2]>=10){
                      temp[i-2]-=10;
                      temp[i-3]+=1;
              }
              if(temp[i-2]>=20){
                      temp[i-2]-=20;
                      temp[i-3]+=2;
              }
              if(temp[i-2]>=30){
                      temp[i-2]-=30;
                      temp[i-3]+=3;
              }
              if(temp[i-2]>=40){
                      temp[i-2]-=40;
                      temp[i-3]+=4;
              }
              if(temp[i-2]>=50){
                      temp[i-2]-=50;
                      temp[i-3]+=5;
              }
              if(temp[i-2]>=50){
                      temp[i-2]-=50;
                      temp[i-3]+=5;
              }
              if(temp[i-2]>=60){
                      temp[i-2]-=60;
                      temp[i-3]+=6;
              }
              if(temp[i-2]>=70){
                      temp[i-2]-=70;
                      temp[i-3]+=7;
              }
              if(temp[i-2]>=80){
                      temp[i-2]-=80;
                      temp[i-3]+=8;
              }



              temp1[i]=0;
              temp1[i-1]=number2[i-1]*number1[i];
              if(temp1[i-1]>=10){
                      temp1[i-1]-=10;
                      temp1[i-2]+=1;
              }
              if(temp1[i-1]>=20){
                      temp1[i-1]-=20;
                      temp1[i-2]+=2;
              }
              if(temp1[i-1]>=30){
                      temp1[i-1]-=30;
                      temp1[i-3]+=3;
              }
              if(temp1[i-1]>=40){
                      temp1[i-1]-=40;
                      temp1[i-2]+=4;
              }
              if(temp1[i-1]>=50){
                      temp1[i-1]-=50;
                      temp1[i-2]+=5;
              }
              if(temp1[i-1]>=50){
                      temp1[i-1]-=50;
                      temp1[i-2]+=5;
              }
              if(temp1[i-1]>=60){
                      temp1[i-1]-=60;
                      temp1[i-2]+=6;
              }
              if(temp1[i-1]>=70){
                      temp1[i-1]-=70;
                      temp1[i-2]+=7;
              }
              if(temp1[i-1]>=80){
                      temp1[i-1]-=80;
                      temp1[i-2]+=8;
              }
              temp1[i-2]+=number2[i-1]*number1[i-1];
              if(temp1[i-2]>=10){
                      temp1[i-2]-=10;
                      temp1[i-3]+=1;
              }
              if(temp1[i-2]>=20){
                      temp1[i-2]-=20;
                      temp1[i-3]+=2;
              }
              if(temp1[i-2]>=30){
                      temp1[i-2]-=30;
                      temp1[i-3]+=3;
              }
              if(temp1[i-2]>=40){
                      temp1[i-2]-=40;
                      temp1[i-3]+=4;
              }
              if(temp1[i-2]>=50){
                      temp1[i-2]-=50;
                      temp1[i-3]+=5;
              }
              if(temp1[i-2]>=50){
                      temp1[i-2]-=50;
                      temp1[i-3]+=5;
              }
              if(temp1[i-2]>=60){
                      temp1[i-2]-=60;
                      temp1[i-3]+=6;
              }
              if(temp1[i-2]>=70){
                      temp1[i-2]-=70;
                      temp1[i-3]+=7;
              }
              if(temp1[i-2]>=80){
                      temp1[i-2]-=80;
                      temp1[i-3]+=8;
              }
              temp1[i-3]+=number2[i-1]*number1[i-2];
              if(temp1[i-3]>=10){
                      temp1[i-3]-=10;
                      temp1[i-4]+=1;
              }
              if(temp1[i-3]>=20){
                      temp1[i-3]-=20;
                      temp1[i-4]+=2;
              }
              if(temp1[i-3]>=30){
                      temp1[i-3]-=30;
                      temp1[i-4]+=3;
              }
              if(temp1[i-3]>=40){
                      temp1[i-3]-=40;
                      temp1[i-4]+=4;
              }
              if(temp1[i-3]>=50){
                      temp1[i-3]-=50;
                      temp1[i-4]+=5;
              }
              if(temp1[i-3]>=50){
                      temp1[i-3]-=50;
                      temp1[i-4]+=5;
              }
              if(temp1[i-3]>=60){
                      temp1[i-3]-=60;
                      temp1[i-4]+=6;
              }
              if(temp1[i-3]>=70){
                      temp1[i-3]-=70;
                      temp1[i-4]+=7;
              }
              if(temp1[i-3]>=80){
                      temp1[i-3]-=80;
                      temp1[i-4]+=8;
              }

              temp2[i]=0;
              temp2[i-1]=0;
              temp2[i-2]=number2[i-2]*number1[i];
              if(temp2[i-2]>=10){
                      temp2[i-2]-=10;
                      temp2[i-3]+=1;
              }
              if(temp2[i-2]>=20){
                      temp2[i-2]-=20;
                      temp2[i-3]+=2;
              }
              if(temp2[i-2]>=30){
                      temp2[i-2]-=30;
                      temp2[i-3]+=3;
              }
              if(temp2[i-2]>=40){
                      temp2[i-2]-=40;
                      temp2[i-3]+=4;
              }
              if(temp2[i-2]>=50){
                      temp2[i-2]-=50;
                      temp2[i-3]+=5;
              }
              if(temp2[i-2]>=50){
                      temp2[i-2]-=50;
                      temp2[i-3]+=5;
              }
              if(temp2[i-2]>=60){
                      temp2[i-2]-=60;
                      temp2[i-3]+=6;
              }
              if(temp2[i-2]>=70){
                      temp2[i-2]-=70;
                      temp2[i-3]+=7;
              }
              if(temp2[i-2]>=80){
                      temp2[i-2]-=80;
                      temp2[i-3]+=8;
              }

              temp2[i-3]+=number2[i-2]*number1[i-1];
              if(temp2[i-3]>=10){
                      temp2[i-3]-=10;
                      temp2[i-4]+=1;
              }
              if(temp2[i-3]>=20){
                      temp2[i-3]-=20;
                      temp2[i-4]+=2;
              }
              if(temp2[i-3]>=30){
                      temp2[i-3]-=30;
                      temp2[i-4]+=3;
              }
              if(temp2[i-3]>=40){
                      temp2[i-3]-=40;
                      temp2[i-4]+=4;
              }
              if(temp2[i-3]>=50){
                      temp2[i-3]-=50;
                      temp2[i-4]+=5;
              }
              if(temp2[i-3]>=50){
                      temp2[i-3]-=50;
                      temp2[i-4]+=5;
              }
              if(temp2[i-3]>=60){
                      temp2[i-3]-=60;
                      temp2[i-4]+=6;
              }
              if(temp2[i-3]>=70){
                      temp2[i-3]-=70;
                      temp2[i-4]+=7;
              }
              if(temp2[i-3]>=80){
                      temp2[i-3]-=80;
                      temp2[i-4]+=8;
              }

              temp2[i-4]+=number2[i-2]*number1[i-2];
              if(temp2[i-4]>=10){
                      temp2[i-4]-=10;
                      temp2[i-5]+=1;
              }
              if(temp2[i-4]>=20){
                      temp2[i-4]-=20;
                      temp2[i-5]+=2;
              }
              if(temp2[i-4]>=30){
                      temp2[i-4]-=30;
                      temp2[i-5]+=3;
              }
              if(temp2[i-4]>=40){
                      temp2[i-4]-=40;
                      temp2[i-5]+=4;
              }
              if(temp2[i-4]>=50){
                      temp2[i-4]-=50;
                      temp2[i-5]+=5;
              }
              if(temp2[i-4]>=50){
                      temp2[i-4]-=50;
                      temp2[i-5]+=5;
              }
              if(temp2[i-4]>=60){
                      temp2[i-4]-=60;
                      temp2[i-5]+=6;
              }
              if(temp2[i-4]>=70){
                      temp2[i-4]-=70;
                      temp2[i-5]+=7;
              }
              if(temp2[i-4]>=80){
                      temp2[i-4]-=80;
                      temp2[i-5]+=8;
              }

               * */

              answer[0]=0;
              for(int j=number1.length-1;j>=0;j--){
                      answer[j]+=temp[j]+temp1[j]+temp2[j];
                      if(answer[j]>=10){
                              answer[j]-=10;
                              answer[j-1]+=1;
                      }
              }
              return answer;
      }
      public int[] divide(int number1[],int number2){
          int answer[]=new int[number1.length];
          int temp;
          int temp1;
          answer[0]=number1[0]/number2;
          temp=answer[0]*number2;
          temp1=number1[0]-temp;
          temp1=temp1*10+number1[1];
          for(int j=1;j<number1.length;j++){
                  answer[j]=temp1/number2;
                  temp=answer[j]*number2;
                  temp1=temp1-temp;
                  if(j<number1.length-1)
                          temp1=temp1*10+number1[j+1];
                  else
                          System.out.println("remainder"+temp1);
                  }
          return answer;
      }

     //@Override
     // public boolean equals(Object o){
      //}

    @Override
    public int compareTo(Object o) {

        if(this.answer> ((IntType)o).answer )
            return 1;
        else if(this.answer == ((IntType)o).answer )
            return 0;
        else
            return -1;
    }

      @Override
      public String toString(){
          return answer+" ";
      }
      public void print(int array[]){
              for(int j=0;j<array.length;j++)
                      System.out.print(array[j]);
              System.out.println();
      }

      public static void main(String [] args){
              int number1[]={0,2,0,0,5,1};
              int number2[]={0,3,5,9,3,5};

              IntType ans=new IntType(number1);

              int answer[]=ans.add(number2);
              System.out.print("The sum is ");
              ans.print(answer);
              int number3[]={0,0,3,5,9,3,5};
              answer=ans.subtract(number3);
              System.out.print("The difference is ");
              ans.print(answer);
              int number4[]={0,0,1,2,3};
              int number5[]={0,0,4,5,6};
              answer=ans.multiply(number4,number5);
              System.out.print("The product is ");
              ans.print(answer);
              int number[]={4,3,5};
              int number6=25;
              answer=ans.divide(number,number6);
              System.out.print("The quotient is ");
              ans.print(answer);
      }

}