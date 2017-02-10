/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2015/1/29 17:03</create-date>
 *
 * <copyright file="Corpus.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package nlp;

import seg.ikanalyzer.core.Seg;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * a set of documents
 * 语料库，也就是文档集合
 *
 * @author hankcs
 */
public class Corpus
{
    List<int[]> documentList;
    Vocabulary vocabulary;

    public Corpus()
    {
        documentList = new LinkedList<int[]>();
        vocabulary = new Vocabulary();
    }

    public int[] addDocument(List<String> document)
    {
        int[] doc = new int[document.size()];
        int i = 0;
        for (String word : document)
        {
            doc[i++] = vocabulary.getId(word, true);
        }
        documentList.add(doc);
        return doc;
    }

    public int[][] toArray()
    {
        return documentList.toArray(new int[0][]);
    }

    public int getVocabularySize()
    {
        return vocabulary.size();
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        for (int[] doc : documentList)
        {
            sb.append(Arrays.toString(doc)).append("\n");
        }
        sb.append(vocabulary);
        return sb.toString();
    }

    /**
     * Load documents from disk
     *
     * @param folderPath is a folder, which contains text documents.
     * @return a corpus
     * @throws IOException
     */
    public static Corpus load(String folderPath) throws IOException
    {
        Corpus corpus = new Corpus();
        File folder = new File(folderPath);
        for (File file : folder.listFiles())
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            List<String> wordList = new LinkedList<String>();
            while ((line = br.readLine()) != null)
            {
                String[] words = line.split(" ");
                for (String word : words)
                {
                    if (word.trim().length() < 2) continue;
                    wordList.add(word);
                }
            }
            br.close();
            corpus.addDocument(wordList);
        }
        if (corpus.getVocabularySize() == 0) return null;

        return corpus;
    }

    public Vocabulary getVocabulary()
    {
        return vocabulary;
    }

    public int[][] getDocument()
    {
        return toArray();
    }

    public static int[] loadDocument(String path, Vocabulary vocabulary) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        List<Integer> wordList = new LinkedList<Integer>();
        while ((line = br.readLine()) != null)
        {
            String[] words = line.split(" ");
            for (String word : words)
            {
                if (word.trim().length() < 2) continue;
                Integer id = vocabulary.getId(word);
                if (id != null)
                    wordList.add(id);
            }
        }
        br.close();
        int[] result = new int[wordList.size()];
        int i = 0;
        for (Integer integer : wordList)
        {
            result[i++] = integer;
        }
        return result;
    }

    public static Corpus getSample() {
        String lines = getSampleData();

        String[] eles = lines.split("[，。\\n]");
        Corpus corpus = new Corpus();
        for (String ele : eles) {
            corpus.addDocument(Arrays.asList(Seg.segAsString(ele).split(" ")));
        }
        return corpus;
    }

    public static String getSampleData() {
        String lines = "算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法\n" +
                "算法可以宽泛的分为三类\n" +
                "一\n" +
                "有限的确定性算法\n" +
                "这类算法在有限的一段时间内终止\n" +
                "他们可能要花很长时间来执行指定的任务\n" +
                "但仍将在一定的时间内终止\n" +
                "这类算法得出的结果常取决于输入值\n" +
                "二\n" +
                "有限的非确定算法\n" +
                "这类算法在有限的时间内终止\n" +
                "然而\n" +
                "对于一个（或一些）给定的数值\n" +
                "算法的结果并不是唯一的或确定的\n" +
                "三\n" +
                "无限的算法\n" +
                "是那些由于没有定义终止定义条件\n" +
                "或定义的条件无法由输入的数据满足而不终止运行的算法\n" +
                "通常\n" +
                "无限算法的产生是由于未能确定的定义终止条件";

        return lines;
    }
}
