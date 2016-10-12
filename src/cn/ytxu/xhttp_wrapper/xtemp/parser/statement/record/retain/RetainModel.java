package cn.ytxu.xhttp_wrapper.xtemp.parser.statement.record.retain;

/**
 * 自动生成文件中需要保留的数据:有可能是我自己后面加的,也有可能是其他coder加的<br>
 * 例如:简化网络请求中参数的长度<br>
 * 2016-04-18<br>
 * version v6
 */
public class RetainModel {
//    要将文件中添加了数据保留：下面是保留的格式：
    //** ytxu.retain-start *//** ytxu.import */
    //** ytxu.retain-end */
    //** ytxu.retain-start *//** ytxu.field */
    //** ytxu.retain-end */
    //** ytxu.retain-start *//** ytxu.method */
    //** ytxu.retain-end */
    //** ytxu.retain-start *//** ytxu.other */
    //** ytxu.retain-end */

    private StringBuffer importSb;// 需要保留的import语句
    private StringBuffer fieldSb;// 需要保留的所有字段
    private StringBuffer methodSb;// 需要保留的所有方法
    private StringBuffer otherSb;// 需要保留的其他东东

    public static RetainModel EmptyRetain = new RetainModel();

    static {
        EmptyRetain.importSb = RetainParser.getData(RetainParser.CategoryImportTag, null);
        EmptyRetain.fieldSb = RetainParser.getData(RetainParser.CategoryFieldTag, null);
        EmptyRetain.methodSb = RetainParser.getData(RetainParser.CategoryMethodTag, null);
        EmptyRetain.otherSb = RetainParser.getData(RetainParser.CategoryOtherTag, null);
    }

    RetainModel() {
        super();
    }

    void setImportSb(StringBuffer importSb) {
        this.importSb = importSb;
    }

    void setFieldSb(StringBuffer fieldSb) {
        this.fieldSb = fieldSb;
    }

    void setMethodSb(StringBuffer methodSb) {
        this.methodSb = methodSb;
    }

    void setOtherSb(StringBuffer otherSb) {
        this.otherSb = otherSb;
    }

    //********************* getter *********************
    public StringBuffer getImportData() {
        return importSb;
    }

    public StringBuffer getFieldData() {
        return fieldSb;
    }

    public StringBuffer getMethodData() {
        return methodSb;
    }

    public StringBuffer getOtherData() {
        return otherSb;
    }

}
