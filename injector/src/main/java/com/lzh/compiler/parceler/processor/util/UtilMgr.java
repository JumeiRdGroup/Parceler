package com.lzh.compiler.parceler.processor.util;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * 单例。存放处理器相关工具。方便在各处任意调用
 */
public class UtilMgr {

	private Elements elementUtils = null;
	private Filer filer = null;
	private Messager messager = null;
	private Types typeUtils = null;

	private static UtilMgr mgr = new UtilMgr();

	private UtilMgr() {
	}

	public static UtilMgr getMgr() {
		return mgr;
	}

	public Elements getElementUtils() {
		return elementUtils;
	}

	public void setElementUtils(Elements elementUtils) {
		this.elementUtils = elementUtils;
	}

	public Filer getFiler() {
		return filer;
	}

	public void setFiler(Filer filer) {
		this.filer = filer;
	}

	public Messager getMessager() {
		return messager;
	}

	public void setMessager(Messager messager) {
		this.messager = messager;
	}

	public Types getTypeUtils() {
		return typeUtils;
	}

	public void setTypeUtils(Types typeUtils) {
		this.typeUtils = typeUtils;
	}

}
