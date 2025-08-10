package com.trading.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 08-08-2025 01:03
 */
@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "Welcome to the Home Page!";
    }
}
