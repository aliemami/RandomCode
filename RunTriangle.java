import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.StringTokenizer;


public class RunTriangle {
	public static void main(String [] args) {
		try {
			System.out.print("Enter the Path to the Input File: ");		
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String filepath = null;
			filepath = br.readLine();
			System.out.println("You Entered: : " + filepath);

			Triangle tri = new Triangle();
			@SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			long startTime = (new Date()).getTime();

			String ln="";

			while ((ln = in.readLine() )!= null) {
				if(ln.trim().length() > 0) {
					StringTokenizer st = new StringTokenizer(ln);
					while(st.hasMoreTokens()){
						String token =  st.nextToken();
						tri.insert(Long.valueOf(token));
					}
				}
			}

			long endTime = (new Date()).getTime();
			long duration = ((endTime - startTime));
			System.out.println("Building the structure took:" + duration + " milisec.");

			System.out.println("");

			startTime = (new Date()).getTime();
			tri.calcWeight();
			endTime = (new Date()).getTime();
			duration = ((endTime - startTime));
			System.out.println("Calculating weight took :" + duration + " milisec.");

			System.out.println("");

			startTime = (new Date()).getTime();
			System.out.println("Maximum value: " + tri.calcPathMaximum() );
			System.out.println("Minimum value: " + tri.calcPathMinimum() );
			System.out.println("");

			endTime = (new Date()).getTime();
			duration = ((endTime - startTime));
			System.out.println("Showing path to min/max took :" + duration + " milisec.");
		} catch (FileNotFoundException e) {
			System.out.println("File not found - try again");
		} catch (NumberFormatException e) {
		} catch (IOException e) {
		}
	}
}
