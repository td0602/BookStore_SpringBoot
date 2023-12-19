package com.example.ebookapp.test.test_filter_book;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class TestBookServiceImpl implements TestBookService{
    @Autowired
    private TestBookRepository testBookRepository;

    // Filter Book cach 1
    @Override
    public Page<BookDetails> getAll(Specification<BookDetails> specification, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 9);
        return testBookRepository.findAll(specification, pageable);
    }
    // Dung trong cach 1
    @Override
    public Page<BookDetails> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 9);
        return testBookRepository.findAll(pageable);
    }

    //Filter Book Cach 2
    @Override
    public Page<BookDetails> getBooksByFilter(
                                              @Param("category") Category category,
                                              @Param("orderBy") String orderBy,
                                              @Param("keyword") String keyword,
                                              Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 9);
        return testBookRepository.findBooksByFilter(category,orderBy,keyword,pageable);
    }
}
