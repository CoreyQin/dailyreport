package com.ob.dailyReport.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.ob.dailyReport.service.ReportGenerateService;
import com.ob.dailyReport.util.Constant;
import com.ob.dailyReport.util.DateUtil;
import com.ob.dailyReport.util.ReportFileManager;

public class ReportGenerateServlet extends HttpServlet {
	
	private static Logger log = Logger.getLogger(ReportGenerateServlet.class);
	
	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext sctx = getServletContext();
		String path = sctx.getRealPath("/" + Constant.reportFilePath);
		ReportFileManager.getInstance().setReportPath(path);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = this.getInputJson(request);
		log.info("input string : " + jsonString);
		
		JSONObject dataJson = new JSONObject(jsonString);
		String teamName = dataJson.getString("team");
		String dateString = dataJson.getString("date");
		String download = dataJson.getString("download");
		try {
			File report = ReportGenerateService.generateReport(ReportFileManager.getInstance().getReportPath(), teamName, DateUtil.parseString2Date(dateString));
			if (download == null || download.equals("") || Boolean.parseBoolean(download)) {
				response.getWriter().print(Constant.reportFilePath + "/" + report.getName());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getInputJson(HttpServletRequest request) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuffer buffer = new StringBuffer();
		String str;
		while ((str = reader.readLine()) != null) {
			buffer.append(str);
		}
		// reader.close();
		return buffer.toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
