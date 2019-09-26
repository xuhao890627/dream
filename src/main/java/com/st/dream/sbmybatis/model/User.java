package com.st.dream.sbmybatis.model;

import lombok.Data;

import javax.persistence.Id;
import java.io.*;
import java.util.List;

@Data
public class User implements Serializable {
//public class User implements Externalizable {

    @Id
    private Integer id;

    private String username;

    private String password;

    private List<UserProperty> propertyList;

//    @Override
//    public void writeExternal(ObjectOutput out) throws IOException {
//        out.writeObject(username);
//        out.writeInt(id);
//    }
//
//    @Override
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        username = (String)in.readObject();
//        id = in.readInt();
//    }
}
