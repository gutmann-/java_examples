package org.tsu;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

class Test {
    private int a;

    public Test() {
        this.a = 123;
    }

    public Test(Integer a) {
        this.a = a;
    }

    int getA() { return a; }
}

class X<T> {
    T t;

    public X(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        t = clazz.newInstance();
    }
}


class AdvancedConstruct<T> {
    private static final Map<List<Class>, List<Object>> defaultConstructorValues = makeDefaultConstructorValues();

    private static Map<Class[], Object[]> __makeDefaultConstructorValues() {
        Map<Class[], Object[]> m = new HashMap<>();
        m.put(new Class[] { Integer.class },               new Object[] { 1 });
        m.put(new Class[] { Integer.class, String.class }, new Object[] { 1, "hello" });
        return m;
    }

    private static Map<List<Class>, List<Object>> makeDefaultConstructorValues() {
        Map<List<Class>, List<Object>> m = new HashMap<>();
        for (Map.Entry<Class[], Object[]> e : __makeDefaultConstructorValues().entrySet()) {
            List<Class>  k = new ArrayList<>(Arrays.asList(e.getKey()));
            List<Object> v = new ArrayList<>(Arrays.asList(e.getValue()));

            m.put(k, v);
        }
        return m;
    }

    T t;

    public AdvancedConstruct(Class<T> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (Constructor c : clazz.getConstructors()) {
            List<Class> paramTypes = Arrays.asList(c.getParameterTypes());
            if (defaultConstructorValues.containsKey(paramTypes)) {
                List<Object> defaultValues = defaultConstructorValues.get(paramTypes);

                t = (T) c.newInstance(defaultValues.toArray());
            }
        }
    }
}

public class CallConstructorOfGenericType {
    public static void demo() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<Test> c = Test.class;
        X<Test> x = new X<>(c);
        AdvancedConstruct<Test> ac = new AdvancedConstruct<>(c);
    }
}
