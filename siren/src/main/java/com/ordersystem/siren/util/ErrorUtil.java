package com.ordersystem.siren.util;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.LinkedHashMap;
import java.util.LinkedList;

@Service
public class ErrorUtil {
    public static LinkedList<LinkedHashMap<String, String>> refineErrors(Errors errors){
        LinkedList linkedList = new LinkedList<LinkedHashMap<String, String>>();
        errors.getFieldErrors().forEach(e->{
            LinkedHashMap<String, String> error = new LinkedHashMap<>();
            error.put("field", e.getField());
            error.put("message", e.getDefaultMessage());
            linkedList.push(error);
        });
        return linkedList;
    }
}
