package com.example.web.mapper;

import com.example.model.Summary;
import com.example.web.dto.SummaryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SummeryMapper extends Mappable<Summary, SummaryDto> {
}
