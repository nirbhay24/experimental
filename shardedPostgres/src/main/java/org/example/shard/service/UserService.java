package org.example.shard.service;


import org.example.shard.configuration.DBContextHolder;
import org.example.shard.configuration.DBTypeEnum;
import org.example.shard.model.UserRequest;
import org.example.shard.model.shard.User;
import org.example.shard.model.shard.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUser(String name) {
        setContext(name);
        return (List<User>) userRepository.findByFirstName(name);
    }

    public List<User> getAllUsers() {
        DBContextHolder.setCurrentDb(DBTypeEnum.SHARD1);
        List<User> allUsers = new ArrayList<>((Collection<? extends User>) userRepository.findAll());
        DBContextHolder.setCurrentDb(DBTypeEnum.SHARD2);
        allUsers.addAll((Collection<? extends User>) userRepository.findAll());
        return allUsers;
    }

    public void addUser(UserRequest request) {
        setContext(request.getFirstName());
        saveInShard(request);

    }

    private void saveInShard(UserRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .age(request.getAge())
                .build();
        userRepository.save(user);
    }

    private void setContext(String name) {
        char firstChar = Character.toLowerCase(name.charAt(0));
        if( firstChar >='a' && firstChar <= 'm') {
            DBContextHolder.setCurrentDb(DBTypeEnum.SHARD1);
        } else {
            DBContextHolder.setCurrentDb(DBTypeEnum.SHARD2);
        }
    }

}
