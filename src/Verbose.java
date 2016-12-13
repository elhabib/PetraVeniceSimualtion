
public class Verbose {
	
	static boolean v = false;
	
	public static void out(String s){
		if (v)
			System.out.println(s);
	}
}
