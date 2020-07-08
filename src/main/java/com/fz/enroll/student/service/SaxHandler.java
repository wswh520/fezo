package com.fz.enroll.student.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**************************************************
 SAX解析器顺序的处理XML文件中的内容，并使用回叫方法向应用程序表明它何时遇到文件中的
 每个项，这被称为用于解析的基于事件的编程接口，在解析器顺序的通过文件的过程中，每标识
 一个标记就被认为是一个事件。
 要使用SAX，就必须实现org.xml.sax.ContentHandler接口，通常，通过扩充org.xml.
 sax.DefaultHanlder类来完成这个任务，因为它提供了SAX处理程序中所有回叫方法的默认
 实现。然后可以重写接口的方法，当遇到XML文档的各个不同部分时，指定应该采取的动作
 **************************************************/

/**
 * 
 * 使用SAX解析XML文档，使其能格式化输出
 */
public class SaxHandler extends DefaultHandler {
	private final String sampleListTrTag = "w:keysfor_sampleList_tr";
	private final String reportTableListTag = "w:keysfor_tableList";
	private String tmplBasePath;//模板根路径
	private OutputStream out;//输出
	private Map<String,String> dataMap;//当前待填充数据
	@SuppressWarnings("unchecked")
	private List<Map> subDataList;//子项待填充数据

	@SuppressWarnings("unchecked")
	public SaxHandler(String tmplBasePath,OutputStream out,Map<String,String> dataMap,List<Map> subDataList) {
		this.tmplBasePath = tmplBasePath;
		this.out = out;
		this.dataMap = dataMap;
		this.subDataList = subDataList;
	}

	/**
	 * 接收元素开始的通知。
	 * 
	 * @param uri
	 *            - 名称空间 URI，如果元素没有任何名称空间 URI，或者没有正在执行名称空间处理，则为空字符串。
	 * @param localName
	 *            - 本地名称（不带前缀），如果没有正在执行名称空间处理，则为空字符串。
	 * @param qName
	 *            - 限定的名称（带有前缀），如果限定的名称不可用，则为空字符串。
	 * @param attributes
	 *            - 附加到元素的属性。如果没有属性，则它将是空的 Attributes 对象。
	 * @exception SAXException
	 *                - 任何 SAX 异常，可能包装另外的异常。
	 */
	@SuppressWarnings("unchecked")
	public void startElement(String uri, String localName, String qName,
			Attributes attrs) throws SAXException {
		if(sampleListTrTag.equals(qName)){
			if(subDataList==null){
				return ;
			}
			SAXParserFactory factory = SAXParserFactory.newInstance();
			InputStream xmlStream = null;
			try {
				SAXParser parser = factory.newSAXParser();
				for(Map<String,String> _data:subDataList){
					SaxHandler handler = new SaxHandler(tmplBasePath,out,_data,null);
					xmlStream = new FileInputStream(new File(tmplBasePath+"sampleList_tr.xml"));
					parser.parse(xmlStream, handler);
				}
			} catch (ParserConfigurationException e) {
				throw new IllegalArgumentException(e);
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException(e);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}finally{
				if(xmlStream!=null){
					try{xmlStream.close();}catch(Exception e){}
				}
			}
			return ;
		}else if(reportTableListTag.equals(qName)){
			if(subDataList==null){
				return ;
			}
			SAXParserFactory factory = SAXParserFactory.newInstance();
			InputStream xmlStream = null;
			try {
				SAXParser parser = factory.newSAXParser();
				for(int i=0;i<subDataList.size();i++){
					Map<String,String> _data = subDataList.get(i);
					SaxHandler handler = new SaxHandler(tmplBasePath,out,_data,null);
					xmlStream = new FileInputStream(new File(tmplBasePath+_data.get("tmpl")));
					parser.parse(xmlStream, handler);
					//分页
					if(i<subDataList.size()-1){
						byte[] data = "<w:p wsp:rsidR=\"007A731E\" wsp:rsidRDefault=\"007A731E\"/><w:p wsp:rsidR=\"009E4560\" wsp:rsidRDefault=\"007A731E\"><w:r><w:br w:type=\"page\"/></w:r></w:p>".toString().getBytes("UTF-8");
						out.write(data, 0, data.length);
					}
				}
			} catch (ParserConfigurationException e) {
				throw new IllegalArgumentException(e);
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException(e);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}finally{
				if(xmlStream!=null){
					try{xmlStream.close();}catch(Exception e){}
				}
			}
			return ;
		}
		StringBuffer xml = new StringBuffer();
		xml.append("<");
		if (!uri.equals(""))
			xml.append(uri + ":");
		xml.append(qName);
		for (int i = 0, size = attrs.getLength(); i < size; i++) {
			xml.append(' ').append(attrs.getQName(i)).append("=\"").append(
					attrs.getValue(i).replaceAll("\"", "&quot;")).append("\"");
		}
		xml.append(">");
		try {
			byte[] data = xml.toString().getBytes("UTF-8");
			out.write(data, 0, data.length);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 接收元素结束的通知。
	 * 
	 * @param uri
	 *            - 名称空间 URI，如果元素没有任何名称空间 URI，或者没有正在执行名称空间处理，则为空字符串。
	 * @param localName
	 *            - 本地名称（不带前缀），如果没有正在执行名称空间处理，则为空字符串。
	 * @param qName
	 *            - 限定的名称（带有前缀），如果限定的名称不可用，则为空字符串。
	 * @exception SAXException
	 *                - 任何 SAX 异常，可能包装另外的异常。
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(sampleListTrTag.equals(qName)
				||reportTableListTag.equals(qName)){
			return ;
		}
		StringBuffer xml = new StringBuffer();
		xml.append("</");
		if (!uri.equals(""))
			xml.append(uri + ":");
		xml.append(qName).append(">");
		try {
			byte[] data = xml.toString().getBytes("UTF-8");
			out.write(data, 0, data.length);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 接收元素中字符数据的通知。
	 * 
	 * @param ch
	 *            - 字符。
	 * @param start
	 *            - 字符数组中的开始位置。
	 * @param length
	 *            - 从字符数组中使用的字符数。
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String text = new String(ch, start, length);
		if (!text.isEmpty()) {
			if(text.trim().startsWith("$")){
				text = dataMap.get(text.trim().substring(1));
				text = text==null?"":text;
//				text = dataMap.get(text.trim().substring(1))==null?text:dataMap.get(text.trim().substring(1));
			}
			try {
				byte[] data = text.toString().getBytes("UTF-8");
				out.write(data, 0, data.length);
			} catch (UnsupportedEncodingException e) {
				throw new IllegalArgumentException(e);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}

	}
}