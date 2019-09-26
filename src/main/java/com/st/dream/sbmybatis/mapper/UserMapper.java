package com.st.dream.sbmybatis.mapper;

import com.st.dream.sbmybatis.model.User;
import com.st.dream.sbmybatis.model.UserProperty;
import org.apache.ibatis.annotations.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper extends BaseMapper<User>{

    User getUserByName(@Param("name") String name);

    User getUserWithProperty(@Param("id") Integer id, @Param("propertyIds") Integer[] propertyIds);

    void insertUserXml(User user);

    void deletePropertyByIds(Integer[] propertyIds);

    @Insert("insert into user (username, password) values (#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertUser(User user);

    @InsertProvider(type = Provider.class, method = "batchInsert")
    Integer batchInsertProperty(List<UserProperty> userPropertyList);


    class Provider{

        public String batchInsert(Map map){
            List<UserProperty> properties = (List<UserProperty>)map.get("list");
            StringBuilder sb = new StringBuilder();
            sb.append("insert into user_property(user_id, content) values ");
            MessageFormat mf = new MessageFormat(
                    "( #'{'list[{0}].userId}, #'{'list[{0}].content})"
            );
            for (int i = 0; i < properties.size(); i++) {
                sb.append(mf.format(new Object[] {i}));
                if (i < properties.size() - 1)
                    sb.append(",");
            }
            return sb.toString();
        }
    }
}
