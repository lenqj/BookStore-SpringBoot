package proiect.Controller.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiect.Data.Repository.Book.BookCategoryRepository;
import proiect.Data.Repository.Book.BookRepository;
import proiect.Model.Book.Book;
import proiect.Model.Book.BookCategory;

import java.util.Optional;


@Controller
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @GetMapping
    public String displayAllBooks(@RequestParam(required = false) Integer categoryID, Model model) {
        if (categoryID == null){
            model.addAttribute("title","All Books");
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("userLogged", false);
        }else{
            Optional<BookCategory> result = bookCategoryRepository.findById(categoryID);
            if(result.isEmpty()){
                model.addAttribute("title","Invalid Category ID: " + categoryID);
                model.addAttribute("userLogged", false);
            }else{
                BookCategory category = result.get();
                model.addAttribute("title", "Books in category: " + category.getName());
                model.addAttribute("books", category.getBooks());
                model.addAttribute("userLogged", false);
            }
        }
        return "books/index";
    }


    @GetMapping("detail")
    public String displayBookDetails(@RequestParam Integer bookID, Model model) {
        Optional<Book> result = bookRepository.findById(bookID);
        if(result.isEmpty()){
            model.addAttribute("title", "Invalid Book ID:" + bookID);
            model.addAttribute("userLogged", false);
        }else{
            Book book = result.get();
            model.addAttribute("title", book.getTitle() + " Details");
            model.addAttribute("book", book);
            model.addAttribute("tags", book.getTags());
            model.addAttribute("userLogged", false);
        }
        return "books/detail";
    }

}
