package org.example.libraryservice.mapper;

import org.example.libraryservice.dto.AvailableBookDto;
import org.example.libraryservice.model.AvailableBook;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AvailableBookMapper
{
    private final ModelMapper modelMapper = new ModelMapper();

    public AvailableBookDto toBookDto(AvailableBook availableBook)
    {
        return modelMapper.map(availableBook, AvailableBookDto.class);
    }
    public AvailableBook toBook(AvailableBookDto bookDto)
    {
        return modelMapper.map(bookDto, AvailableBook.class);
    }
}
