package ir.cetr;

import com.google.common.collect.Lists;
import ir.cetr.clustering.KMeans;
import ir.cetr.clustering.KMeansBuilder;
import ir.cetr.clustering.Point;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Based on thesis: CETR - Content Extraction via Tag Ratios
 * <p>
 * Created by mingyao on 1/17/17.
 */
public class ContentExtractorTagRatio {
    private static volatile ContentExtractorTagRatio INSTANCE = null;
    private ContentExtractorTagRatio() {
    }

    public double countTF(String input) {
        double countTag = 0;
        double countTextLen = 0;
        boolean startTag = false;
        for (int i = 0; i < input.length(); i++) {
            String c = String.valueOf(input.charAt(i));
            if (c.equals("<")) {
                startTag = true;
            } else if (c.equals(">") && startTag) {
                countTag += 1;
                startTag = false;
            } else if (!startTag) {
                countTextLen += 1;
            }
        }
        return countTag == 0 ? countTextLen : countTextLen / countTag;
    }

    public List<Double> smooth(List<Double> tagRatioList) {
        double c1 = Math.exp(-1.0 / 8.0);
        double c2 = Math.exp(-4.0 / 8.0);
        List<Double> smoothed = Lists.newArrayList();
        smoothed.add(tagRatioList.get(0));
        smoothed.add(tagRatioList.get(1));

        for (int i = 2; i < tagRatioList.size() - 2; i++) {
            double v = tagRatioList.get(i - 2) * c2 +
                    tagRatioList.get(i - 1) * c1 +
                    tagRatioList.get(i) +
                    tagRatioList.get(i + 1) * c1 +
                    tagRatioList.get(i + 2) * c2;
            v = v / (2 * c1 + 2 * c2 + 1);
            smoothed.add(v);
        }

        int originalLength = tagRatioList.size();
        smoothed.add(tagRatioList.get(originalLength - 2));
        smoothed.add(tagRatioList.get(originalLength - 1));
        return smoothed;
    }

    public List<Double> getDerivatives(List<Double> arrs) {
        double alpha = 3;
        List<Double> sdList = Lists.newArrayList();
        for (int i = 0; i < arrs.size() - 3; i++) {
            double v = 0.0;
            for (int j = 1; j < alpha + 1; j++) {
                v += arrs.get(i + j);
            }
            v = v / 3 - arrs.get(i);
            sdList.add(Math.abs(v));
        }
        sdList.add(0.0);
        sdList.add(0.0);
        sdList.add(0.0);
        return sdList;
    }

    public List<Double> getTagRatioList(String... arrs) {
        List<Double> trList = Lists.newArrayList();
        for (String line : arrs) {
            trList.add(this.countTF(line.trim()));
        }
        return trList;
    }

    public static void compute(String... arrs) {
        if (INSTANCE == null) {
            synchronized (ContentExtractorTagRatio.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ContentExtractorTagRatio();
                }
            }
        }
        List<String> tagList = Lists.newArrayList(arrs);
        List<Double> trList = INSTANCE.getTagRatioList(arrs);
        List<Double> smoothedTrList = INSTANCE.smooth(trList);
        List<Double> derivativeList = INSTANCE.getDerivatives(smoothedTrList);
        List<Double> smoothedDList = INSTANCE.smooth(derivativeList);
        List<Point> points = new ArrayList(tagList.size());
        for (int i = 0; i < tagList.size(); i++) {
            points.add(new Point(tagList.get(i), smoothedTrList.get(i), smoothedDList.get(i)));
        }
        KMeans kmeans = new KMeansBuilder()
                .setK(3)
                .setIteration(15)
                .source(points)
                .build();
        kmeans.compute();
    }

    /**
     * http://cg.hzft.gov.cn/www/noticedetail.do?noticeguid=8aa8d09f581a1fd20158addeb1723105
     * http://www.ccgp-jiangsu.gov.cn/pub/jszfcg/cgxx/cggg/201701/t20170120_116464.html
     * http://cg.hzft.gov.cn/www/noticedetail.do?noticeguid=8a0691a3597356b701598636e59504e3
     *
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        Document doc = Jsoup.parse(new URL("http://www.ccgp-jiangsu.gov.cn/pub/jszfcg/cgxx/cggg/201701/t20170120_116464.html"), 10000);
        Whitelist wl = Whitelist.relaxed();
        wl.addAttributes(":all", "id", "class", "data");

        Document.OutputSettings os = new Document.OutputSettings();
        os.indentAmount(0).outline(true).prettyPrint(true);

        Cleaner c = new Cleaner(wl);
        doc = c.clean(doc);
        String[] arrs = doc.outputSettings(os).toString().split("\\r?\\n");

        ContentExtractorTagRatio.compute(arrs);
    }
}
