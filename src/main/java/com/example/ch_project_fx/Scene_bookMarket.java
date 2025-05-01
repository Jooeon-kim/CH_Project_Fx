package com.example.ch_project_fx;

import java.util.ArrayList;
import java.util.List;

public class Scene_bookMarket {
    BookDAO dao = new BookDAO();
   List<Book> books = dao.getAllBooksFromDB();
   void openMarket(){

   }
}
