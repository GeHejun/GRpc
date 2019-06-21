package com.ghj.rpc.context;

import lombok.Data;

import java.io.Serializable;

/**
 * Rpc返回实体类
 * @author GeHejun
 * @date 2019-06-18
 */
@Data
public class Response implements Serializable {
    /**
     * 请求id
     */
    private String requestId;
    /**
     * 返回参数
     */
    private Object resultResponse;

}
