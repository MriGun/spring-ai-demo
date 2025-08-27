package com.mrinmoy.spring_ai.functions;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Service
public class ShellScriptService {

    public String runShellScript(String scriptPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();

            // For Windows, use Git Bash, WSL, or Cygwin
            processBuilder.command("bash", scriptPath);

            // Alternative: Use WSL
            // processBuilder.command("wsl", "bash", scriptPath);

            processBuilder.directory(new File(System.getProperty("user.dir")));

            Process process = processBuilder.start();

            // Read output
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString();
            } else {
                throw new RuntimeException("Script execution failed with exit code: " + exitCode);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error executing shell script: " + e.getMessage(), e);
        }
    }

}
