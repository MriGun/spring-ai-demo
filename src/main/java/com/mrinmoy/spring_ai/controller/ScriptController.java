package com.mrinmoy.spring_ai.controller;

import com.mrinmoy.spring_ai.functions.ShellScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScriptController {


    @Autowired
    private ShellScriptService shellScriptService;

    @PostMapping("/run-script")
    public ResponseEntity<String> runScript(@RequestParam String scriptName) {
        try {
            String scriptPath = "scripts/" + scriptName + ".sh";
            String result = shellScriptService.runShellScript(scriptPath);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
