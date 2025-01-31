package controller.logInForm;

import model.user.UserFind;
import model.user.UserLogIn;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface LogInFormService {
    public UserFind LogIn(UserLogIn userLogIn) throws SQLException;



}
