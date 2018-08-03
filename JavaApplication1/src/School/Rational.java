package School;

public class Rational {
	long num;
	long den;
	public static Rational ZERO=new Rational(0,0);
        public static Rational ONE=new Rational(1,1);

	public static void main(String args []){
	Rational r=new Rational(8,4);
	System.out.println(r);
	Rational r2=new Rational(6,7);
	System.out.println(r2);
	System.out.println("Added: "+r.add(r2));
	System.out.println("Multiplied: "+r.multiply(r2));
	System.out.println("Divided: "+r.divide(r2));
	System.out.println("Subtracted: "+r.subtract(r2));
	
	}
	public Rational(){
		num=0;
		den=0;
	}
	public Rational(long num,long den){
		this.num=num;
		this.den=Math.abs(den);
                if(den!=0)
                    reduce();
		
	}
	public void reduce(){
            if(den==0 || num==0)
                return;
		if(den%num==0){
			long newDen=den/num;
			den=Math.abs(newDen);
			num=num/Math.abs(num);
		}
		else
			checkFactors();
		
	}
	private void checkFactors(){
		long top=num; 
		long bot=den;
		long small=num<den?num:den;
		small=Math.abs(small);
		for(long x=small/2;x>1;x--){
			if(num%x==0 && den%x==0){
				top/=x;
				bot/=x;
			}
		}
		num=top;
		den=bot;
	}
	public Rational add(Rational r){
		long topThis=this.num*r.den;
		long bottomThis=this.den*r.den;
		
		long topThat=r.num*this.den;
		long bottomThat=den*this.den;
		
		long newTop=topThis+topThat;
		return new Rational(newTop,bottomThis);
		
	}
	public Rational multiply(Rational r){
		long top=this.num*r.num;
		long bot=this.den*r.den;
		return new Rational(top,bot);
		
	}
	public Rational divide(Rational r){
		long top=this.num*r.den;
		long bot=this.den*r.num;
		return new Rational(top,bot);
	}
	public Rational subtract(Rational r){
		long topThis=this.num*r.den;
		long bottomThis=this.den*r.den;
		
		long topThat=r.num*this.den;
		long bottomThat=den*this.den;
		
		long newTop=topThis-topThat;
		return new Rational(newTop,bottomThis);
	
	}

        public Rational reverse(){
            return new Rational(den,num);
        }
        public Rational switchSigns(){
            return new Rational(-num,den);
        }

        public boolean equals(Rational r){
            if(this.num==r.num && this.den==r.den)
                return true;
            return false;
        }

	public String toString(){
            if(den==1 || den==0 || num==0)
                return num+" ";
            return num+"/"+den;
	}
	/*
	
	public equals(){
	
	}
	public compareTo(){
	}
	*/
}
