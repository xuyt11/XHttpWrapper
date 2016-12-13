package cn.ytxu.http_wrapper.config.property.template_file_info;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.common.util.TextUtil;
import cn.ytxu.http_wrapper.template_engine.XHWTFileType;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Created by ytxu on 2016/12/14.
 */
public class TemplateFileInfoWrapper {
    private static TemplateFileInfoWrapper instance;

    private String xhwtConfigPath;
    private LinkedHashMap<String, TemplateFileInfoBean> templateFileInfos;

    public static TemplateFileInfoWrapper getInstance() {
        return instance;
    }

    public static void load(String xhwtConfigPath, LinkedHashMap<String, TemplateFileInfoBean> templateFileInfos) {
        LogUtil.i(TemplateFileInfoWrapper.class, "load template file info property start...");
        instance = new TemplateFileInfoWrapper(xhwtConfigPath, templateFileInfos);
        LogUtil.i(TemplateFileInfoWrapper.class, "load template file info property success...");
    }

    private TemplateFileInfoWrapper(String xhwtConfigPath, LinkedHashMap<String, TemplateFileInfoBean> templateFileInfos) {
        this.xhwtConfigPath = xhwtConfigPath;
        this.templateFileInfos = templateFileInfos;

        if (Objects.isNull(templateFileInfos) || templateFileInfos.isEmpty()) {
            throw new IllegalArgumentException("u must setup template file info property...");
        }

        judgeHasValidTemplateFileInfo(templateFileInfos);
    }

    private void judgeHasValidTemplateFileInfo(LinkedHashMap<String, TemplateFileInfoBean> templateFileInfos) {
        templateFileInfos.keySet().forEach(templateFileTypeName -> XHWTFileType.get(templateFileTypeName));

        templateFileInfos.values().forEach(templateFileInfo -> {
            if (isValidFileInfo(templateFileInfo)) {
                return;
            }
        });
        throw new IllegalArgumentException("no template file can be parsed, u must check the template file info property settting...");
    }

    private boolean isValidFileInfo(TemplateFileInfoBean templateFileInfo) {
        if (Objects.isNull(templateFileInfo) || !templateFileInfo.isNeedGenerate()) {
            return false;
        }

        String path = templateFileInfo.getPath();
        if (TextUtil.isBlank(path)) {
            return false;
        }

        File templateFile = new File(path);
        if (templateFile.isAbsolute()) {
            return templateFile.exists();
        }

        templateFile = new File(xhwtConfigPath, path);
        return templateFile.exists();
    }

    public boolean needParseTheTemplateFile(XHWTFileType tFileType) {
        TemplateFileInfoBean tFileInfo = templateFileInfos.get(tFileType.name());
        return isValidFileInfo(tFileInfo);
    }

    /**
     * @throws NonNeedParseTheTemplateFileException
     */
    public String getTemplateFileAbsolutePath(XHWTFileType tFileType) {
        if (!needParseTheTemplateFile(tFileType)) {
            throw new NonNeedParseTheTemplateFileException();
        }

        TemplateFileInfoBean tFileInfo = templateFileInfos.get(tFileType.name());

        File templateFile = new File(tFileInfo.getPath());
        if (templateFile.isAbsolute()) {
            return templateFile.getAbsolutePath();
        }

        templateFile = new File(xhwtConfigPath, tFileInfo.getPath());
        return templateFile.getAbsolutePath();
    }

    public static final class NonNeedParseTheTemplateFileException extends RuntimeException {
    }

}
