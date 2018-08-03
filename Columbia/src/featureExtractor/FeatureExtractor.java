/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureExtractor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @author Ilan, Joe
 */
public class FeatureExtractor {
	/*
	 * 17 Features:
	 * Number of // comments
	 * Number of block comments
	 * Number of lines
	 * Number of if condition statements
	 * Number of while loops
	 * Number of for loops
	 * Number of single "{" on a line situation
	 * Number of all spaces
	 * Number of CSS in if conditon, while and for loops between:
	 * 1) assignment
	 * 2) equality
	 * 3) oen parens left and right
	 * 4) close parens left and right
	 */
	
	static ConstructParser c = new ConstructParser();

	static int numOfLines = 0;
	static int whileLoopCount = 0;
	static int forLoopCount = 0;
	static int comments = 0;
	static int blockComments = 0;
	static int ifCount = 0;
	static int classes = 0;
	static int bracketCount = 0;
	static int allSpaces = 0;
	static int codingStyle = 0;
	static int functions = 0;
	static int fields = 0;

	public static void main(String args[]) throws IOException, ClassNotFoundException
	{
		String singleComm = "//";
		String startBlockComm = "/*";
		String endBlockComm = "*/";

		String bracket = "{"; 
		/* 
		 * Used to check for situation where someone has the 
		 * following coding style:
		 * 	if(condition)	rather than if(condition){
		 * 	{
		 * 
		 * 	OR
		 * 
		 * 	while(condition)
		 * 	{
		 * 
		 * 	OR
		 * 
		 * 	public foo()
		 * 	{
		 */

		writeHeader();
		//String pseudonym = args[0];
                String directory = args[1];
                File data = new File(directory);
                if( data == null){
                    System.out.println("BAD data: " + directory);
                    System.exit(1);
                }
                File [] people = data.listFiles();
                if( people == null){
                    System.out.println("BAD people: " + directory);
                    System.exit(1);
                }
                for(int peopleCount = 0; peopleCount < people.length; peopleCount++){
                    File person = people[peopleCount];
                    if( person == null){
                        System.out.println("BAD Person: " + directory);
                        System.exit(1);
                    }
                    
                    String pseudonym = person.getName();
                    
                    File [] folders = person.listFiles();
                    if( folders == null){
                        System.out.println("BAD folder: " + directory);
                        System.exit(1);
                    }
                    for(int folderCount = 0; folderCount < folders.length; folderCount++){
                        File [] dir = folders[folderCount].listFiles();
                        if( dir == null){
                            System.out.println("BAD dir: " + directory);
                            System.exit(1);
                        }

                        c = new ConstructParser();

                        numOfLines = 0;
                        whileLoopCount = 0;
                        forLoopCount = 0;
                        comments = 0;
                        blockComments = 0;
                        ifCount = 0;
                        classes = 0;
                        bracketCount = 0;
                        allSpaces = 0;
                        codingStyle = 0;
                        functions = 0;
                        fields = 0;


                        for(int i = 0; i< dir.length; i++){
                                System.out.println("\n\nLooking at file: " + i + " " + dir[i].getName());

                                if( dir[i].isDirectory() ){
                                    System.out.println("Skipping " + i);
                                    continue;
                                }

                                FileInputStream fstream = new FileInputStream(dir[i].getAbsolutePath());
                                DataInputStream in = new DataInputStream(fstream);
                                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                                String strLine;
                                while ((strLine = br.readLine()) != null) {

                                        //System.out.println("Line currently being looked at: " + strLine);

                                        numOfLines++;
                                        //allSpaces += countAllSpaces(strLine);

                                        /*
                                         * Count number of comments/blockComments
                                         */
                                        if(strLine.contains(singleComm)) {
                                                //System.out.println(strLine);
                                                comments++;

                                                //line is a comment only
                                                if(strLine.trim().startsWith("//")){
                                                        //System.out.println("Comment only line: " + strLine);
                                                        continue;
                                                }
                                        } else if(strLine.contains(startBlockComm) && strLine.contains(endBlockComm)){
                                                blockComments++;
                                                //System.out.println(strLine);

                                                /*
                                                 * We know the line starts with /* but if it contains * / also
                                                 * then is must be a single line comment and we can skip it
                                                 */
                                                if(strLine.trim().startsWith("/*") && strLine.trim().endsWith("*/")){
                                                        System.out.println("!!!!!!!!!!!!!!!!!!!!!Skipped line: " + strLine);
                                                        continue;
                                                }
                                        }

                                        /*
                                         * Check for line that contains "{" as the only
                                         * piece of code
                                         */
                                        if(singleOpenBracket(strLine, bracket)){
                                                bracketCount++;
                                                continue;
                                        }


                                        /*
                                         * Get the coding style spaces
                                         * If needed, we can have individual features:
                                         * one for the if condition
                                         * one for the for loop
                                         * one for the while loop
                                         * As of now they are summed up together as a single sum
                                         */
                                        strLine = removeComment(strLine);
                                        codingStyle += test(strLine);

                                        /* 
                                         * Put this after eveything else because checking the situation 
                                         * where a block comment exists after some code is a little tricky
                                         * and I did it after everything else
                                         * Counts number of comments
                                         * If it sees a block comment, it will skip lines until the
                                         * end of the block comment is found
                                         */
                                        if(strLine.contains(startBlockComm)  && !strLine.contains(endBlockComm)){
                                                blockComments++;
                                                //System.out.println(strLine);
                                                /*
                                                 * Skip the lines in the block comment until we see a * / 
                                                 */
                                                for(;;){
                                                        strLine = br.readLine();
                                                        //System.out.println(strLine);
                                                        if(strLine.contains("*/")){
                                                                break;
                                                        }
                                                }
                                        }

                                }
                                in.close();
                                //checkFunction(args[0], args[i]);
                                String s = "comments: " + comments + "\nblockComments: " + blockComments + "\nnumOfLines: " + numOfLines + "\nwhileLoop: "
                                                + whileLoopCount + "\nforLoop: " + forLoopCount + "\nifCount: " + ifCount + "\nclasses: "
                                                + classes + "\nbracketCount:" + bracketCount + "\nall spaces: " + allSpaces + "\ncoding style spaces: " + codingStyle; 		
                                System.out.println(s);		
                        }
                        writeData(getClassData(pseudonym));
                        //System.exit(1);
                    }
                }
	}

