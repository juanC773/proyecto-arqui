module Demo {
    class Response {
        long responseTime;
        string value;
    }
    interface Observer {
        void notify(string message, string eventType);
    }
    interface Subject {
        void attach(Observer* observer);
        void detach(Observer* observer);
        void notifyObservers(string message, string eventType);
    }
    interface Printer {
        Response printString(string s);
    }
}
