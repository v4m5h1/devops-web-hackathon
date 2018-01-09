package io.sudheer.devops.web.devopsweb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import io.sudheer.devops.web.devopsweb.jenkins.ReadBuildAndStageInfo;
import io.sudheer.devops.web.devopsweb.jenkins.utils.JobDetailsDAO;

@EnableAutoConfiguration
public class DevopsWebMavenApplication {
    public static void main (String[] args) {
    		try {
    			String jenkinsURL = "http://192.168.43.115:8080";
    			String projectName = "devops-web-hackathon";
    			String projectBranch = "master";
    			boolean isMultiBranchPipeline = true;
    			
    			JobDetailsDAO jobDetailsObj = new JobDetailsDAO();
    			jobDetailsObj.setJenkinsURL(jenkinsURL);
    			jobDetailsObj.setProjectName(projectName);
    			jobDetailsObj.setProjectBranch(projectBranch);
    			jobDetailsObj.setIsMultiBranchPipeline(isMultiBranchPipeline);
    			
    			StringBuilder sb = ReadBuildAndStageInfo.getFullInfo(jobDetailsObj);
    			File file = new File("data.csv");
    			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
    			    writer.write(sb.toString());
    			}
    			//System.out.println(ReadBuildAndStageInfo.getFullInfo(jobDetailsObj));
		} catch (Exception e) {
			e.printStackTrace();
		}
        SpringApplication app = new SpringApplication(DevopsWebMavenApplication.class);
        app.run(args);
    }
    
    @Bean
    WebMvcConfigurer configurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers (ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/pages/**").
                          addResourceLocations("classpath:/static/");
            }
        };
    }
}
