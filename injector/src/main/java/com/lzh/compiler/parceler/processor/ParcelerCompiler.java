package com.lzh.compiler.parceler.processor;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.processor.model.ElementParser;
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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by admin on 16/10/11.
 */
public class ParcelerCompiler extends AbstractProcessor {

    private Map<String,ElementParser> map = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        System.err.println("======apt解析开始");
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Arg.class);
        for (Element ele : elements) {
            try {
                TypeElement type = (TypeElement) ele.getEnclosingElement();
                String clzName = type.toString();
                if (!checkTypeElementValid(type) && map.containsKey(clzName)) {
                    continue;
                }
                map.put(clzName,ElementParser.parse(type));
            }catch (Throwable e) {
                error(ele, "Parceler tools generate java files failed: %s,%s", ele, e.getMessage());
                return true;
            }
        }
        System.err.println("======apt解析结束");
        return false;
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private static boolean checkTypeElementValid (TypeElement type) {
        Set<Modifier> modifiers = type.getModifiers();
        System.out.println(modifiers);
        if (modifiers.contains(Modifier.ABSTRACT)) {
            return false;
        }
        return true;
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
        UtilMgr mgr = UtilMgr.getMgr();
        mgr.setElementUtils(processingEnv.getElementUtils());
        mgr.setFiler(processingEnv.getFiler());
        mgr.setMessager(processingEnv.getMessager());
        mgr.setTypeUtils(processingEnv.getTypeUtils());
    }
}
