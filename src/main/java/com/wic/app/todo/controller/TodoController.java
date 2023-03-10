package com.wic.app.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wic.app.todo.TodoRepository;
import com.wic.app.todo.model.Todo;

@Controller
public class TodoController {

	@Autowired
	protected TodoRepository todoRepository;

	@GetMapping("/todos")
	public String list(Model model, 
			@RequestParam(required = false) String keyword, 
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		try {
			Page<Todo> pageTodos;

			if (keyword == null) {
				pageTodos = todoRepository.findAll(PageRequest.of(page, size));
			} else {
				pageTodos = todoRepository.findByTitleContainingIgnoreCase(keyword,PageRequest.of(page, size));
			}

			model.addAttribute("todolist", pageTodos.getContent());
			model.addAttribute("currentPage", pageTodos.getNumber());
			model.addAttribute("totalItems", pageTodos.getTotalElements());
			model.addAttribute("totalPages", pageTodos.getTotalPages());
		}catch (Exception e) {
			model.addAttribute("messageDanger", e.getMessage());
		}
		model.addAttribute("pageTitle", "Todo List");
		return "list";
	}
	
	  @GetMapping("/todos/new")
	  public String add(Model model) {
	    model.addAttribute("todo", new Todo());
	    model.addAttribute("pageTitle", "Create new ToDo");
	    return "form";
	  }	

	  @PostMapping("/todos/save")
	  public String save(Todo todo, RedirectAttributes redirectAttributes) {
	    try {
	    	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	    	todoRepository.save(todo);
	    	System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
	      redirectAttributes.addFlashAttribute("messageSuccess", "The ToDo has been saved successfully!");
	    } catch (Exception e) {
	    	System.out.println("ccccccccccccccccccccccccccccccccccccccccccccccccc");
	    	e.printStackTrace();
	      redirectAttributes.addAttribute("messageDanger", e.getMessage());
	    }
	    return "redirect:/todos";
	  }

}
