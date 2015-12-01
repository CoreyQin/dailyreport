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

import org.json.JSONObject;

import com.ob.dailyReport.util.DateUtil;
import com.ob.dailyReport.util.ReportGenerater;

public class ReportGenerateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reportPath = "report";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = this.getInputJson(request);
		JSONObject dataJson = new JSONObject(jsonString);
		String teamName = dataJson.getString("team");
		String dateString = dataJson.getString("date");
		String download = dataJson.getString("download");

		ServletContext sctx = getServletContext();
		String path = sctx.getRealPath("/" + reportPath);
		try {
			File report = ReportGenerater.generateReport(path, teamName, DateUtil.parseString2Date(dateString));
			if (download == null || download.equals("") || Boolean.parseBoolean(download)) {
				response.getWriter().print(reportPath + "/" + report.getName());
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
		System.out.println(buffer.toString());
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
