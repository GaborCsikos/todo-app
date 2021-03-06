package hu.gabor.csikos.todoapp.controller;

import hu.gabor.csikos.todoapp.dto.TodoDTO;
import hu.gabor.csikos.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService todoService;


    @GetMapping("/list")
    public ResponseEntity<Page<TodoDTO>> getAllTodos(@PageableDefault(sort = {"name"}, direction = Sort.Direction.ASC, page = 0, size = 20) final Pageable pageable) {
        Page<TodoDTO> result = todoService.getAllTodos(pageable);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable long id) {
        TodoDTO result = todoService.getTododById(id);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/")
    public ResponseEntity<TodoDTO> createTodo(@RequestBody TodoDTO todo) {
        TodoDTO result = todoService.createTodo(todo);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable long id, @RequestBody TodoDTO todo) {
        TodoDTO result = todoService.updateTodo(id, todo);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTodo(@PathVariable long id) {
        todoService.deleteId(id);
        return HttpStatus.OK;
    }
}
