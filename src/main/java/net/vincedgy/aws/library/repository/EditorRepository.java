package net.vincedgy.aws.library.repository;

import net.vincedgy.aws.library.model.Editor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by vincent on 06/05/2017.
 */
@RepositoryRestResource
public interface EditorRepository extends PagingAndSortingRepository<Editor, Long> {
    List<Editor> findAll();
    List<Editor> findById(Long id);
}
