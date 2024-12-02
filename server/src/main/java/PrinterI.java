import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import Demo.CallbackPrx;
import Demo.Response;

public class PrinterI implements Demo.Printer
{   
    PrimeFactorAnalyzer primeFactorAnalyzer = new PrimeFactorAnalyzer();
    Dao dao = new Dao();

    public void printString(String s, CallbackPrx callback, com.zeroc.Ice.Current current) {
        Long startTime = System.currentTimeMillis();
        try {
        String [] query = s.split(" ");
        int isprime = primeFactorAnalyzer.processNumber(query[1]);
        String result = dao.consultarMesaVotacion(query[1]);
        Long totalTime = System.currentTimeMillis();
        totalTime = (totalTime-startTime)/1000;
        writeToFile(query[0] + query[1] + result + "Prime value: " + isprime + totalTime + " segundos");
        callback.reportResponse(new Response(0, "Server response: " + s));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String data) {
        try {
            String path = "..\\resources\\server_result.txt";
            //String path = "../resources/server_result.txt";
            FileWriter fileWriter = new FileWriter(path, true); 
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
            printWriter.println(data);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

