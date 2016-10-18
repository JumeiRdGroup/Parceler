package com.lzh.compiler.parceler.processor;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.processor.model.ElementParser;
import com.lzh.compiler.parceler.processor.model.LogUtil;
import com.lzh.compiler.parceler.processor.util.UtilMgr;
import com.lzh.compiler.parceler.processor.util.Utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class ParcelerCompiler extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        LogUtil.debug = true;
        Map<String,ElementParser> map = new HashMap<>();
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Arg.class);
        TypeElement type = null;
        try {
            for (Element ele : elements) {
                type = (TypeElement) ele.getEnclosingElement();
                String clzName = type.getQualifiedName().toString();
                if (!Utils.checkClassValid(type) || map.containsKey(clzName)) {
                    continue;
                }
                LogUtil.print(String.format("add class %s to parsed",type.getQualifiedName()));
                map.put(clzName,ElementParser.parse(type));
            }

            Set<String> keys = map.keySet();
            for (String key : keys) {
                map.get(key).generateClass();
            }

        } catch (ParcelException e) {
            LogUtil.debug = true;
            LogUtil.printException(e);
            error(e.getEle(),e.getMessage());
            return true;
        } catch (Throwable e) {
            LogUtil.debug = true;
            LogUtil.printException(e);
            error(type, "Parceler compiler generated java files failed: %s,%s", type, e.getMessage());
            return true;
        }



        return false;
    }

    private void warning(Element ele, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message,args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING,message,ele);
    }

    /**
     * compiler output method,when compiler occurs exception.should be notice here.
     *
     * @param element Element of class who has a exception when compiled
     * @param message The message should be noticed to user
     * @param args args to inflate message
     */
    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Arg.class.getCanonicalName());
        return set;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        UtilMgr.getMgr().init(processingEnv);
    }
}
