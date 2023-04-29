package com.yellow.test.service.user;

import com.yellow.test.model.user.SaveUserDTO;
import com.yellow.test.model.user.UserDTO;

public interface UserService {

    UserDTO save(SaveUserDTO dto);

}
