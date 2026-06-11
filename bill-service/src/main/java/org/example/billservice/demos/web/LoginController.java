package org.example.billservice.demos.web;

import org.example.billservice.demos.domain.AjaxResponse;
import org.example.billservice.demos.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("/login")
    public AjaxResponse login(){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode("200");
        ajaxResponse.setMsg("success");
        ajaxResponse.setData(new User("admin","123456"));
        return ajaxResponse;
    }
}
