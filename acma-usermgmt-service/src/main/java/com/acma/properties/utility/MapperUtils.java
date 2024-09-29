package com.acma.properties.utility;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MapperUtils {

    ModelMapper modelMapper = new ModelMapper();

    public MapperUtils() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy( MatchingStrategies.STRICT);
    }

    public <T, D> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public <T, D> List<D> map(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect( Collectors.toList());
    }

    public <T, D> Set<D> mapToSet(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect( Collectors.toSet());
    }

}