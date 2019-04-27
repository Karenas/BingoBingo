package com.gmself.stidio.gm.mg_baselib.test;


import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

/**
 * Created by guomeng on 3/23.
 */

@AutoService(Process.class)
/**拥有注解处理器能力*/
public class BingoAbstractProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    //增加JDK 的支持版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    //定义一个用来生成JAVA文件的对象
    Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     * 按自定义规则生成数据
     *
     * Set<? extends Element>
     *  package com.example.XXX      -->    PackageElement
     *  public class XXX{}       -->     TypeElement
     *   private int a       -->   VariableElement
     *   private XXX other    -->   VariableElement
     *   public XXX(){}       -->   ExecuteableElement
     *   public void method(){}   -->   ExecuteableElement
     */

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //roundEnvironment用来找到含有自定义注解的java文件信息
        Set<? extends Element> elementSet = roundEnvironment.getElementsAnnotatedWith(BindView.class);

        Map<String, List<VariableElement>> cacheMap = new HashMap<>();
        for (Element element : elementSet){
            VariableElement variableElement = (VariableElement) element;
            //bingobingo
            String activityName = getActivityName(variableElement);
            List<VariableElement> list = cacheMap.get(activityName);
            //判断是否分类
            if (list == null){
                list = new ArrayList<>();
                cacheMap.put(activityName, list);
            }
            list.add(variableElement);
        }
        //分类完成，生成java文件

        Iterator iterator = cacheMap.keySet().iterator();
        while (iterator.hasNext()){
            //准备生成java需要的信息

            //activity名称
            String activityName = (String) iterator.next();

            //当前activity中所有成员
            List<VariableElement> cacheElements = cacheMap.get(activityName);

            //包名
            String packageName = getPackageName(cacheElements.get(0));

            //类名
            String newActivityBinder = activityName+"$ViewBinder";

            //开始写java文件
            Writer writer = null;
            try {
                JavaFileObject javaFileObject = filer.createSourceFile(newActivityBinder);
                writer = javaFileObject.openWriter();
//                String activitySimpleName = cacheElements.get(0).getEnclosingElement().getSimpleName().toString();
                writer.write("package "+packageName+";");
                writer.write("\n");
                writer.write("import "+packageName+".ViewBinder;");
                writer.write("\n");
                writer.write("public class "+newActivityBinder+" implements ViewBinder<"+activityName+">{");
                writer.write("\n");
                writer.write("public void bind("+activityName+" target){");
                writer.write("\n");
                for (VariableElement variableElement : cacheElements){
                    BindView bindView = variableElement.getAnnotation(BindView.class);
                    //拿到成员变量
                    String fieldName = variableElement.getSimpleName().toString();
                    TypeMirror typeMirror = variableElement.asType();
                    writer.write("target."+fieldName+" = ("+typeMirror.toString()+")target.findViewById("+bindView.value()+");");
                    writer.write("\n");
                }
                writer.write("}");
                writer.write("\n");
                writer.write("}");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    private String getActivityName(VariableElement variableElement) {
        String packageName = getPackageName(variableElement);
        TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
        return packageName+"."+typeElement.getSimpleName().toString();
    }

    private String getPackageName(VariableElement variableElement){
        TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        return packageName;
    }


}
