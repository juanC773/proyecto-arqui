import Demo.CallbackPrx;
import Demo.Response;

import com.zeroc.Ice.Current;

public class PrinterI implements Demo.Printer
{
    public void printString(String s, CallbackPrx callback, com.zeroc.Ice.Current current){
        System.out.println(s);
        callback.reportResponse(new Response(0, "Server response: " + s));
    }

}

