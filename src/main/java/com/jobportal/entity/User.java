package com.jobportal.entity;


import com.jobportal.dto.AccountType;
import com.jobportal.dto.UserDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


//Understanding _________+++++++++++++++++++++++++
//If we only use the @Data Annotation then there is no need to use getter setter & toString method
//Document is same as @Table() @Entity()

@Data
@Document(collection = "users")
public class User {
    @Id
    private Long id;
    private String name;
    @Indexed(unique = true)
    private String email;
    private String password;
    private AccountType accountType;
    private Long profileId;

    public User(Long id, String name, String email, String password, AccountType accountType,Long profileId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.profileId = profileId;
    }

    public UserDto toDto(){
        return new UserDto(this.id,this.name,this.email,this.password,this.accountType,this.profileId);
    }

}
