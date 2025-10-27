package br.csi.gymhub.Swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gymhubOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GymHub API")
                        .description("API de gerenciamento de fichas e alunos da academia GymHub.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe GymHub")
                                .email("suporte@gymhub.com.br")
                                .url("https://gymhub.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                );
    }
}
