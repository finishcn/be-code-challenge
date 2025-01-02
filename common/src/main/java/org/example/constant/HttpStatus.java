/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.constant;

/**
 * http code and result message
 * @author liyu.caelus 2024/12/31
 */
public class HttpStatus {

    /**
     * rate limited code
     */
    public final static int RATE_LIMITED = 429;
    /**
     * http success code
     */
    public final static int HTTP_OK = 200;
    /**
     * response success message
     */
    public final static String SUCCESS_MESSAGE = "SUCCESS";
    /**
     * pong response rate limited message
     */
    public final static String RATE_LIMITED_MSG = "pong rate limited";
    /**
     * request error message
     */
//    public final static String PING_ERROR_MSG = "ping Request sent is error";
    /**
     * ping send rate limited
     */
    public final static String PING_LIMITED_MSG = "ping rate limited";
}
