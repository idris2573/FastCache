package Tools;

import ScrapeAmazonCsv.ReadFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Idris on 21/07/2017.
 */
public class Cache extends Thread {

    public Document document;
    public String[] urls = ReadFile.read("cache/test.txt");
    public static String exportTo = "cacheLog.txt";

    public int count;
    public int thread;
    public static int threadAmount;
    public int attempts;

    private FileWriter fw;
    private BufferedWriter writer;

    static int completed = 0;

    public static void main (String[]args) throws IOException{

        Cache cache1 = new Cache(1);
        Cache cache2 = new Cache(2);
        Cache cache3 = new Cache(3);
        Cache cache4 = new Cache(4);
        Cache cache5 = new Cache(5);
        Cache cache6 = new Cache(6);
        Cache cache7 = new Cache(3);
        Cache cache8 = new Cache(4);
        Cache cache9 = new Cache(5);
        Cache cache10 = new Cache(6);




        cache1.createFile();

        threadAmount = 1;

        cache1.start();
//        cache2.start();
//        cache3.start();
//        cache4.start();
//        cache5.start();
//        cache6.start();
//        cache7.start();
//        cache8.start();
//        cache9.start();
//        cache10.start();


        System.in.read();

        System.out.println("Terminated Process");

        try{
            cache1.saveFile();
        }catch(Exception e){
            System.out.println("Already saved and exported");
        }
        System.out.println("Workbook Saved");

    }

    public Cache(int getThread){
        thread = getThread;
    }

    public void run(){

        try{

            get();

        }catch(Exception e){
            System.out.println("Error : " + e.getMessage());
            System.out.println("Failed to run cache on Thread " + thread);
        }

    }

    public void get() throws IOException{

        count = thread -1;

        while (count < urls.length) {



            try {

                document = Jsoup.connect(urls[count]).timeout(60000).get(); //get websitelink

//                String test = document.body().text();
//                System.out.print(test);

                completed ++;

                System.out.println(count + " : " + "cached : " + urls[count] + " (" + completed + ")");
                writer.append("success : " + urls[count] + "\n");

                count+=threadAmount;
                attempts = 0;

            }catch(Exception e){
//                attempts++;


                System.out.println(count + " : " + "failed : " + urls[count] + " : Failed to cache post...");
                writer.append("failed : " + urls[count] + "\n");
                count+=threadAmount;

//                if (attempts < 5) {
//
//                    System.out.println(count + " : " + urls[count] + " : Failed to cache post... trying again... Attempt : " + attempts);
//
//
//                }else{
//                    writer.append("failed : " + urls[count] + "\n");
//
//                    System.out.println(count + " : " + urls[count] + " : Skipping post after 5 attempts");
//
//                    count+=threadAmount;
//                    attempts = 0;
//
//                }

            }


        }

        System.out.println("Thread " + thread + " completed...");

    }


    public void createFile() throws IOException {
        File file2 = new File("cache/" + exportTo);
        fw = new FileWriter(file2.getAbsolutePath(),true);
        writer = new BufferedWriter(fw);
    }

    public void saveFile() throws IOException{

        writer.flush();
        writer.close();

        System.out.println("Links Saved");
    }

}
