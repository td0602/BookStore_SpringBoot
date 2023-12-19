package com.example.ebookapp.service;

import com.example.ebookapp.model.BookDetails;
import com.example.ebookapp.model.Category;
import com.example.ebookapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;
    @Override
    public Boolean create(BookDetails bookDetails) {
        try {
            bookRepository.save(bookDetails);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<BookDetails> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<BookDetails> findByCategoryId(Long id) {
        return bookRepository.findBookDetailsByCategoryId(id);
    }

    @Override
    public BookDetails findById(Long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public Boolean update(BookDetails bookDetails) {
        try {
            bookRepository.save(bookDetails);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            bookRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<BookDetails> search(String keyword) {
        return bookRepository.searchBook(keyword);
    }

    @Override
    public Page<BookDetails> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 9);
        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<BookDetails> getByCategory(Long id, Integer pageNo) {
        List<BookDetails> list = findByCategoryId(id);
        Pageable pageable = PageRequest.of(pageNo-1, 2);
        Integer start = (int) pageable.getOffset();
        Integer end = (int) (pageable.getOffset()+pageable.getPageSize() > list.size() ? list.size():pageable.getOffset()+pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<BookDetails>(list, pageable, findByCategoryId(id).size());
    }

    @Override
    public Page<BookDetails> search(String keyword, Integer pageNo) {
        List<BookDetails> list = search(keyword);
        Pageable pageable = PageRequest.of(pageNo-1, 2);

        Integer start = (int) pageable.getOffset();
        Integer end = (int) (pageable.getOffset() + pageable.getPageSize() > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<BookDetails>(list, pageable, search(keyword).size());
    }

    @Override
    public Page<BookDetails> getAllBookOrderByAsc(Integer pageNo) {
       Pageable pageable = PageRequest.of(pageNo-1, 9);
       return bookRepository.findAllByOrderByPriceAsc(pageable);
    }

    @Override
    public Page<BookDetails> getAllBookOrderByDesc(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 9);
        return bookRepository.findAllByOrderByPriceDesc(pageable);
    }
    //Thay the cac function Page<BookDetails> tren
    @Override
    public Page<BookDetails> getBooksByFilter(Category category, String orderBy, String keyword, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 9);
        return bookRepository.findBooksByFilter(category,orderBy,keyword,pageable);
    }
}
