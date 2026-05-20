import java.io.*;
import java.net.*;
import java.util.*;

public class TcpClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    private int correct = 0;
    private int wrong = 0;
    private int qIndex = 0;
    private List<QuestionBank.Question> questions = QuestionBank.getQuestions();

    public TcpClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String line;
            while ((line = in.readLine()) != null) {
                handleMessage(line.trim());
            }
        } catch (IOException e) {
            System.err.println("客户端连接异常：" + e.getMessage());
        } finally {
            OnlineStatusManager.logout(username);
            close();
        }
    }

    private void handleMessage(String msg) {
        if (msg.startsWith("LOGIN:")) {
            String[] s = msg.split(":");
            username = s[1];
            String pwd = s[2];
            if (pwd.equals("123")) {
                OnlineStatusManager.login(username);
                out.println("LOGIN_OK");
            } else {
                out.println("LOGIN_FAIL");
            }
        }

        else if (msg.equals("GET_QUESTION")) {
            if (qIndex < questions.size()) {
                QuestionBank.Question q = questions.get(qIndex);
                out.println("QUESTION:" + q.id + ":" + q.title + ":" + q.a + ":" + q.b + ":" + q.c + ":" + q.d);
            } else {
                out.println("RESULT:end");
            }
        }

        else if (msg.startsWith("ANSWER:")) {
            String ans = msg.split(":")[2];
            QuestionBank.Question q = questions.get(qIndex);
            boolean ok = q.answer.equalsIgnoreCase(ans);

            if (ok) correct++;
            else wrong++;

            out.println("RESULT:正确=" + correct + " 错误=" + wrong);
            qIndex++;
        }

        else if (msg.equals("SUBMIT")) {
            out.println("SCORE: 正确=" + correct + " 错误=" + wrong + " 总分=" + correct + "/" + questions.size());
        }
    }

    private void close() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}