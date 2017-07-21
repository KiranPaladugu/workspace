package com.tcs.tools.yang;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class YangConfHelper {
    class YangHelperData {
        String title;
        String data;
    }

    public String getContent() {
        final StringBuilder content = new StringBuilder();
        try {
            URL url = new URL("https://tools.ietf.org/html/rfc6020");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0(Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML,like Gecko) Chrome/55.0.2883.95 Safari/537.36");
            // urlConn.setRequestProperty("Cookie", cookieStr);
            urlConnection.getInputStream();
            boolean redirect = false;
            int status = urlConnection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
            }
            if (redirect) {
                System.out.println("RedirecteURL :" + urlConnection.getHeaderField("Location"));
                urlConnection = (HttpURLConnection) new URL(
                        urlConnection.getHeaderField("Location")).openConnection();
            }
            InputStream urlInputStream = null;
            if (urlConnection.getContent() instanceof InputStream) {
                urlInputStream = (InputStream) urlConnection.getContent();
            } else {
                urlInputStream = urlConnection.getInputStream();
            }

            if (urlInputStream != null) {
                final BufferedInputStream stream = new BufferedInputStream(urlInputStream);
                final byte[] bytes = new byte[8 * 1024];
                int lenght = -1;
                while ((lenght = stream.read(bytes)) != -1) {
                    content.append(new String(bytes, 0, lenght));
                }
                stream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public void printContent() {
        try {
            String line = null;
            BufferedReader reader = new BufferedReader(new StringReader(getContent()));
            boolean flag = false;
            int separatorCount = 0;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0)
                    continue;
                if (line.contains("The") && line.contains("'s Substatements")
                        && !line.contains("....")) {
                    System.out.println(">>>>>>>>>>>>> Making true...");
                    flag = true;
                }
                if (flag) {
                    System.out.println(line);
                }
                if (flag) {
                    if (line.trim().startsWith("+---") && line.trim().endsWith("---+")) {
                        separatorCount++;
                    }
                }
                if (separatorCount >= 3) {
                    flag = false;
                    separatorCount = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<YangHelperData> getContent2() {
        List<YangHelperData> dataList = new ArrayList<>(40);
        try {
            String line = null;
            BufferedReader reader = new BufferedReader(new StringReader(getContent()));
            boolean flag = false;
            int separatorCount = 0;
            String previousLine = "";
            YangHelperData data = null;
            StringBuffer buffer = null;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0)
                    continue;
                if (line.trim().startsWith("+---") && line.trim().endsWith("---+")) {
                    separatorCount++;
                    if (!flag) {
                        flag = true;
                        data = new YangHelperData();
                        data.title = previousLine;
                        buffer = new StringBuffer();
                    }
                }
                if (flag) {
                    buffer.append(line + "\n");
                    // System.out.println(line);
                }

                if (separatorCount >= 3) {
                    flag = false;
                    separatorCount = 0;
                    data.data = buffer.toString();
                    dataList.add(data);
                }
                previousLine = line;
            }
            reader.close();
            System.out.println(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public void printFormattedData() {
        List<YangHelperData> dataList = getContent2();
        for (YangHelperData data : dataList) {
            if (isStatement(data.title)) {
                printSubstatements(data);
            }else{
                System.out.println("Skipping Table :\n"+data.data);
            }
        }
    }

    private void printSubstatements(YangHelperData data) {
        if (data != null) {
            String title = data.title;
            String cardinality=null;
            String subStmts="";
            String member = "";
            int start = title.lastIndexOf("The ");
            if (start != -1) {
                int end = title.lastIndexOf("'s Substatements");
                if (end != -1) {
                    member = title.substring(start + "The ".length(), end);
                }
                System.out.println("MemeberName : " + member);

                String tblData = data.data;
                if (tblData != null) {
                    try {
                        BufferedReader reader = new BufferedReader(new StringReader(tblData));
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            if(line.trim().length()==0 || (line.contains("+--") && line.contains("--+") && line.contains("-+-"))){
                                continue;
                            }
                            if(line.contains(" | ")){
                                String tokens[] = line.trim().replace("|" , ":").split(":");
                                if(tokens.length==4){
                                    if(tokens[1].trim().equals("substatement") && tokens[3].trim().equals("cardinality")){
                                        continue;
                                    }
                                    subStmts+=tokens[1].trim()+", ";
                                    System.out.println(member+"."+tokens[1].trim()+".Cardinality="+tokens[3].trim());
                                }
                            }else{
                                System.out.println("Skipping Line :"+line);
                            }
                        }
                        reader.close();
                        System.out.println(member+".members="+subStmts);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean isStatement(String title) {
        if (title != null && title.trim().contains("'s Substatements"))
            return true;
        return false;
    }

    public void print(String siteContent) {
        if (siteContent.trim().length() <= 0)
            return;

    }

    public static void main(String args[]) {
        YangConfHelper helper = new YangConfHelper();
        helper.printFormattedData();
        // System.out.println(helper.getContent());
    }
}
