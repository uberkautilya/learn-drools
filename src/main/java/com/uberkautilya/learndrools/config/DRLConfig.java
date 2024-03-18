package com.uberkautilya.learndrools.config;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DRLConfig {
    private final KieServices kieServices = KieServices.Factory.get();

    @Bean
    public KieSession kieSession() {
        return getKieContainer().newKieSession();
    }

    public KieContainer getKieContainer() {
        final KieRepository repository = kieServices.getRepository();
        repository.addKieModule(repository::getDefaultReleaseId);

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(ResourceFactory.newClassPathResource("rules/order.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        ReleaseId releaseId = kieBuilder.getKieModule().getReleaseId();

        return kieServices.newKieContainer(releaseId);
    }

}
