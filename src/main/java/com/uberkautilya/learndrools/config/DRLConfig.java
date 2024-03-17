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
        initializeKieRepository();
        KieBuilder kieBuilder = kieServices.newKieBuilder(getKieFileSystem()).buildAll();
        return kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
    }

    private void initializeKieRepository() {
        final KieRepository repository = kieServices.getRepository();
        repository.addKieModule(repository::getDefaultReleaseId);
    }

    private KieFileSystem getKieFileSystem() {
        return kieServices.newKieFileSystem().write(ResourceFactory.newClassPathResource("rules/order.drl"));
    }
}
