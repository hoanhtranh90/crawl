import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Crawl {
    public static void main(String[] args) {
        String url = "https://truyenfull.vn/duoi-vuong-trieu-co-dai/";
        System.out.println("abccascasd");
        try {
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
                    String urlv = url+"/chuong-"+i+"/";
                    listChuong.add(urlv);
                }
            }
            System.out.println(listChuong);

            ArrayList<HashMap<String, String>> last = new ArrayList<>();
            HashMap<String, String> fullData = new HashMap<>();
//            for (int i = 0; i < listChuong.size(); i++) {
            for (int i = 0; i < 1; i++) {
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

            File file = new File("/Users/sangnk/IdeaProjects/untitled/src/test1.html");
            file.getParentFile().mkdirs();
            try {
                FileWriter myWriter = new FileWriter(file);
                myWriter.write("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<title>Page Title</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        last.get(0).get("datas").replace("<br>","<br/>").replace("&nbsp;","")+
                        "\n" +
                        "</body>\n" +
                        "</html>\n");
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Loi timeOut");
        }
    }
}

