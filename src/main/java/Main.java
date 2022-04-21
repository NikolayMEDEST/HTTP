import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        // создаем объект запроса
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        // отправляем запрос
        CloseableHttpResponse response = httpClient.execute(request);

        // выводим тело ответа
      //  String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
       // System.out.println(body);

        // преобразование json в java
        List<Post> posts = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Post>>(){});

        List<Post> stream = posts.stream()
                .filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0)
                .collect(Collectors.toList());
        stream.forEach(System.out::println);

    }

    public static ObjectMapper mapper = new ObjectMapper();

}




