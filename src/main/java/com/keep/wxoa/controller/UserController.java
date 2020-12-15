package com.keep.wxoa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xinyueyan
 * @Date: 12/14/2020 2:21 PM
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController extends BaseController {
    /*@Autowired
    private UserService userService;
*/
    @GetMapping("test")
    public String Test1(){
        log.info("test111111111");

        return "test1111";
    }

}
