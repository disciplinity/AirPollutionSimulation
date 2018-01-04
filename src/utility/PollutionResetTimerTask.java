package utility;

/**
 * Created by Daiy on 02.01.2018.
 */
public class PollutionResetTimerTask implements Runnable {

    private Flag flag;

    public PollutionResetTimerTask(Flag flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!flag.timerIsOff()) {
                    System.out.println("Two second timer has started...");
                    Thread.sleep(2000);
                    System.out.println("Two second timer has stopped...");
                    flag.turnTimerOff();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
