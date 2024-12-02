import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;

import Demo.Response;
import com.zeroc.Ice.Util;

public class Client
{
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
            service.printString("Hello World from a remote client!", callbackPrx);

        }
    }
}