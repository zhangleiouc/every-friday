package com.example.demo.onjava8.fp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionProgramming {

    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.stream().map(s -> s + "").collect(Collectors.toList());
        System.out.println("FunctionProgramming");
    }
    
}
