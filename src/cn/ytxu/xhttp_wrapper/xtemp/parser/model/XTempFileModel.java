package cn.ytxu.xhttp_wrapper.xtemp.parser.model;

import java.util.List;

/**
 * xhwt文件，中生成文件的路径与名称；
 * 2016-12-11
 */
public class XTempFileModel {
    private String fileName;// 文件的名称
    private List<XTempFileDirModel> fileDirs;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<XTempFileDirModel> getFileDirs() {
        return fileDirs;
    }

    public void setFileDirs(List<XTempFileDirModel> fileDirs) {
        this.fileDirs = fileDirs;
    }
}
