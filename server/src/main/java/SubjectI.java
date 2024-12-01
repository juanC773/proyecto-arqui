import java.util.List;
import java.util.ArrayList;

public class SubjectI implements Demo.Subject {
    // Lista sincronizada de observadores
    private final List<Demo.ObserverPrx> observers = new ArrayList<>();

    @Override
    public synchronized void attach(Demo.ObserverPrx observer, com.zeroc.Ice.Current current) {
        observers.add(observer);
        System.out.println("Observador agregado: " + observer);
    }

    @Override
    public synchronized void detach(Demo.ObserverPrx observer, com.zeroc.Ice.Current current) {
        observers.remove(observer);
        System.out.println("Observador eliminado: " + observer);
    }

    @Override
    public synchronized void notifyObservers(String message, String eventType, com.zeroc.Ice.Current current) {
        System.out.println("Notificando a los observadores con evento: " + eventType);
        for (Demo.ObserverPrx observer : observers) {
            try {
                // El metodo notify ahora debe ser válido
                observer._notify(message, eventType);
            } catch (Exception e) {
                System.err.println("Error notificando a un observador: " + e.getMessage());
                observers.remove(observer); // Manejo de desconexiones
            }
        }
    }
}
