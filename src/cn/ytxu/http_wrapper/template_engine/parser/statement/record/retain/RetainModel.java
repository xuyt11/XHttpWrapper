package cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain;

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

    private StringBuffer importRetainContent = new StringBuffer();// 需要保留的import语句
    private StringBuffer fieldRetainContent = new StringBuffer();// 需要保留的所有字段
    private StringBuffer methodRetainContent = new StringBuffer();// 需要保留的所有方法
    private StringBuffer otherRetainContent = new StringBuffer();// 需要保留的其他东东

    public static RetainModel EmptyRetain = new RetainModel();

    RetainModel() {
        super();
    }

    public void appendImport(StringBuffer retainContent) {
        this.importRetainContent.append(retainContent);
    }

    public void appendField(StringBuffer retainContent) {
        this.fieldRetainContent.append(retainContent);
    }

    public void appendMethod(StringBuffer retainContent) {
        this.methodRetainContent.append(retainContent);
    }

    public void appendOther(StringBuffer retainContent) {
        this.otherRetainContent.append(retainContent);
    }

    //********************* getter *********************
    public StringBuffer getImportRetainContent() {
        return importRetainContent;
    }

    public StringBuffer getFieldRetainContent() {
        return fieldRetainContent;
    }

    public StringBuffer getMethodRetainContent() {
        return methodRetainContent;
    }

    public StringBuffer getOtherRetainContent() {
        return otherRetainContent;
    }

}
