package seg.ikanalyzer.core;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


/**
 * IK分词器包装器
 *
 * @author chen
 */
public class Seg {

    private static String SEPERATOR = " ";
    private static IKSegmenter seg = new IKSegmenter(new StringReader(""), true);  ////IK分词器


    public static String segAsString(String input) {
        seg.reset(new StringReader(input));
        Lexeme lex = null;
        StringBuffer sb = new StringBuffer();
        try {
            while ((lex = seg.next()) != null) {
                sb.append(lex.getLexemeText() + SEPERATOR);
//                if (lex.getLength() > 1) {
//                    int lexemeType = lex.getLexemeType();
//                    if (lexemeType == Lexeme.TYPE_ENGLISH || lexemeType == Lexeme.TYPE_CNWORD) {
//                        sb.append(lex.getLexemeText() + SEPERATOR);
//                    }
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String segAsCrf(String input) {
        seg.reset(new StringReader(input));
        Lexeme lex = null;
        StringBuffer sb = new StringBuffer();
        try {
            while ((lex = seg.next()) != null) {
                sb.append(lex.getLexemeText() + ":1" + ";");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public List<String> segAsList(String input) {
        seg.reset(new StringReader(input));
        Lexeme lex = null;
        List<String> resultList = new ArrayList<String>();
        try {
            while ((lex = seg.next()) != null) {
                resultList.add(lex.getLexemeText());
//				if(lex.getLength() > 1){
//					int lexemeType = lex.getLexemeType();
//					if(lexemeType == Lexeme.TYPE_ENGLISH || lexemeType == Lexeme.TYPE_CNWORD){
//							resultList.add(lex.getLexemeText());
//					}
//				}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }
//    public static void main(String args[]) {
//        System.out.println(Seg.segAsString("我爱中国天安门"));
//    }

}
