package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;

public class Record {

    public static void main(String[] args) {

    }

    // get the casanova object and write it to the file 
    public static void writeToFile(Casanova casanova) {
        try {
            File myFile = new File("currentUser.txt");
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            FileWriter fw = new FileWriter(myFile.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(casanova.toString());
            bw.newLine();
            bw.close();
            fw.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // read the contents from the file, and put them into the treeset for sorting
    public static Casanova readFromFile() {
        TreeSet<Casanova> ply1 = new TreeSet<Casanova>();
        Casanova str = new Casanova();
        try {
            File record1 = new File("currentUser.txt");
            if (!record1.exists()) {
                throw new FileNotFoundException("File does not exist");
            }
            FileReader fr = new FileReader(record1.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String s;

            while ((s = br.readLine()) != null) {

                String[] tmp = new String[2];
                tmp = s.split(", ");
                ply1.add(new Casanova(tmp[0], Integer.parseInt(tmp[1])));
                System.out.println(s);

                System.out.println("C size : " + ply1.size());

            }

            System.out.println("A size : " + ply1.size());
            for (Casanova u : ply1) {
                System.out.println("0006 : " + u);
            }
            str = ply1.last();

            br.close();
            fr.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return str;
    }
}
