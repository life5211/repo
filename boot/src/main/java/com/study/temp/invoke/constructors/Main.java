package com.study.temp.invoke.constructors;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

class MyClass<T> {

    public MyClass(int i, int j, String s) {

    }

    public MyClass(T t) {

    }

    public int getInt(String a) {
        return 0;
    }
}

public class Main {
    public static void main(String[] args) {
        Class<MyClass> c = MyClass.class;

        System.out.println("Constructors for " + c.getName());
        Constructor[] constructors = c.getConstructors();
        ArrayList<String> constructDescList = getConstructorsDesciption(constructors);
        for (String desc : constructDescList) {
            System.out.println(desc);
        }
    }

    public static ArrayList<String> getConstructorsDesciption(
            Constructor[] constructors) {
        ArrayList<String> constructorList = new ArrayList<>();
        for (Constructor constructor : constructors) {
            String modifiers = getModifiers(constructor);

            String constructorName = constructor.getName();

            constructorList.add(modifiers + "  " + constructorName + "("
                    + getParameters(constructor) + ") " + getExceptionList(constructor));
        }
        return constructorList;
    }
    public static ArrayList<String> getParameters(Executable exec) {
        Parameter[] parms = exec.getParameters();
        ArrayList<String> parmList = new ArrayList<>();
        for (int i = 0; i < parms.length; i++) {

            int mod = parms[i].getModifiers() & Modifier.parameterModifiers();
            String modifiers = Modifier.toString(mod);
            String parmType = parms[i].getType().getSimpleName();
            String parmName = parms[i].getName();
            String temp = modifiers + "  " + parmType + "  " + parmName;
            if (temp.trim().length() == 0) {
                continue;
            }
            parmList.add(temp.trim());
        }
        return parmList;
    }

    public static ArrayList<String> getExceptionList(Executable exec) {
        ArrayList<String> exceptionList = new ArrayList<>();
        for (Class<?> c : exec.getExceptionTypes()) {
            exceptionList.add(c.getSimpleName());
        }
        return exceptionList;
    }

    public static String getModifiers(Executable exec) {
        int mod = exec.getModifiers();
        if (exec instanceof Method) {
            mod = mod & Modifier.methodModifiers();
        } else if (exec instanceof Constructor) {
            mod = mod & Modifier.constructorModifiers();
        }
        return Modifier.toString(mod);
    }
}