
import java.util.Scanner;

public BiolerPlate{

	static String NL = "\n";

	public static String createMain( ArrayList<String> list ){

		String s = "";
		s += "" +
		"public class GDLMain {" + NL +
		"static HashMap<String, String> closedList = new HashMap<String, String>();" + NL +
		"static HashMap<String, AbstractState> allStatesTable = new HashMap<String,AbstractState>();" + NL +
		" static { " + NL;
		for( String state : list ){
			s += "allStatesTable.put(\"" + state + "\" , new " + state + "())" + NL;
		}
		s += " }" + NL;


		// Output MainMethodBoilerPlate w/ trace
		try {
			Scanner scan = new Scanner(new File("MainMethodBP.java"));
			scan.useDelimiter("\n");

			while(scan.hasNext()){
				s += scan.next() + NL;
			}

		} catch (FileNotFoundException ex) {
		   System.out.println(ex);
		}
        return "";

	}

	public static String createGraph(String name, String params, String stmts){

		String s = "" +
		"import java.util.ArrayList;" + NL +
		"public class " + name + " extends AbstractState{" + NL +
		"	public " + name + "(){" + NL +
		"		actualName = \"" + name + "\";" + NL +
		"	}" + NL +
		"	@Override " + NL +
		"	public ArrayList<String> getChildrenStates() { " + NL +
		" 		ArrayList<String> children = new ArrayList<String>(); " + NL +
		"		" + stmts +
		"		return children " + NL +
		"	} " + NL +
		"}" + NL;
        return s;

	}
}