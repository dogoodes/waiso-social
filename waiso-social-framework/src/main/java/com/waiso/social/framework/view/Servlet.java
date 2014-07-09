package com.waiso.social.framework.view;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.waiso.social.framework.exceptions.CriticalUserException;
import com.waiso.social.framework.exceptions.UserException;
import com.waiso.social.framework.i18n.GerenciadorMensagem;

public abstract class Servlet<T> extends HttpServlet {
	private static final long serialVersionUID = 3299377047422968161L;
	// private Map<String, Object> webClass;
	protected ServletContext servletContext;
	
	@SuppressWarnings("unchecked")
	protected T executeWebClass(ServletRequest request, ServletResponse response, Object webClass, String invoke) {
		T executionReturn = null;
		try{
			Method m = webClass.getClass().getMethod(invoke, ServletRequest.class, ServletResponse.class);
			executionReturn = (T) m.invoke(webClass, new Object[]{request, response});
		}catch(UserException e){
			throw e;
		}catch(NoSuchMethodException e){
			String message = GerenciadorMensagem.getMessage("view.action.invalid", new Object[]{invoke, webClass.getClass().getSimpleName()});
			throw new UserException(message);
		}catch(InvocationTargetException e){
			if(e.getTargetException() instanceof UserException){
				throw (UserException) e.getTargetException();
			}
			String message = GerenciadorMensagem.getMessage(GerenciadorMensagem.ERROR_GERAL);
			throw new CriticalUserException(Servlet.class, message, e);
		}catch(IllegalAccessException e){
			String message = GerenciadorMensagem.getMessage(GerenciadorMensagem.ERROR_GERAL);
			throw new CriticalUserException(Servlet.class, message, e);
		}
		return executionReturn;
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.servletContext = config.getServletContext();
		// NFeLoader loader = new NFeLoader();
		// this.webClass = loader.getWebClass();
	}

	protected void writeBinary(InputStream inStream, HttpServletResponse response) throws FileNotFoundException, IOException {
		OutputStream out = response.getOutputStream();
		InputStream in = null;
		try{
			in = new BufferedInputStream(inStream);
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			int bytesRead;
			while((bytesRead = in.read(buf)) != -1){
				out.write(buf, 0, bytesRead);
			}
		}finally{
			if(in != null){
				in.close();
			}
		}
	}

	protected PrintWriter getWriter(PrintWriter out, ServletResponse response) throws IOException {
		if(out == null){
			out = response.getWriter();
		}
		return out;
	}
}