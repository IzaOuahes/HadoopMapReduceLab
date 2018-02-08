package display.tree;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tree {

    public static void main(String[] args) {

        //String csvFile = "data/arbres.csv";
        String csvFile = args[0];
    	String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] arbre = line.split(";");

                System.out.println("Arbre [year= " + arbre[5] + " , height=" + arbre[6] + "]");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}