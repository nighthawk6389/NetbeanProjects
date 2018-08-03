
public class CarWithOOP {
	private boolean ignition;
	private int positionX;
	private int positionY;
	private String color;

	//constructor
	public CarWithOOP(){
		this.positionX = (int) (Math.random() * 20 + 1);
		this.positionY = (int) (Math.random() * 20 + 1);
		this.ignition = false;
		this.color = color();
				
	}
	
	public String color(){
		String[] carColor = { "Red", "Green", "Blue", "White", "Silver"};
		int colorIndex = (int) (Math.random() * 5);
		String randColor = carColor[colorIndex];
		return randColor;	
	}
	
	public int moveHorizontal(int howFar){
		if (ignition == false) {
			System.out.println("You must first turn the ignition on before moving the car");
			return this.positionX;
		} else {
			if ((this.positionX + howFar > 20) || (this.positionX + howFar <= 0)) {
				System.out.println("You are attempting to move the car beyond the grid");
				return this.positionX;
			} else {
				this.positionX = positionX + howFar;
				return this.positionX;
			}
		}
	}
	

	public int moveVertical(int howFar){
		if (this.ignition == false) {
			System.out.println("You must first turn the ignition on before moving the car");
			return this.positionY;
		} else {
			if ((this.positionY + howFar > 20) || (this.positionY + howFar <= 0)) {
				System.out.println("You are attempting to move the car beyond the grid");
				return this.positionY;
			} else {
				this.positionY = this.positionY + howFar;
				return this.positionY;
			}
		}
	}
	

	public boolean ignition(){
		boolean updatedIgnition = (!this.ignition);
		return updatedIgnition;
	}

	public String getColor(){
		return this.color;
	}
	
	public boolean getIgnition(){
		return this.ignition;
	}
	
	public int getXPosition(){
		return this.positionX;
	}
	
	public int getYPosition(){
		return this.positionY;
	}
	
        
        public void setIgnition(boolean ignition) {
            this.ignition = ignition;
        }

        public void setPositionX(int positionX) {
            this.positionX = positionX;
        }

        public void setPositionY(int positionY) {
            this.positionY = positionY;
        }

        public void setColor(String color) {
            this.color = color;
        }
	
        
	public String toString(){
		String ignitionStatus = "";
		if (this.ignition) {
			ignitionStatus = "on";
		} else{
			ignitionStatus = "off";
		}
		String firstLetter = Character.toString(getColor().charAt(0));	

		String str = ("Car Stats" + "\nColor: " + getColor()+ "\nIgnition: " + ignitionStatus + "\nLocation: (" 
		+ getXPosition() + "," + getYPosition() + ")");
		
		
		
		for (int i = 1 ; i <= 20; i++){
			for( int k = 1; k <=20; k++) {
				if ((k == getXPosition()) && (i == getYPosition())){
					str += (firstLetter + " ");
				} else{
					str += ("- ");
				}
			}
			str += "\n"; //newline
		}
		
		return str;
		
		/*
		System.out.println("Car Stats");
		System.out.println("Color: " + getColor());
		System.out.println("Ignition: " + ignitionStatus);
		System.out.println("Location: (" + getXPosition() + "," + getYPosition() + ")");
		for (int i = 1 ; i <= 20; i++){
			for( int k = 1; k <=20; k++) {
				if ((k == getXPosition()) && (i == getYPosition())){
					System.out.print(firstLetter + " ");
				} else{
					System.out.print("- ");
				}
			}
		System.out.println("");
		
		
		
		}
		*/
	
	}
	
}
