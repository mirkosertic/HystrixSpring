package de.mirkosertic.controller;

import de.mirkosertic.RandomBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    private final RandomBean randomBean;

    @Autowired
    public HelloWorldController(RandomBean aRandomBean) {
        randomBean = aRandomBean;
    }

    @RequestMapping("/hello")
    public ModelAndView simpleHello() {
        String message = "Welcome to our App, here is a random value : " + randomBean.random();
        return new ModelAndView("hello", "message", message);
    }
}
