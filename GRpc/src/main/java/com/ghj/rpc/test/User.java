package com.ghj.rpc.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class User implements Serializable {

    private Integer id;
    private String name;
}
