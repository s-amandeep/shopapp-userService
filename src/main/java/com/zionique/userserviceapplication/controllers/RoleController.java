package com.zionique.userserviceapplication.controllers;

import com.zionique.userserviceapplication.models.Session;
import com.zionique.userserviceapplication.models.User;
import com.zionique.userserviceapplication.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;
}
