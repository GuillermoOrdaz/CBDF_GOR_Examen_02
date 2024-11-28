package com.upiiz.pyments.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
                title = "API de pyments",
                description = "Esta API proporciona acceso a los recursos de pyments",
                version = "1.0.0",
                contact = @Contact(
                        name = "Guillermo Ordaz Rodríguez",
                        email = "gordazr2100@alumno.ipn.mx",
                        url = "http://localhost:8081/contacto"
                ),
                license = @License(),
                termsOfService = "Solo se permiten 400 solicitudes"
        ),
        servers = {
                @Server(
                        description = "Servidor de pruebas",
                        url = "http://localhost:8081"
                ),
                @Server(
                        description = "Servidor de Produccion",
                        url = "https://localhost:8081/produccion"//Poner el link del render
                )
        },
        tags = {
                @Tag(
                        name = "pyments",
                        description = "EndPoint de los recursos"
                )
        }
)
public class OpenApiConfiguration {

}
