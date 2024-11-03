package org.example.bookservice.mapper;

import org.example.bookservice.dto.BookDto;
import org.example.bookservice.dto.BookUpdateDto;
import org.example.bookservice.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper
{
    private final ModelMapper modelMapper = new ModelMapper();

    public BookDto toBookDto(Book book)
    {
        return modelMapper.map(book, BookDto.class);
    }
    public Book toBook(BookDto bookDto)
    {
        return modelMapper.map(bookDto, Book.class);
    }
    public void copyFields(Book book, BookUpdateDto bookDto)
    {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(bookDto, book);
    }
}
