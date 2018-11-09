package com.comic.backend.book;

import com.comic.backend.constant.UrlConstant;
import com.comic.backend.utils.DataTablePaginationResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value = UrlConstant.BOOK_BASE_URL)
public class BookController {
    private Logger logger = LogManager.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value = UrlConstant.GET_PAGE_URL, method = RequestMethod.GET)
    public @ResponseBody
    DataTablePaginationResponse getPage(@RequestParam MultiValueMap<String, String> params) {
        logger.info("========== Start getting book page ==========");
        try {
            return bookService.getPage(params);
        } catch (Exception e) {
            return null;
        }
    }
}
