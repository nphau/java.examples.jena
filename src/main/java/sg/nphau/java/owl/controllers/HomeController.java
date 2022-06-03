/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
