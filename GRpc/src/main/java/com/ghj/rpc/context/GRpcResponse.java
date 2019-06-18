package com.ghj.rpc.context;

import lombok.Data;

/**
 * Rpc返回实体类
 * @author GeHejun
 * @date 2019-06-18
 */
@Data
public class GRpcResponse {
    /**
     * 请求id
     */
    private String requestId;
    /**
     * 返回参数
     */
    private Object resultResponse;

}
