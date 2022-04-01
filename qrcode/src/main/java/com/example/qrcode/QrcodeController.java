package com.example.qrcode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qrcode")
public class QrcodeController {

	@GetMapping(value = "/")
    public String convertPdf() {
        return "convertpdf";
    }
}
