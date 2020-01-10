package run.redis;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

public class RedisTest {
    private static String ip = "localhost";

    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(0);
        //jedis.del("5");
        //jedis.flushDB();
        Set<String> strs = jedis.keys("*");
        System.out.println("strs : "+strs);
        Iterator<String> it = strs.iterator();
        while(it.hasNext()){
            System.out.println(jedis.get(it.next()));
        }
        //jedis.flushAll();
        //System.out.println("Connection to server sucessfully");
        //查看服务是否运行
        //System.out.println("Server is running: "+jedis.ping());
        jedis.close();
    }

    /**
     * 遍历所有值，根据传入用户判断是否删除
     * @param username
     * @return
     */
    public static boolean searchForDel(String username){
        boolean status = false;
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(0);
        Set<String> strs = jedis.keys("*");
        Iterator<String> it = strs.iterator();
        while(it.hasNext()){
            String key = it.next();
            String val = jedis.get(key);
            if(username.equals(val)){
                jedis.del(key);
                status = true;
            }
        }
        jedis.close();
        return status;
    }

    public static boolean searchLoginUser(String sessionId){
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(0);
        String result = jedis.get(sessionId);
        System.out.println("result :"+result);
        jedis.close();
        if(result != null && !"".equals(result)){
            return true;
        }else{
            return false;
        }

    }

    public static String getLoginUser(String sessionId){
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(0);
        String result = jedis.get(sessionId);
        //System.out.println("result :"+result);
        jedis.close();
        if(result != null && !"".equals(result)){
            return result;
        }else{
            return "";
        }

    }

    public static void addLoginUser(String sessionId,String userId){
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(0);
        jedis.set(sessionId,userId);
        jedis.persist(sessionId);
        jedis.close();
    }

    public static void removeLoginUser(String sessionId){
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(0);
        jedis.del(sessionId);
        jedis.close();
    }

    //查询公告栏
    public static String searchMarquee(){
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(3);
        String res = jedis.get("marquee");
        jedis.close();
        return res;
    }

    //修改公告栏
    public static void updateMarquee(String marquee){
        Jedis jedis = new Jedis(ip,6379);
        jedis.auth("XinHong12345");
        jedis.select(3);
        jedis.del("marquee");
        jedis.set("marquee",marquee);
        jedis.close();
    }

}
