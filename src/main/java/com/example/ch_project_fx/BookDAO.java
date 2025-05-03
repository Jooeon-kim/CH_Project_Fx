package com.example.ch_project_fx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // 책 추가
    public void saveBookToDB(Book book) {
        String sql = "INSERT INTO book (isbn, title, date, author, description, category, publishDate, amount, publisher, price, imgPath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setInt(3, book.getDate());
            pstmt.setString(4, book.getAuthor());
            pstmt.setString(5, book.getDescription());
            pstmt.setString(6, book.getCategory());
            pstmt.setString(7, book.getPublishDate());
            pstmt.setInt(8, book.getAmount());
            pstmt.setString(9, book.getPublisher());
            pstmt.setInt(10, book.getPrice());
            pstmt.setString(11, book.getImgPath());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 책 타이틀로 검색 (contains)
    public List<Book> searchBooksByTitle(String keyword) {
        String sql = "SELECT * FROM book WHERE title LIKE ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // 책 저자로 검색
    public List<Book> searchBooksByAuthor(String author) {
        String sql = "SELECT * FROM book WHERE author LIKE ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + author + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // 책 카테고리로 검색
    public List<Book> searchBooksByCategory(String category) {
        String sql = "SELECT * FROM book WHERE category LIKE ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + category + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // 가격순 정렬
    public List<Book> getBooksSortedByPrice() {
        String sql = "SELECT * FROM book ORDER BY price";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // ResultSet을 Book 객체로 변환
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        String isbn = rs.getString("isbn");
        String title = rs.getString("title");
        int date = rs.getInt("date");
        String author = rs.getString("author");
        String description = rs.getString("description");
        String category = rs.getString("category");
        String publishDate = rs.getString("publishDate");
        int amount = rs.getInt("amount");
        String publisher = rs.getString("publisher");
        int price = rs.getInt("price");
        String imgPath = rs.getString("imgPath");

        // Book 객체는 이미지 경로로 이미지 생성 이미 처리하므로 추가로 Image 객체 생성은 필요하지 않음
        return new Book(isbn, title, date, author, description, category, publishDate, amount, publisher, price, imgPath);
    }
    public List<Book> getAllBooksFromDB() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";  // 책 테이블에서 모든 책을 가져오는 쿼리

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // 각 책 정보 추출
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                int date = rs.getInt("date");
                String author = rs.getString("author");
                String description = rs.getString("description");
                String category = rs.getString("category");
                String publishDate = rs.getString("publishDate");
                int amount = rs.getInt("amount");
                String publisher = rs.getString("publisher");
                int price = rs.getInt("price");
                String imgPath = rs.getString("imgPath");
                // Book 객체 생성하여 리스트에 추가
                Book book = new Book(isbn, title, date, author, description, category, publishDate, amount, publisher, price, imgPath);
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books; // 모든 책 리스트 반환
    }
    public void increaseBookAmount(String isbn, int amountToAdd) {
        String sql = "UPDATE book SET amount = amount + ? WHERE isbn = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, amountToAdd);
            pstmt.setString(2, isbn);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

