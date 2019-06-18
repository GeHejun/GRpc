package com.ghj.rpc.context;

import lombok.Data;

/**
 * Rpc请求实体类
 * @author GeHejun
 * @date 2019-06-18
 */
@Data
public class GRpcRequest {
    /**
     * 请求id
     */
    private String requestId;
    /**
     * 请求类名
     */
    private String className;
    /**
     * 请求方法名
     */
    private String methodName;
    /**
     * 请求参数
     */
    private Object[] parameters;
    /**
     * 请求参数类型
     */
    private Class[] parameterTypes;
}
