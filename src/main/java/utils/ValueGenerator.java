package utils;

import java.util.Random;

/**
 * генератор тестовых значений
 */

public class ValueGenerator {
    static final Integer id = new Random(System.currentTimeMillis()).nextInt(8888 - 1400)+1800;

    public static Long genRandomId(){
       return Long.valueOf(id);
    }

    public static Long addPostfixForMessageId(Long id){
        String postfix = id.toString() + "9001";
        return Long.valueOf(postfix);
    }



}
