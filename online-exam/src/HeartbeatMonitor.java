public class HeartbeatMonitor implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                OnlineStatusManager.checkTimeout();
            } catch (Exception e) {}
        }
    }
}