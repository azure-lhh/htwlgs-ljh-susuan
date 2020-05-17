package cn.htwlgs.guanwang.utils;

import java.util.UUID;

public class GenerationUUID {
    public static  String create(){
        return  UUID.randomUUID().toString().replace("-", "");
    }

}