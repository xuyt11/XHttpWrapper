package cn.ytxu.http_wrapper.template_engine;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.model.version.VersionModel;
import cn.ytxu.http_wrapper.template_engine.creater.XHWTFileBaseCreater;
import cn.ytxu.http_wrapper.template_engine.creater.XHWTFileType;
import cn.ytxu.http_wrapper.template_engine.parser.model.XHWTModel;
import cn.ytxu.http_wrapper.template_engine.parser.XHWTFileParser;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class XHWTFileCreater {

    private List<VersionModel> versions;

    public XHWTFileCreater(List<VersionModel> versions) {
        this.versions = versions;
    }

    public void start() {
        createTargetFile();
    }

    private void createTargetFile() {
        for (XHWTFileType xhwtFileType : XHWTFileType.values()) {
            try {
                XHWTModel tModel = getXHWTModelByParseTemplateFile(xhwtFileType);

                List reflectDatas = xhwtFileType.getReflectiveDatas(versions);

                loopGenerateTargetFilesByReflectDatas(tModel, reflectDatas);

                LogUtil.i(XHWTFileType.class, "this template type has been successfully parsed, the type is " + xhwtFileType.name());
            } catch (XHWTFileParser.XHWTNonNeedParsedException e) {
                LogUtil.i(XHWTFileType.class, e.getMessage());
            }
        }
    }

    private XHWTModel getXHWTModelByParseTemplateFile(XHWTFileType xhwtFileType) throws XHWTFileParser.XHWTNonNeedParsedException {
        return new XHWTFileParser(xhwtFileType).start();
    }

    private void loopGenerateTargetFilesByReflectDatas(XHWTModel tModel, List reflectDatas) {
        for (Object reflectData : reflectDatas) {
            generateTargetFileByReflectData(tModel, reflectData);
        }
    }

    private void generateTargetFileByReflectData(XHWTModel tModel, Object reflectData) {
        XHWTFileBaseCreater.writeContent2TargetFileByXTempAndReflectModel(tModel, reflectData);
    }

}
