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
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class ParcelerCompiler extends AbstractProcessor {

    private Map<String,ElementParser> map = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        map.clear();
        System.err.println("======apt parse start");
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Arg.class);
        TypeElement type = null;
        for (Element ele : elements) {
            try {
                type = (TypeElement) ele.getEnclosingElement();
                String clzName = type.getQualifiedName().toString();
                if (!Utils.checkClassValid(type) || map.containsKey(clzName)) {
                    continue;
                }
                System.out.println(type.getQualifiedName());
                map.put(clzName,ElementParser.parse(type));
            } catch (ParcelException e) {
                error(e.getEle(),e.getMessage());
            } catch (Throwable e) {
                error(ele, "Parceler compiler generated java files failed: %s,%s", type, e.getMessage());
                e.printStackTrace();
                return true;
            }
        }

        System.err.println("======apt parse end");
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
