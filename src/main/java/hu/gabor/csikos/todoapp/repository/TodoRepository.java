package hu.gabor.csikos.todoapp.repository;

import hu.gabor.csikos.todoapp.entity.Todo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<Todo, Long> {
}
