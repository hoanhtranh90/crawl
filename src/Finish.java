
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Finish {
    private static InputStream getResource( String path ) {
        return Finish.class.getResourceAsStream( path );
    }

    private static Resource getResource(String path, String href ) throws IOException {
        return new Resource( getResource( path ), href );
    }
    public static void main(String[] args) throws IOException {
        String url = "https://truyenfull.vn/yeu-nham-nu-bang-chu-sieu-quay/";
        System.out.println("abccascasd");
            Document doc = Jsoup.connect(url).get();
            Element content = doc.getElementById("total-page");
            System.out.println("try");
            String titleBook = doc.getElementsByTag("title").text();
            ArrayList<String> listChuong = new ArrayList<>();
//        Elements getListDetai2 = getListChuong.get(0).getElementsByTag("a");
            String urlCheck = doc.getElementsByClass("list-chapter").get(0).getElementsByAttribute("href").get(0).attr("href");
            if(urlCheck.contains("quyen")){
                String totalPage = content.select("input").attr("value");
                for (int i = 1; i <= Integer.parseInt(totalPage); i++) {
                    String url1 = url+"/trang-"+i+"/";
                    Document doc1 = Jsoup.connect(url1).get();
                    Elements getListChuong = doc1.getElementsByClass("list-chapter");
                    for (int k = 0; k < getListChuong.size(); k++) {
                        for (int j = 0; j < getListChuong.get(k).getElementsByAttribute("href").size(); j++) {
                            String url2 = getListChuong.get(k).getElementsByAttribute("href").get(j).attr("href");
                            listChuong.add(url2);
                        }
                    }
                }
            }
            else {
                String totalPage = content.select("input").attr("value");

                String url1 = url+"/trang-"+Integer.parseInt(totalPage)+"/";
                Document doc1 = Jsoup.connect(url1).get();
                Elements getListChuong = doc1.getElementsByClass("list-chapter");
                String urlLast = getListChuong.get(getListChuong.size()-1)
                        .getElementsByAttribute("href")
                        .get(getListChuong.get(getListChuong.size()-1)
                                .getElementsByAttribute("href").size()-1).attr("href");
                int count = Integer.parseInt(urlLast.replaceAll("[^0-9]", ""));
                System.out.println("abccascasd");
                System.out.println(count);
                for (int i = 1; i <= count; i++) {
                    String urlv = url+"chuong-"+i+"/";
                    listChuong.add(urlv);
                }
            }
            System.out.println(listChuong);

            ArrayList<HashMap<String, String>> last = new ArrayList<>();
            HashMap<String, String> fullData = new HashMap<>();
//            for (int i = 0; i < listChuong.size(); i++) {
            for (int i = 0; i < 5; i++) {
                String url_n = listChuong.get(i);
                Document doc_n = Jsoup.connect(url_n).get();
                Element datas = doc_n.select("div.chapter-c").first();
                datas.select("div").remove();
                Element title = doc_n.getElementsByClass("truyen-title").first();
                Element chap = doc_n.getElementsByClass("chapter-title").first();
//            if(datas == null) {
//                return ResponseEntity.ok("truyen bi loi");
//            }
                HashMap<String, String> fullDatax = new HashMap<>();
                fullDatax.put("title", title.text());

                fullDatax.put("datas", datas.html());
                String h = datas.html();
                fullDatax.put("chap", chap.text());


                last.add(fullDatax);
            }
//            List<JSONObject> jsonObj = new ArrayList<JSONObject>();
//            for(HashMap<String, String> data : last) {
//                JSONObject obj = new JSONObject(data);
//                jsonObj.add(obj);
//            }
//            JSONArray test = new JSONArray(jsonObj);
//            JSONObject item = new JSONObject();
//            item.put("title", titleBook);
//            item.put("totalChap", listChuong.size());
//            item.put("listChap", test.toString());
//            System.out.println(item);
            Book book = new Book();
            book.getMetadata().addTitle("hahaha");
            book.getMetadata().addAuthor(new Author("hahahah"));
            int k = 0;
            for (int i = 0; i < last.size();i++)
            {
                HashMap<String,String> element = last.get(i);
                File file = new File("C:\\Users\\User\\IdeaProjects\\CrawlData\\src\\test"+ i +".html");
                if(!file.exists()) file.createNewFile();
                file.getParentFile().mkdirs();
                try {

                    PrintWriter prw= new PrintWriter (file);
//                    prw.write("<!DOCTYPE html>\n" +
//                            "<html>\n" +
//                            "<head>\n" +
//                            "<title>Page Title</title>\n" +
//                            "</head>\n" +
//                            "<body>\n" +
//                            "<div style='text-align: center'>"+last.get(i).get("chap") + "</div>" +
//                            "\n" +
//                            last.get(i).get("datas").replace("<br>","<br/>").replace("&nbsp;","")+
//                            "\n" +
//                            "</body>\n" +
//                            "</html>\n"
//                    );
                    prw.write(
                           "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n" +
                                   "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                                   "<head>\n" +
                                   "<title>Cover</title>\n" +
                                   "<style type=\"text/css\"> img { max-width: 100%; } </style>\n" +
                                   "</head>\n" +
                                   "<body>\n" +
                                   "<div id=\"cover-image\">\n" +
                                   "<img src=\"/images/cover.jpg\" alt=\"Title\"/>\n" +
                                   "</div>\n" +
                                   "</body>\n" +
                                   "</html>"
                    );
                    prw.close();

//                    FileWriter myWriter = new FileWriter(file);
//                    myWriter.write("<!DOCTYPE html>\n" +
//                            "<html>\n" +
//                            "<head>\n" +
//                            "<title>Page Title</title>\n" +
//                            "</head>\n" +
//                            "<body>\n" +
//                            "\n" +
//                            element.get("datas").replace("<br>","<br/>")+
//                            "\n" +
//                            "</body>\n" +
//                            "</html>\n");
//                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                try {
                    // Create new Book



                    // Set the titl

                    // Set cover image
                    // Add Chapter 1
                    book.addSection (element.get("chap"), new Resource(new FileInputStream(
                            new File ("C:\\Users\\User\\IdeaProjects\\CrawlData\\src\\test"+i+".html")), "test"+i+".html"));
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




                    // Write the Book as Epub
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        EpubWriter epubWriter = new EpubWriter ();
            File file = new File("C:\\Users\\User\\IdeaProjects\\CrawlData\\finish1.epub");
            if (!file.exists()) file.createNewFile();
        epubWriter.write (book, new FileOutputStream(file));
    }
}
