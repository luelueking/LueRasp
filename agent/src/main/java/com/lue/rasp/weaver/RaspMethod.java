package com.lue.rasp.weaver;

public class RaspMethod extends org.objectweb.asm.commons.Method {

    public int access;

    public RaspMethod(int access , String name, String descriptor) {
        super(name, descriptor);
        this.access = access;
    }
}