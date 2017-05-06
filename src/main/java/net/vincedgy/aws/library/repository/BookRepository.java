package net.vincedgy.aws.library.repository;

import net.vincedgy.aws.library.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


/**
 * Created by vincent on 06/05/2017.
 */
@RepositoryRestResource
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    List<Book> findAll();
    List<Book> findById(Long id);
    List<Book> findByTitle(Long title);

}
