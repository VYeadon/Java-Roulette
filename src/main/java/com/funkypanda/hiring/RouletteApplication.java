package com.funkypanda.hiring;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.jersey.jackson.JacksonMessageBodyProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class RouletteApplication extends Application<RouletteConfiguration> {
	public static void main(String[] args) throws Exception {
		new RouletteApplication().run(args);
	}

	@Override
	public String getName() {
		return "roulette";
	}

	@Override
	public void initialize(Bootstrap<RouletteConfiguration> bootstrap) {
	}

	@Override
	public void run(RouletteConfiguration configuration, Environment environment) {
		RouletteResource resource = new RouletteResource();
		ObjectMapper objectMapper = new ObjectMapper();
		environment.jersey().register(new JacksonMessageBodyProvider(objectMapper, environment.getValidator()));
		environment.jersey().register(resource);
	}

}