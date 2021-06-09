

import java.io.*;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;

import nl.siegmann.epublib.epub.EpubWriter;

public class Translator {
    private static InputStream getResource( String path ) {
        return Translator.class.getResourceAsStream( path );
    }

    private static Resource getResource( String path, String href ) throws IOException {
        return new Resource( getResource( path ), href );
    }

    public static void main(String[] args) {
        try {
            // Create new Book
            Book book = new Book();
            Metadata metadata = book.getMetadata();

            // Set the titl
            book.getMetadata().addTitle("hahaha");
            book.getMetadata().addAuthor(new Author("hahahah"));
            // Set cover image
            // Add Chapter 1
                book.addSection ("introduce", new Resource (new FileInputStream(
                    new File ("/Users/sangnk/IdeaProjects/untitled/src/test1.html")), "test1.html"));
            // Add css file
            // Create EpubWriter


//            EpubWriter epubWriter = new EpubWriter();
//            FileOutputStream fos;
//            File file = new File("/Users/sangnk/IdeaProjects/untitled/src/test1.epub");
//            if(!file.exists()){
//                file.createNewFile();
//            }
//            fos = new FileOutputStream(file);
//            epubWriter.write(book, fos);


            EpubWriter epubWriter = new EpubWriter ();
            epubWriter.write (book, new FileOutputStream (new File ("/Users/sangnk/IdeaProjects/untitled/src/sangdz.epub")));

            // Write the Book as Epub
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}