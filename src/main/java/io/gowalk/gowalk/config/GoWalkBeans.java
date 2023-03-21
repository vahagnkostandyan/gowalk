package io.gowalk.gowalk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

import java.time.Duration;

import static com.theokanning.openai.service.OpenAiService.*;

@Configuration
public class GoWalkBeans {
    @Value("${app.openai.api-key}")
    private String openAIApiKey;

    @Bean
    public OpenAiService openAiService() {
        ObjectMapper mapper = defaultObjectMapper();
        OkHttpClient okHttpClient = defaultClient(openAIApiKey, Duration.ofSeconds(120));
        Retrofit retrofit = defaultRetrofit(okHttpClient, mapper);

        OpenAiApi api = retrofit.create(OpenAiApi.class);
        return new OpenAiService(api);
    }
}
