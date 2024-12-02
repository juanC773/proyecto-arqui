import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;

import Demo.Response;
import com.zeroc.Ice.Util;

public class Client
{
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.client",extraArgs))
        {
            Demo.PrinterPrx service = Demo.PrinterPrx
                    .checkedCast(communicator.propertyToProxy("Printer.Proxy"));
            
            ObjectAdapter adapter = communicator.createObjectAdapter("callback");
            Demo.Callback callback = new MyCallback();
            ObjectPrx prx = adapter.add(callback, Util.stringToIdentity("callback"));
            Demo.CallbackPrx callbackPrx = Demo.CallbackPrx.checkedCast(prx);
            adapter.activate();
            
            if(service == null)
            {
                throw new Error("Invalid proxy");
            }
            
            try {
                String path = "..\\resources\\cedulas.txt";
                //String path = "../resources/cedulas.txt";
                BufferedReader reader = new BufferedReader(new FileReader(path));
                String cedula;
                while ((cedula = reader.readLine()) != null) { 
                    final String cedulaFinal = cedula;                   
                    // Send the request asynchronously
                    CompletableFuture.runAsync(() -> {
                        try {
                            service.printString( System.getProperty("user.name") + " " + cedulaFinal, callbackPrx);
                        } catch (Exception e) {
                            System.err.println("Error sending request: " + e.getMessage());
                        }
                    }, executorService);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //service.printString("Hello World from a remote client!", callbackPrx);

        }
    }
}