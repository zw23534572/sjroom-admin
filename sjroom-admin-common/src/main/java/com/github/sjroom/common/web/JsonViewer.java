package com.github.sjroom.common.web;

import com.github.sjroom.common.util.JsonUtil;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 
 * @author luobinwen
 *
 */
public class JsonViewer extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(JsonUtil.toJson(model));
		writer.flush();

	}

}
