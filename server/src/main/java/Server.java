import Demo.Response;

public class Server
{
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, extraArgs))
        {
            if(!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                for(String v : extraArgs)
                {
                    System.out.println(v);
                }
            }

            // Configura el adaptador replicado gestionado por IceGrid
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("VotosAdapter");
            com.zeroc.Ice.Object object = new PrinterI();
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("votos"));
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
