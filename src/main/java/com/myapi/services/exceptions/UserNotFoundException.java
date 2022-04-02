/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi.services.exceptions;

public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = -6221868913026910626L;

        public UserNotFoundException(String msg){
            super(msg);
        }
}
