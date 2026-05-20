import java.net.*;

public class ExamServer {
    public static void main(String[] args) {
        int tcpPort = 9000;
        int udpPort = 9001;

        // 启动UDP心跳监听
        new Thread(new UdpHeartbeatListener(udpPort)).start();
        // 启动心跳超时监控
        new Thread(new HeartbeatMonitor()).start();

        try (ServerSocket serverSocket = new ServerSocket(tcpPort)) {
            System.out.println("考试服务器已启动 TCP:" + tcpPort + " UDP:" + udpPort);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("新考生连接：" + socket);
                new Thread(new TcpClientHandler(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}