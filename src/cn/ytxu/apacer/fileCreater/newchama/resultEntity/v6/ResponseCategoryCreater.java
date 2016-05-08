package cn.ytxu.apacer.fileCreater.newchama.resultEntity.v6;

import cn.ytxu.apacer.system_platform.Config;
import cn.ytxu.apacer.entity.*;
import cn.ytxu.util.CamelCaseUtils;
import cn.ytxu.util.FileUtil;
import cn.ytxu.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求结果中的实体类类别的生成器<br>
 * Future:生成一个不是树形结构的entity.
 * 2016-04-07
 */
public class ResponseCategoryCreater {
    private static volatile ResponseCategoryCreater instance;

    private ResponseCategoryCreater() {
        super();
    }

    public static ResponseCategoryCreater getInstance() {
        if (null == instance) {
            synchronized (ResponseCategoryCreater.class) {
                if (null == instance) {
                    instance = new ResponseCategoryCreater();
                }
            }
        }
        return instance;
    }

    public void create(CategoryEntity category, ApiEnitity api, DocumentEntity doc) {
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

        final String version = api.getFormatVersion();
        name = FileUtil.getCategoryPackageName(name);
        String packageName = Config.Entity.getPackageName4Category(version, name);
        String dirPath = Config.Entity.getDirPath4Category(version, name);

        // result fields and getter and setter method of result fileds
        createResultFieldsForEachMethods(methods, packageName, dirPath);
    }

    private void createResultFieldsForEachMethods(List<MethodEntity> methods, String packageName, String dirPath) {
        // category--N-->method--1-->response(ok)--1-->output(data)--N-->output(Obj,Array)
        // 1 获取到所有output(Obj,Array)(targets)
        List<OutputParamEntity> targets = getAllSubOutput(methods);

        // 2 解析targets,并生成所有的output entity class
        // 2.1 要有一个保存所有已生成class的output的List(createdOutputs)
        List<OutputParamEntity> needCreatedOutputs = new ArrayList<>();
        // 2.2 循环遍历targets,并与createdOutputs进行对比
        if (null != targets && targets.size() > 0) {
            for (OutputParamEntity target : targets) {
                // 2.2.1 若createdOutputs.contains(target)==true,
                //       则直接continue;什么都不做,因为已经生成过了,不需要再生成该class了
                if (needCreatedOutputs.contains(target)) {
                    continue;
                }
                // 如国家\省份\城市,三级目录;他们(省份\城市)不需要生成
                if (OutputParamEntity.isSameObject(needCreatedOutputs, target)) {
                    continue;
                }
                // 2.2.2 若OutputParamEntity.hasSameName(createdOutputs, target)==true,
                //       重命名该output的类名:根据从根节点开始生成,并target.setUsedClassName(rename);
                if (OutputParamEntity.hasSameName(needCreatedOutputs, target)) {
                    String rename = target.rename4Class();
                    target.setCreatedClassName(rename);
                }
                needCreatedOutputs.add(target);
            }
            // 2.3 createdOutputs.add(target); 生成target的class
            createAllOutputClass(needCreatedOutputs, packageName, dirPath);
        }

        // 3 获取到所有output(data)(dataOutputs)
        List<OutputParamEntity> dataOutputs = getAllDataOutput(methods);
        createAllOutputClass(dataOutputs, packageName, dirPath);
    }

    private void createAllOutputClass(List<OutputParamEntity> outputs, String packageName, String dirPath) {
        if (null == outputs || outputs.size() <= 0) {
            return;
        }

        // 3.1 for each dataOutputs 生成class
        for (OutputParamEntity output : outputs) {
            OutputClassCreater.getInstance().create(output, packageName, dirPath);
        }
    }

    private List<OutputParamEntity> getAllSubOutput(List<MethodEntity> methods) {
        // category--N-->method--1-->response(ok)--1-->output(data)--N-->output(Obj,Array)
        List<OutputParamEntity> dataOutputs = getAllDataOutput(methods);
        List<OutputParamEntity> subs = new ArrayList<>();
        for (OutputParamEntity dataOutput : dataOutputs) {
            List<OutputParamEntity> list = getObjOrArrayOutputs(dataOutput);
            if (null == list || list.size() <= 0) {
                continue;
            }
            subs.addAll(list);
        }
        return subs;
    }

    private List<OutputParamEntity> getObjOrArrayOutputs(OutputParamEntity output) {
        if (null == output.getSubs() || output.getSubs().size() <= 0) {
            return null;
        }

        List<OutputParamEntity> subs = new ArrayList<>();
        for (OutputParamEntity sub : output.getSubs()) {
            if (sub.isObject() || sub.isArray()) {
                subs.add(sub);
                List<OutputParamEntity> list = getObjOrArrayOutputs(sub);
                if (null == list || list.size() <= 0) {
                    continue;
                }
                subs.addAll(list);
            }
        }
        return subs;
    }

    private List<OutputParamEntity> getAllDataOutput(List<MethodEntity> methods) {
        // category--N-->method--1-->response(ok)--1-->output(data)--N-->output(Obj,Array)
        List<OutputParamEntity> dataOutputs = new ArrayList<>();
        for (MethodEntity method : methods) {
            ResponseEntity responseEntity = getOKResponseEntity(method);
            if (null == responseEntity) {
                continue;
            }

            String dataClassName = CamelCaseUtils.toUpperFirst(method.getMethodName());
            List<OutputParamEntity> outputs = responseEntity.getOutputParams();
            OutputParamEntity dataOutput = getDataOutput(outputs);
            dataOutput.setDataClassName(dataClassName);
            dataOutputs.add(dataOutput);
        }
        return dataOutputs;
    }

    private OutputParamEntity getDataOutput(List<OutputParamEntity> outputs) {
        OutputParamEntity dataOutput = null;
        for (OutputParamEntity output : outputs) {
            if (Config.Entity.BaseResponse.Data.equals(output.getName())) {
                dataOutput = output;
                break;
            }
        }
        return dataOutput;
    }

    private ResponseEntity getOKResponseEntity(MethodEntity method) {
        List<ResponseEntity> responses = method.getResponses();
        for (ResponseEntity response : responses) {
            if ("0".equals(response.getStatusCode())) {// 只将成功的数据做成实体类
                return response;
            }
        }
        return null;
    }


}
