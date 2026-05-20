import java.net.*;

public class UdpHeartbeatListener implements Runnable {
    private int port;

    public UdpHeartbeatListener(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (DatagramSocket ds = new DatagramSocket(port)) {
            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            while (true) {
                ds.receive(dp);
                String msg = new String(dp.getData(), 0, dp.getLength());
                if (msg.startsWith("HEARTBEAT:")) {
                    String user = msg.split(":")[1];
                    OnlineStatusManager.updateHeartbeat(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}