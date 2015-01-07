package com.sample.box.helpers;

import com.sample.box.handlers.conditions.Checker;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ReflectApp {

    public void call(String methodName, Object[] params){

        try{
            //load the AppTest at runtime
            Checker checker = new Checker();

            List<Method> methods = Arrays.asList(checker.getClass().getMethods());

            for (Method m : methods){
                if(m.getName().contentEquals(methodName)){
                    if(m.getReturnType().equals(Void.TYPE)) System.out.println("call void from xml");
                    m.invoke(checker,params);
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