	public static String helpRemoveComment(String s){
		if(s.length() > 0){
			//System.out.println("Should be some code here: " + s);
			//now need to check if comment comes after code
			if(s.endsWith("*/")){
				int index2 = s.lastIndexOf("/*");
				s = s.substring(0, index2-1).trim();
				//System.out.println("Code after comment removed. Code: " + s);
			}else{
				for(int i = 0; i<s.length()-1; i++){
					//System.out.println("looking at: " + temp.charAt(i));
					if(s.charAt(i) == '/' && s.charAt(i+1) == '/'){
						//System.out.println("subsequence == //");
						if(s.charAt(i-1) == '"' && s.charAt(i+2) == '"'){
							//came across false comment so skip
							//something like System.out.println("//");
							continue;
						}else{
							//now need to remove comment
							s = s.substring(0, i-1).trim();
							//System.out.println("Code after ending in //: " + s);
							break;
						}
					}
				}
			}
		}
		//System.out.println("final string: " + s);
		return s;
	}
	//Check for the following situations
	// 1) /* comment */ int a = b;
	// 2) /* comment */
	// 3) /* comment */ int a = b; /* comment */
	// 4) /* comment */ int a = b; //comment
	// 5) int a = b;
	// 6) int a = b; /* comment */
	// 7) int a = b; //comment
	// 8) //int a = b; //comment
	public static String removeComment(String s){
		//System.out.println("Parse Comments");
		//System.out.println("String: " + s);
		//block comment on same line as code, but before the code: /* */ code
		if(s.startsWith("/*")){
			int index = s.indexOf("*/");
			if(index == -1){
				//block comment possibly spanning multiple lines
				return s;
			}
			String temp = s.substring(index+2).trim();
			return helpRemoveComment(temp);		
		} else{
			return helpRemoveComment(s);
		}
	}

	/*
	 * test for line that contains only this string "{" as in:
	 * if (condition)
	 * { <-----
	 */
	public static boolean singleOpenBracket(String s, String bracket){
		s = s.trim();
		if(s.startsWith("{")){
			//System.out.println("started with {");
		}
		if(s.equals(bracket)){
			//System.out.println("OPEN BRACKET FOUND!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			return true;
		}

		return false;
	}

	/*
	 * Counts all spaces, including those in comments
	 */
	public static int countAllSpaces(String s){
		int num = 0;
		for (int i = 0; i < s.length(); i++){
			if(s.charAt(i) == ' ')
				num++;
		}
		return num;
	}

	/*
	 * Checking coding style spaces
	 */ 
	public static int test(String s){
		//System.out.println("In Test");
		//System.out.println("String: " + s);
		//System.out.println("Coding style spaces: " + codingStyle(s)); 
		return codingStyle(s);
	}

