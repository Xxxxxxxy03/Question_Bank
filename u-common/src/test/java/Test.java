


import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @Desc:
 * @Author: bingbing
 * @Date: 2022/5/4 0004 19:19
 * 单线程处理
 */
public class Test {

    /* 1、大数据分析题，找出重复次数最多的那个数？
    现有一个10G文件的数据，里面包含了18-70之间的整数，分别表示18-70岁的人群数量统计，假设年龄范围分布均匀，分别表示系统中所有用户的年龄数，找出重复次数最多的那个数，现有一台内存为4G、2核CPU的电脑，请写一个算法实现。
    23,31,42,19,60,30,36,........ */

    //定义文件路径
    public static final String FILE_NAME = "E:\\ujiuye\\The_fourth_phase\\java-0802\\u-common\\src\\test\\java\\data.txt";


    //定义map,年龄及对应的数量
    private static Map<String, AtomicInteger> countMap = new ConcurrentHashMap<>();


    public static void main(String[] args) throws IOException {
        //调用读取文件数据方法
        readData();
    }

    //读取文件数据
    private static void readData() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_NAME), "utf-8"));
        String line;
        while ((line = br.readLine()) != null) {
            // 处理每行数据
            splitLine(line);
        }
        findMostAge();
        br.close();
    }

    //每行内容按","分割，并存入到countMap
    public static void splitLine(String lineData) {
        String[] arr = lineData.split(",");
        for (String str : arr) {
            //判断是否为空，如果为空，就跳过，继续往后遍历
            if (StringUtils.isEmpty(str)) {
                continue;
            }
            countMap.computeIfAbsent(str, s -> new AtomicInteger(0)).getAndIncrement();
        }
    }



    //查找数量最多的年龄
    private static void findMostAge() {
        Integer targetValue = 0;
        String targetKey = null;
        Iterator<Map.Entry<String, AtomicInteger>> entrySetIterator = countMap.entrySet().iterator();
        while (entrySetIterator.hasNext()) {
            Map.Entry<String, AtomicInteger> entry = entrySetIterator.next();
            Integer value = entry.getValue().get();
            String key = entry.getKey();
            if (value > targetValue) {
                targetValue = value;
                targetKey = key;
            }
        }
        System.out.println("数量最多的年龄为:" + targetKey + "数量为：" + targetValue);
    }


}