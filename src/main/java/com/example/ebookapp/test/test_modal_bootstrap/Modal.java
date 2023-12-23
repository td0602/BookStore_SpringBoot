package com.example.ebookapp.test.test_modal_bootstrap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Modal {
    @GetMapping("/test-modal")
    public String modal() {
        return "test/test_modal_bootstrap/test-modal";
    }
}
