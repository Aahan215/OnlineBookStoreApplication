package com.aahan.onlineBookStore.service;

import com.aahan.onlineBookStore.dao.BookDao;
import com.aahan.onlineBookStore.model.BookForm;
import com.aahan.onlineBookStore.pojo.BookPojo;
import com.aahan.onlineBookStore.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDao dao;

    @Transactional
    public void add(BookPojo p) throws ApiException {
        BookPojo existing = dao.selectById(p.getId());
        if(StringUtil.isEmpty(p.getTitle())) {
            throw new ApiException("Title cannot be empty");
        }
        if(StringUtil.isEmpty(p.getAuthor())) {
            throw new ApiException("Author cannot be empty");
        }
        if(StringUtil.isEmpty(p.getGenre())) {
            throw new ApiException("Genre cannot be empty");
        }
        if(dao.checkCombination(p.getTitle(),p.getAuthor())!=null){
            throw new ApiException("Title and Author Combination already exists");
        }
        dao.insert(p);
    }

    public BookPojo get(int id) throws ApiException{
        BookPojo p= dao.selectById(id);
        if(p==null){
            throw new ApiException("Book with given ID does not exist");
        }
        return dao.selectById(id);
    }

    @Transactional
    public List<BookPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional
    public void delete(int id) throws ApiException{
        BookPojo pojo= get(id);
        if(pojo==null){
            throw new ApiException("Book with given id already not present");
        }
        dao.delete(id);
    }

    @Transactional
    public void update(int id, BookForm form) throws ApiException{
        BookPojo pojo= get(id);
        if(pojo==null){
            throw new ApiException("Book with given id does not exist");
        }
        if(StringUtil.isEmpty(form.getTitle())) {
            throw new ApiException("Title cannot be empty");
        }
        if(StringUtil.isEmpty(form.getAuthor())) {
            throw new ApiException("Author cannot be empty");
        }
        if(StringUtil.isEmpty(form.getGenre())) {
            throw new ApiException("Genre cannot be empty");
        }
        if (dao.checkCombination(form.getTitle(), form.getAuthor() )!=null) {
            if (id != dao.checkCombination(form.getTitle(), form.getAuthor()).getId()) {
                throw new ApiException("Title and Author Combination already exists");
            }
        }
        pojo.setTitle(form.getTitle());
        pojo.setAuthor(form.getAuthor());
        pojo.setGenre(form.getGenre());
        pojo.setPrice(form.getPrice());
        pojo.setAvailability(form.getAvailability());
    }

    public List<BookPojo> getAuthor(String author) throws ApiException{
        List<BookPojo> pojo=dao.select_author(author);
        if(pojo==null){
            throw new ApiException("Book with given author does not exist");
        }
        return pojo;
    }

    public List<BookPojo> getGenre(String genre) throws ApiException{
        List<BookPojo> pojo=dao.select_genre(genre);
        if(pojo==null){
            throw new ApiException("Book with given author does not exist");
        }
        return pojo;
    }

    public List<BookPojo> getPrice(double minPrice,double maxPrice) throws ApiException{
        List<BookPojo> pojo=dao.select_greaterPrice(minPrice,maxPrice);
        if(pojo==null){
            throw new ApiException("Book with given price range does not exist");
        }
        return pojo;
    }

}
