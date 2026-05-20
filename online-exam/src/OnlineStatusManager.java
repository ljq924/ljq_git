import java.util.*;
import java.util.concurrent.*;

public class OnlineStatusManager {
    private static final Map<String, Long> heartbeatMap = new ConcurrentHashMap<>();
    private static final long TIMEOUT = 10000;

    public static void login(String user) {
        heartbeatMap.put(user, System.currentTimeMillis());
    }

    public static void updateHeartbeat(String user) {
        heartbeatMap.put(user, System.currentTimeMillis());
    }

    public static void logout(String user) {
        heartbeatMap.remove(user);
    }

    public static void checkTimeout() {
        long now = System.currentTimeMillis();
        for (String user : new ArrayList<>(heartbeatMap.keySet())) {
            long last = heartbeatMap.get(user);
            if (now - last > TIMEOUT) {
                System.out.println("[WARNING] 用户 " + user + " 已离线");
                logout(user);
            }
        }
    }
}