package net.vincedgy.aws.library.repository;

import net.vincedgy.aws.library.model.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by vincent on 06/05/2017.
 */
@RepositoryRestResource
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
    List<Person> findAll();
    List<Person> findById(Long id);
}
