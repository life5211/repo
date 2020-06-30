package com.study.temp.invoke.build;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class MyClass {
    public String name = "className";
    private String pri = "privateName";

    public MyClass() {
        System.out.println("called");
    }

    private MyClass(int i, String s) {
        System.out.println("Private called");
        System.out.println(i);
        System.out.println(s);
    }

    public String setName(String n) {
        System.out.println(n);
        return n + "===";
    }

    private void print(String n) {
        System.out.println(n);
    }
}

public class Main {
    public static void main(String[] args) throws InstantiationException {
        con();
        System.out.println("==============");
        priconst();
        System.out.println("==============");
        method();
        System.out.println("==============");
        priMethod();
        System.out.println("==============");
        field();
        System.out.println("==============");
        priField();
    }

    static void field() {
        Class<MyClass> ppClass = MyClass.class;
        try {
            MyClass p = ppClass.newInstance();
            Field name = ppClass.getField("name");
            String nameValue = (String) name.get(p);
            System.out.println("Current name is " + nameValue);
            name.set(p, "abc");
            nameValue = (String) name.get(p);
            System.out.println("New  name is " + nameValue);
        } catch (InstantiationException | IllegalAccessException
                | NoSuchFieldException | SecurityException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    static void priField() {
        Class<MyClass> my = MyClass.class;
        try {
            MyClass p = my.newInstance();
            Field nameField = my.getDeclaredField("pri");
            nameField.setAccessible(true);
            String nameValue = (String) nameField.get(p);
            System.out.println("Current name is " + nameValue);
            nameField.set(p, "abc");
            nameValue = (String) nameField.get(p);
            System.out.println("New name is " + nameValue);
        } catch (InstantiationException | IllegalAccessException
                | NoSuchFieldException | SecurityException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    static void method() {
        Class<MyClass> myClass = MyClass.class;
        try {
            MyClass p = myClass.newInstance();
            Method setName = myClass.getMethod("setName", String.class);
            String abc = (String) setName.invoke(p, "abc");
            System.out.println(abc);
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | SecurityException | IllegalArgumentException
                | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }
    }

    static void priMethod() {
        Class<MyClass> myClass = MyClass.class;
        try {
            MyClass p = myClass.newInstance();
            Method setName = myClass.getDeclaredMethod("print", String.class);
            setName.setAccessible(true);
            setName.invoke(p, "private");
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | SecurityException | IllegalArgumentException
                | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }
    }

    static void con() {
        Class<MyClass> personClass = MyClass.class;
        try {
            MyClass p = personClass.newInstance();
            System.out.println(p);
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    static void priconst() {
        Class<MyClass> myClass = MyClass.class;
        try {
            Constructor<MyClass> cons = myClass.getDeclaredConstructor(int.class, String.class);
            cons.setAccessible(true);
            MyClass chris = cons.newInstance(1, "abc");
            System.out.println(chris);
        } catch (NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }
    }
}
