package org.example.shard.presentation;


import org.example.shard.model.UserRequest;
import org.example.shard.model.shard.User;
import org.example.shard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shardTest")
public class Resource {

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/usersByfirstName")
    public List<User> getUsersByFirstName(@RequestParam("firstName") String firstName) {
        return service.getUser(firstName);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addUser")
    public void addUser(@RequestBody UserRequest userRequest) {
        service.addUser(userRequest);
    }
}
