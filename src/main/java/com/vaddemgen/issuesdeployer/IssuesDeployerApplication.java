package com.vaddemgen.issuesdeployer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class IssuesDeployerApplication {

  public static void main(String[] args) {
    SpringApplication.run(IssuesDeployerApplication.class, args);
  }

}
