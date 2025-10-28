package edu.javeriana.process;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.javeriana.process.DTOs.ActivityDTO;
import edu.javeriana.process.model.Activity;

@Configuration
public class ModelMapperConfig {

    @Bean
    // Creacion de la instancia singleton de model mapper
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // De Activity -> ActivityDTO
        modelMapper.addMappings(new PropertyMap<Activity, ActivityDTO>() {
            @Override
            protected void configure() {
                map().setProcessId(source.getProcess().getId());
            }
        });

        // De ActivityDTO -> Activity
        modelMapper.addMappings(new PropertyMap<ActivityDTO, Activity>() {
            @Override
            protected void configure() {
                // Evita intentar mapear el processId directamente (lo manejarás manualmente)
                skip(destination.getProcess());
            }
        });

        return modelMapper;
    }
}
