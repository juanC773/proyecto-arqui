module Demo
{
    class Response{
        long responseTime;
        string value;
    }

    interface Callback{
        void reportResponse(Response response);
    }

    interface Printer
    {
        void printString(string s, Callback* callback);
    }
}