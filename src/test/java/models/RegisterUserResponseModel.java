package models;

import lombok.Data;

@Data
public class RegisterUserResponseModel {
    String userID, username;
    String[] books;
}
