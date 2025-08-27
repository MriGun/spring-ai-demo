package com.mrinmoy.spring_ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@EnableConfigurationProperties
@SpringBootApplication
public class SpringAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiApplication.class, args);

		/*try {
			var apiKey = System.getenv("OPENAI_API_KEY");
			var body = """
        {
            "model": "gpt-4o",
            "messages": [
                {
                    "role": "user",
                    "content": "Generate a book recommendation for a book on AI and coding. Please limit the summary to 100 words."
                }
            ]
        }""";

			var request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.openai.com/v1/chat/completions"))
					.header("Content-Type", "application/json")
					.header("Authorization", "Bearer " + apiKey)
					.POST(HttpRequest.BodyPublishers.ofString(body))
					.build();

			var client = HttpClient.newHttpClient();
			var response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}*/

	}

}
