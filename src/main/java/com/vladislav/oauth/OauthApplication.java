package com.vladislav.oauth;

import java.nio.file.Files;
import java.nio.file.Path;
import lombok.SneakyThrows;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OauthApplication implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(OauthApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    showLogo();
  }

  @SneakyThrows
  private void showLogo() {
    final Path path = Path.of(OauthApplication.class.getResource("/logo").getPath());
    System.out.println(new String(Files.readAllBytes(path)));
  }
}
