package com.varmateo;

import com.varmateo.Greeter;
import com.varmateo.yawg.api.YawgInfo;


public final class Main {


    public static void main(final String[] args) {

        String yawgDetails = YawgInfo.PRODUCT_NAME + " " + YawgInfo.VERSION;
        String message = String.format("Hello, we are using %s", yawgDetails);
        Greeter.say(message);
    }

}
