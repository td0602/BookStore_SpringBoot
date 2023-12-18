package com.example.ebookapp.test_filter_book;

import com.example.ebookapp.model.BookDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TestBookServiceImpl implements TestBookService{
    @Autowired
    private TestBookRepository testBookRepository;
    @Override
    public Page<BookDetails> getAll(Specification<BookDetails> specification, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 9);
        return testBookRepository.findAll(specification, pageable);
    }

    @Override
    public Page<BookDetails> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 9);
        return testBookRepository.findAll(pageable);
    }
}
