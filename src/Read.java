import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Read {
	Controller Ctr;
	
	public Read(Controller ctr) {
		this.Ctr = ctr;
	}
	
	public void readLines(String filename) throws IOException {
	        /*
	        Methode zum erkennen der relevanten Zeilen der LST-Datei
	        Liste mit allen Befehlsziffern (zweite Spalte der Original Datei) wird zurückgegeben
	         */
	        FileReader fileReader = new FileReader(filename);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);     //Die Datei wird mit dem BufferedReader gelesen
	        int[] progCounter = Ctr.Mem.getProgcount();                         //Programmspeicher wird erstellt
	        

	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {                //Solange es noch Zeilen gibt lies diese ein
	            if (!line.substring(0, 1).equals(" ")) {                        //Wenn der Anfang einer Zeile Ungleich Leerzeichen ist,
	                String addresse = line.substring(0, 4);
	                int intaddr = Integer.parseInt(addresse, 16);

	                String befehl = line.substring(5, 9);                       //füge zum Array nur die vier wichtigen Befehlsziffern hinzu
	                int intbefehl = Integer.parseInt(befehl, 16);

	                progCounter[intaddr] = intbefehl;
	            }
	        }
	        bufferedReader.close();
	        for (int i = 0; i < 1024; i++) {
	            if (progCounter[i] != 0) {
	                System.out.println(progCounter[i]);
	            }
	        }
	        Ctr.Mem.setProgcount(progCounter);                                            //fertiges Array mit allen Befehlsziffern zurückgegeben
	}
}
