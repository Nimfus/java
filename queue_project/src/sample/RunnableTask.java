package sample;

import java.time.LocalDate;
import java.util.ArrayList;

public class RunnableTask implements Runnable {

    private Thread thread;

    private String email;

    private LocalDate dateFromValue;

    private LocalDate dateToValue;

    RunnableTask(String email, LocalDate dateFrom, LocalDate dateTo) {
        this.email = email;
        this.dateFromValue = dateFrom;
        this.dateToValue = dateTo;
    }

    @Override
    public void run() {
        ArrayList<String> links;

        PageParser parser = new PageParser(dateFromValue, dateToValue);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                links = parser.getLinks();
                System.out.println(links);
                Mailer mailer = new Mailer();
                mailer.generateAndSendEmail(email, links);
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void start()
    {
        System.out.println("Starting....");
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
            System.out.println("Started");
        }
    }

    void stop()
    {
        System.out.println("Stopping...");
        if (thread != null) {
            thread.interrupt();
            System.out.println("Stopped");
        }
    }
}
