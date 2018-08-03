import java.util.Scanner;

public class TestCarWithOOP {

	public static void main(String[] args) {
		CarWithOOP[] carArray = new CarWithOOP[10];
		for (int i=0; i< carArray.length; i++){
			carArray[i] = new CarWithOOP();
		}
		
		Scanner input = new Scanner(System.in);
		
		while (true) {
			System.out.println("Which car would you like to use next? (choose a car from 1-10)");
			
			int userInputCar = input.nextInt();
			
			int carIndex = userInputCar - 1;
						
			System.out.println("What would you like to do?");
			System.out.println("1: turn the ignition on/off");
			System.out.println("2: change the position of car");
			System.out.println("Q: quit this program");

			char userInput = input.next().charAt(0);

			if (userInput == 'Q') {
				break;
			}
			
			else if (userInput == '1') {
				// call ignition function
				carArray[carIndex].setIgnition(carArray[carIndex].ignition());
				}

			else if (userInput == '2') {
				System.out.println("In which direction would you like to move the car?");
				System.out.println("H: horizontal");
				System.out.println("V: vertical");
				char direction = input.next().charAt(0);

				if (direction == 'H') {
					System.out.println("How far (negative value to move left)?");
					int howFarH = input.nextInt();
					carArray[carIndex].setPositionX(carArray[carIndex].moveHorizontal(howFarH));
				} else if (direction == 'V') {
					System.out.println("How far (negative value to move up)?");
					int howFarV = input.nextInt();
					carArray[carIndex].setPositionY(carArray[carIndex].moveVertical(howFarV));
				}	
			} else {
				System.out.println("You did not enter a correct input");
			}
			
                        System.out.println(carArray[carIndex].toString());
	
	System.out.println(carArray[carIndex].getIgnition());
	System.out.println(carArray[carIndex].getXPosition());
	System.out.println(carArray[carIndex].getYPosition());
	System.out.println(carArray[carIndex].getColor());
		}

	
	}
	
}
