package com.st.dream.sbmybatis.model;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class UserProperty implements Serializable {

    @Id
    private Integer id;

    private Integer userId;

    private String content;
}
