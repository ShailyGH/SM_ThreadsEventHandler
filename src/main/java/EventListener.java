public class EventListener implements Runnable{

    private String messageToListenFor;
    private String messageToReplyWith;
    private Tracker eventTracker;

    public EventListener(String message, String reply) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = EventTracker.getInstance();
    }

    public EventListener(String message, String reply, Tracker tracker) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = tracker;
    }

    public void run()
    {
        try
        {
            while (true)
            {
                if (shouldReply())
                {
                    System.out.println(this.messageToReplyWith);
                }
                Thread.sleep(500);
            }
        }
        catch (InterruptedException e)
        {
            if (readyToQuit())
            {
                System.out.println("Ready to Quit");
            }
            else
            {
                while (!readyToQuit())
                {
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException ex)
                    {
                        System.out.println("Not Ready to Quit");
                    }
                }
            }
        }
    }

    public Boolean readyToQuit()
    {
        if(!Thread.currentThread().isInterrupted())
        {
            Thread.currentThread().interrupt();
            return Thread.currentThread().isInterrupted();
        }
        else
        {
            return !Thread.currentThread().isAlive();
        }
    }

    public Boolean shouldReply()
    {
        return this.eventTracker.has(this.messageToListenFor);
    }

    public void reply()
    {
        System.out.println(this.messageToReplyWith);
        eventTracker.handle("", () -> {});
    }
}