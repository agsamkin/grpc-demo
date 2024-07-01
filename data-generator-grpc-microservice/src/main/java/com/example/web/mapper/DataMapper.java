package com.example.web.mapper;

import com.example.model.Data;
import com.example.web.dto.DataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataMapper extends Mappable<Data, DataDto> {

}
