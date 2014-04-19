package org.tsu;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;


public class Main {
    public static final void main(String args[]) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        CallConstructorOfGenericType.demo();
        MySerializer.demo();

        System.out.println("end");
    }
}
