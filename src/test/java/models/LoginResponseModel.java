package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponseModel {

    String userId, password, token, expires, isActive;

    @JsonProperty("username")
    String userName;

    @JsonProperty("created_date")
    String createdDate;
}
