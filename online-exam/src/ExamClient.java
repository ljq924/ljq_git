import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ExamClient {
    private static String username;
    private static final String SERVER_IP = "127.0.0.1";
    private static final int TCP_PORT = 9000;
    private static final int UDP_PORT = 9001;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Socket socket = new Socket(SERVER_IP, TCP_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("输入用户名：");
            username = sc.next();
            System.out.print("输入密码：");
            String pwd = sc.next();
            out.println("LOGIN:" + username + ":" + pwd);

            new Thread(new HeartbeatSender()).start();

            String resp;
            while ((resp = in.readLine()) != null) {
                System.out.println("【服务器】" + resp);

                if (resp.startsWith("LOGIN_OK")) {
                    out.println("GET_QUESTION");
                } else if (resp.startsWith("QUESTION:")) {
                    System.out.print("请输入答案(A/B/C/D)：");
                    String ans = sc.next();
                    out.println("ANSWER:" + resp.split(":")[1] + ":" + ans);
                } else if (resp.startsWith("RESULT:")) {
                    if (resp.contains("end")) {
                        out.println("SUBMIT");
                    } else {
                        out.println("GET_QUESTION");
                    }
                } else if (resp.startsWith("SCORE:")) {
                    System.out.println("====================================");
                    System.out.println("           考试结束！");
                    System.out.println("====================================");
                    break;
                }
            }

            in.close();
            out.close();
            socket.close();
            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class HeartbeatSender implements Runnable {
        @Override
        public void run() {
            try (DatagramSocket ds = new DatagramSocket()) {
                while (true) {
                    String msg = "HEARTBEAT:" + username;
                    byte[] buf = msg.getBytes();
                    DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName(SERVER_IP), UDP_PORT);
                    ds.send(dp);
                    Thread.sleep(3000);
                }
            } catch (Exception ignored) {}
        }
    }
}