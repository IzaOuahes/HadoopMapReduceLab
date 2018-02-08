package read.compact;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadcompactFile {

    public static void main(String[] args) {

        String File = args[0];
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(File))) {
        	
        	int curLineNr = 1;
        	int skipLines = 22;
            while ((line = br.readLine()) != null) {
            	
            	if (curLineNr++ <= skipLines) {
                    continue;
                }
                // use comma as separator
                String USAF = line.substring(0,6);
                String name = line.substring(13,42);
                String country = line.substring(43,45);
                String elevation = line.substring(74,81);

                System.out.println("History [USAF= " + USAF + " , name=" + name + " , country=" + country + " , elevation=" + elevation + "]");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}