	/*
	 * Check for situation where coding style is like the following:
	 * if(condition) 
	 * OR
	 * if(condition)
	 * OR
	 * if (condition)
	 * OR
	 * if ( condition )
	 * etc.
	 * Does the for while and for loops as well
	 */
	public static int codingStyle(String s){
		//System.out.println("check coding style");
		if(s.trim().startsWith("if ") || s.trim().startsWith("if(")){
			//System.out.println("IF: String: " + s);
			ifCount++;
			return c.check_if(s);
		}else if(s.trim().startsWith("for ") || s.trim().startsWith("for(")){
			//System.out.println("FOR: String: " + s);
			forLoopCount++;
			return c.check_for(s);
		}else if(s.trim().startsWith("while ") || s.trim().startsWith("while(")){
			//System.out.println("WHILE: String: " + s);
			whileLoopCount++;
			return c.check_while(s);
		}

		/* 
		 * Check for assignment and equality spacing
		 */
		String temp  = new String(s);
		int [] assignEqArray;
		int assignIndex = -1; // This needs to be -1
		int stretch_right = 0;

		do {
			temp            = temp.substring(assignIndex + stretch_right + 1);
			assignEqArray   = c.checkAssignmentOrEquality(temp);
			assignIndex     = assignEqArray[0];
			stretch_right   = assignEqArray[1];
		} while( assignIndex != -1 );

		return 0;
	}

	//probably will be deleted
	public static void checkFunction(String packName, String s) throws ClassNotFoundException {
		//pass in args[0] here too
		//String packName = "featureExtractor.";
		packName = packName.concat(".");
		//System.out.println("packName: " + packName);
		//System.out.println("Before sub: " + s);
		int index = s.lastIndexOf("/");
		String className = s.substring(index+1, s.length());
		index = className.indexOf('.');
		className = className.substring(0, index);
		//System.out.println("After sub: " + className);

		String fullPath = packName.concat(className);
		//System.out.println("After concat: " + fullPath);


		Class<?> cls = Class.forName(fullPath); 
		functions += cls.getDeclaredMethods().length;
		classes += cls.getClasses().length;
		fields += cls.getDeclaredFields().length;
		//System.out.println("functions: " + functions + "\nclasses: " + classes + "\nfields: " + fields);
	}

	public static String getFileHeader(){
		return "Class\t\tComments\tBComments\tNumOfLines\tWhileCount\tForCount\tIfCondCount\tBracketCount\tAllSpaces\tCSS" +
				"\tAssignment\tAssignOpp\tEquality\tEqualityOpp\tOpenParenL\tOpenParenR\tOpenParenOpp\tCloseParenL\tCloseParenR\tCloseParenOpp" +
                                "\tIfParen\tIfParenCond\tIfCondParen\tWhileParen\tWhileParenCond\tWhileCondParen" +
                                "\tForParen\tForParenVar\tForVarSemi\tForSemiCond\tForCondSemi\tForSemiInc\tForIncParen\tForParenBracket";
	}

	public static String getClassData(String pseudonym){
		return pseudonym + "\t" + comments + "\t\t" + blockComments + "\t\t" + numOfLines + "\t\t" + whileLoopCount + "\t\t" + forLoopCount + "\t\t" + ifCount + 
				"\t\t" + bracketCount + "\t\t" + allSpaces + "\t\t" + codingStyle + "\t\t" + c.assignment + "\t\t" + c.assignmentOpp + 	"\t\t" + c.equality +
				"\t\t" + c.equalityOpp + "\t\t" + c.open_paren_left + "\t\t" + c.open_paren_right + "\t\t" + c.open_parenOpp + 
                                "\t\t" + c.close_paren_left + "\t\t" + c.close_paren_right + "\t\t" + c.close_parenOpp + "\t\t" + c.if_ifParen + 
                                "\t\t" + c.if_parenCondition + "\t\t" + c.if_conditionParen + "\t\t" + c.while_whileParen + "\t\t" + c.while_parenCondition +
                                "\t\t" + c.while_conditionParen + "\t\t" + c.for_forParen + "\t\t" + c.for_parenVariable + "\t\t" + c.for_variableSemi+
                                "\t\t" + c.for_semiCondition + "\t\t" + c.for_conditionSemi + "\t\t" + c.for_semiIncrement  + "\t\t" + c.for_incrementParen +
                                "\t\t" + c.for_parenBracket;
	}

	public static void writeHeader() throws IOException{
		File file = new File("features.txt");
		if (!file.exists()) {
			file.createNewFile();
		}

		double bytes = file.length();
		if(bytes > 0){
			return;
		}

		FileWriter fw = new FileWriter("features.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(getFileHeader());  
		bw.newLine();
		bw.close();

		System.out.println("\nDone writing header\n");
	}

	public static void writeData(String s) throws IOException{
		FileWriter fw = new FileWriter("features.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(s);
		bw.newLine();
		bw.close();

		System.out.println("\nDone writing data\n");
	}
}
