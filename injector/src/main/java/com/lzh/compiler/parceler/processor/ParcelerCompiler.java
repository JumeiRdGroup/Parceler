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

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            parseArgElement(roundEnv);
        } catch (ParcelException e) {
            e.printStackTrace();
            error(e.getEle(),e.getMessage());
            return true;
        }
        return false;
    }

    /**
     * Parse elements with {@link Arg}
     * @throws ParcelException throws to notify user it occurs an exception.
     */
    private Map<TypeElement,ElementParser> parseArgElement(RoundEnvironment roundEnv) throws ParcelException{
        Map<TypeElement,ElementParser> parserMap = new HashMap<>();
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Arg.class);
        TypeElement type = null;
        try {
            // parse and get data from elements
            for (Element ele : elements) {
                type = (TypeElement) ele.getEnclosingElement();
                if (parserMap.containsKey(type) || !Utils.checkClassValid(type)) {
                    continue;
                }
                // parse and put into parser map.
                parserMap.put(type,ElementParser.parse(type));
            }
            // generate injector class
            Set<TypeElement> keys = parserMap.keySet();
            for (TypeElement key : keys) {
                parserMap.get(key).generateClass();
            }
        } catch (ParcelException e) {
            throw e;
        } catch (Throwable e) {
            throw new ParcelException(String.format("Parceler compiler generated java files failed: %s,%s", type, e.getMessage()),e,type);
        }
        return parserMap;
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
