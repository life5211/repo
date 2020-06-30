package com.hnf.main;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

class MySuperClass {
    public int super_id = -1;
    public String super_name = "Unknown";

}

class MyClass extends MySuperClass{
    public int id = -1;
    public String name = "Unknown";

}

public class Hero {
    public static void main(String[] args) {
        Class<MyClass> c = MyClass.class;

        // Print declared fields
        ArrayList<String> fieldsDesciption = getDeclaredFieldsList(c);

        System.out.println("Declared Fields for " + c.getName());
        for (String desc : fieldsDesciption) {
            System.out.println(desc);
        }
        fieldsDesciption = getFieldsList(c);

        System.out.println("\nAccessible Fields for " + c.getName());
        for (String desc : fieldsDesciption) {
            System.out.println(desc);
        }
    }

    public static ArrayList<String> getFieldsList(Class c) {
        Field[] fields = c.getFields();
        ArrayList<String> fieldsList = getFieldsDesciption(fields);
        return fieldsList;
    }

    public static ArrayList<String> getDeclaredFieldsList(Class c) {
        Field[] fields = c.getDeclaredFields();
        ArrayList<String> fieldsList = getFieldsDesciption(fields);
        return fieldsList;
    }

    public static ArrayList<String> getFieldsDesciption(Field[] fields) {
        ArrayList<String> fieldList = new ArrayList<>();

        for (Field f : fields) {
            int mod = f.getModifiers() & Modifier.fieldModifiers();
            String modifiers = Modifier.toString(mod);

            Class<?> type = f.getType();
            String typeName = type.getSimpleName();

            String fieldName = f.getName();

            fieldList.add(modifiers + "  " + typeName + "  " + fieldName);
        }

        return fieldList;
    }
}
