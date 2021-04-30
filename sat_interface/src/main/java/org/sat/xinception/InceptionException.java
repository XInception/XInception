package org.sat.xinception;

public class InceptionException extends Exception {
    String msg="";
    public InceptionException(String msg) {
        this.msg=msg;
    }
    @Override
    public String getMessage(){
        return this.msg;
    }
}
