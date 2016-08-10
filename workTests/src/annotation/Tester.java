package annotation;

import com.pack.test.format.FormatFixer;

public class Tester {
	@Example
	FormatFixer fixer;
	
	public static void main(String args[]){
		System.out.println("Done!");
		new Tester();
	}
}
