import com.zeroc.Ice.Current;

public class ObserverI implements Demo.Observer {
    @Override
    public void _notify(String message, String eventType, Current current) {
        System.out.println("Notificación recibida: [" + eventType + "] " + message);
    }
}
