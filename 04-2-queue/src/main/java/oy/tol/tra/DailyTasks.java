package oy.tol.tra;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class showing your daily schedule using a timer.
 */
public class DailyTasks {

    private static final int TASK_DELAY_IN_SECONDS = 1000;
    private QueueInterface<String> dailyTaskQueue = null;
    private Timer timer = null;

    private DailyTasks() {
    }

    /**
     * Execute from the command line:  <code>java -jar target/04-queue-1.0-SNAPSHOT-jar-with-dependencies.jar</code>
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        DailyTasks tasks = new DailyTasks();
        tasks.run();
    }

    private void run() {
        try {
            // 1. create a queue (to the member variable!) for daily tasks, which are strings.
            dailyTaskQueue = QueueFactory.createStringQueue(10);
            // 2. read the tasks for today by calling readTasks() -- implementing missing parts of it!
            readTasks();
            // 3. create Java Timer object (to member variable!!) to schedule your daily tasks.
            timer = new Timer();
            // 4. schedule the timer at fixed rate (!!!!) with a new TimerTask,
            //  using the delay constant values in the class member variable.
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (dailyTaskQueue.isEmpty()) {
                        timer.cancel();
                    } else {
                        try {
                            System.out.println(dailyTaskQueue.dequeue());
                        } catch (QueueIsEmptyException e) {
                            timer.cancel();
                        }
                    }
                }
            }, TASK_DELAY_IN_SECONDS, TASK_DELAY_IN_SECONDS);
        } catch (IOException e) {
            System.out.println("Something went wrong :( " + e.getLocalizedMessage());
        }
    }

    private void readTasks() throws IOException {
        String tasks;
        tasks = new String(getClass().getClassLoader().getResourceAsStream("DailyTasks.txt").readAllBytes());
        String[] allTasks = tasks.split("\\r?\\n");
        for (String task : allTasks) {
            dailyTaskQueue.enqueue(task);
        }
        System.out.println("Number of tasks: " + dailyTaskQueue.size());
    }

}
