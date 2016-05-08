package cn.ytxu.apacer.templateParser;

import cn.ytxu.apacer.config.Config;
import cn.ytxu.util.FileUtil;
import cn.ytxu.util.LogUtil;
import cn.ytxu.apacer.entity.CategoryEntity;
import cn.ytxu.apacer.entity.MethodEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by newchama on 16/3/29.
 */
public class TemplateClassCreater {

    public static void create(CategoryEntity category) {
        if (null == category) {
            LogUtil.i("this category is null, so do nothing...");
            return;
        }

        String name = category.getName().trim();
        if (null == name) {
            LogUtil.i("the name is null for this category, so do nothing...");
            return;
        }

        List<MethodEntity> methods = category.getMethods();
        if (null == methods || methods.size() <= 0) {
            LogUtil.i("the methods is null or empty for this category, so do nothing...");
            return;
        }


        String classFileName = FileUtil.getClassFileName(name);
        String classFileFullName = classFileName + ".java";
        Writer writer = null;
        BufferedReader reader = null;
        try {
            writer = FileUtil.getWriter(classFileFullName, Config.Template.DirPath);
            reader = new BufferedReader(new FileReader(new File(Config.Template.FilePath)));

            String content = null;
            boolean listMethodStart = false;
            List<String> methodContents = new ArrayList<>();
            while ((content = reader.readLine()) != null) {
                if (content.contains("{ytxu.ClassName}")) {
                    content = content.replace("{ytxu.ClassName}", classFileName);
                    writer.write(content); writer.write("\n");

                } else if (content.contains("{ytxu.list-methodStart}")) {// list method start
                    listMethodStart = true;

                } else if (listMethodStart && !content.contains("{ytxu.list-methodEnd}")) {// method loop start : add method content to list
                    methodContents.add(content);

                } else if (content.contains("{ytxu.list-methodEnd}")) {// method loop end : parserApiDocHtmlCode2DocumentEntity method content, and fill or replace it
                    listMethodStart = false;
                    TemplateMethodCreater.create(category, methodContents, writer);

                } else {
                    writer.write(content); writer.write("\n");
                }
            }

            writer.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
