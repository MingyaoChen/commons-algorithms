package ir;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mingyao on 1/18/17.
 */
public class ChildTagCount {

    static Logger logger = LoggerFactory.getLogger(ChildTagCount.class);

    private Element traverse(Element ele) {
        if (ele.children().size() == 0) {
            return null;
        }
        Element current = ele;
        for (Element child : ele.children()) {
            Element tmp = traverse(child);
            if (tmp == null) continue;
            if(tmp.children().size() > current.children().size()) {
                current = tmp;
            }
        }
        return current;
    }

    public Element getElementWithMostChildren(Document doc) {
        Elements eles = doc.select(":root");
        if (eles.size() != 1) {
            logger.error("there must be only one root element, check your html source");
            new Exception();
        }

        Element root = eles.first();
        return traverse(root);
    }

    public static String udf(String html) {
        Document doc = Jsoup.parse(html);
        ChildTagCount ctc = new ChildTagCount();
        Element ele = ctc.getElementWithMostChildren(doc);
        return ele.toString();
    }


    /**
     * http://cg.hzft.gov.cn/www/noticedetail.do?noticeguid=8aa8d09f581a1fd20158addeb1723105
     * http://www.zjzfcg.gov.cn/new/sxcggg/924198.htm
     * http://www.ccgp.gov.cn/cggg/dfgg/gkzb/201701/t20170118_7871451.htm
     * http://www.ccgp-jiangsu.gov.cn/pub/jszfcg/cgxx/cggg/201701/t20170120_116464.html
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception{
        Document doc = Jsoup.parse(new URL("http://www.ccgp.gov.cn/cggg/dfgg/gkzb/201701/t20170118_7871451.htm"), 5000);
        Whitelist wl = Whitelist.relaxed();
        wl.addAttributes(":all", "id", "class", "data");

        Document.OutputSettings os = new Document.OutputSettings();
        os.indentAmount(0).outline(true).prettyPrint(true);

        Cleaner c = new Cleaner(wl);
        doc = c.clean(doc);

        ChildTagCount ctc = new ChildTagCount();
        Element ele = ctc.getElementWithMostChildren(doc);
        System.out.println(ele.toString().replaceAll("<.*?>", "").replaceAll("&nbsp;", ""));
//        replaceAll("<.*?>", "")

    }
}
