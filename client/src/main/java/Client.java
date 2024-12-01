import Demo.ObserverPrx;
import Demo.SubjectPrx;
import Demo.Response;

public class Client {
    public static void main(String[] args) {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "config.client", extraArgs)) {
            // Obtener el proxy del sujeto
            Demo.SubjectPrx subject = Demo.SubjectPrx.checkedCast(
                    communicator.propertyToProxy("Subject.Proxy"));

            if (subject == null) {
                throw new Error("No se pudo conectar al sujeto");
            }

            // Crear un adaptador local para el observador
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("");
            Demo.ObserverPrx observer = Demo.ObserverPrx.uncheckedCast(
                    adapter.addWithUUID(new ObserverI())
            );

// Activar el adaptador
            adapter.activate();

// Registrar el observador en el sujeto
            subject.attach(observer);
            System.out.println("Cliente registrado como observador");

            // Obtener el proxy del servicio Printer y usarlo
            Response response = null;
            Demo.PrinterPrx service = Demo.PrinterPrx.checkedCast(
                    communicator.propertyToProxy("Printer.Proxy"));

            if (service == null) {
                throw new Error("Invalid proxy for Printer");
            }

            // Realizar una llamada al servicio Printer
            response = service.printString("Hello World from a remote client!");
            System.out.println("Respuesta del servidor: " + response.value + ", " + response.responseTime);

            // Mantener el cliente activo para recibir notificaciones
            communicator.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
