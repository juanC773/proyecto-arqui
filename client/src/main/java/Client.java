import Demo.PrinterPrx;

public class Client
{
    public static void main(String[] args)
    {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "config.client"))
        {
            // Obtener un proxy al servicio de consulta
            com.zeroc.IceGrid.QueryPrx query =
                    com.zeroc.IceGrid.QueryPrx.checkedCast(communicator.stringToProxy("DemoIceGrid/Query"));

            if (query == null)
            {
                throw new Error("No se pudo conectar al servicio de consulta");
            }

            // Localizar el adaptador "VotosAdapter"
            com.zeroc.Ice.ObjectPrx base = query.findObjectByType("::Demo::Printer");
            PrinterPrx printer = PrinterPrx.checkedCast(base);

            if (printer == null)
            {
                throw new Error("No se pudo localizar el objeto votos");
            }

            // Invocar el método remoto
            Demo.Response response = printer.printString("Hola desde el cliente con Query Service!");
            System.out.println("Respuesta del servidor: " + response.value);
            System.out.println("Tiempo de respuesta: " + response.responseTime);
        }
    }
}
