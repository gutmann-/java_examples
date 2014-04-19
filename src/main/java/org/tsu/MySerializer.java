package org.tsu;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME) // чтобы наша аннотация была видна в момент выполнения
@interface MySerializable {
    String name() default "";
}

class MyTest {
    @MySerializable(name = "int")
    private Integer i;
    @MySerializable(name = "str")
    private String s;

    private Double d;

    public MyTest() {
        i = 1;
        s = "s";
        d = 12.34;
    }
}

public class MySerializer {
    public String serializeToString(Object o) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        for (Field f : o.getClass().getDeclaredFields()) {
            if (!f.isAnnotationPresent(MySerializable.class)) continue; // нас не интерисуют не аннотированные нашей аннтотацией классы

            boolean isAccessible = f.isAccessible();
            if (!isAccessible) f.setAccessible(true); // если поле недоступно, делаем его доступным

            MySerializable a = f.getAnnotation(MySerializable.class); // получаем аннотацию
            Object val = f.get(o); // и значение (будет работать не всегда... подумайте, как это можно переписать)
            sb.append(a.name() + ":" + "\"" + val.toString() + "\"" + ";");

            if (!isAccessible) f.setAccessible(false); // возвращаем как было
        }

        return sb.toString();
    }

    public static void demo() throws IllegalAccessException {
        MySerializer s = new MySerializer();
        MyTest m = new MyTest();
        System.out.println(s.serializeToString(m));
    }
}
