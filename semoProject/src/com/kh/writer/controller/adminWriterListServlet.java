package com.kh.writer.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.vo.PageInfo;
import com.kh.writer.model.service.WriterService;
import com.kh.writer.model.vo.Writer;

/**
 * 작가 조회 리스트
 * Servlet implementation class adminWriterListServlet
 */
@WebServlet("/adminList.wr")
public class adminWriterListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminWriterListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 작가를 리스트로 조회해보겠습니다
		int listCount;			// 총 게시글 갯수
		int currentPage;		// 현재 페이지 (즉, 요청한 페이지)
		int startPage;			// 현재 페이지 하단에 보여지는 페이징 바의 시작 수
		int endPage;			// 현재 페이지 하단에 보여지는 페이징 바의 끝 수
		int maxPage;			// 전체 페이지에서의 가장 마지막 페이지
		
		int pageLimit;			// 한 페이지 하단에 보여질 페이지 최대 갯수
		int boardLimit;			// 한 페이지에 보여질 게시글 최대 갯수
		
		// 총 게시글 수
		listCount = new WriterService().getListCount();
		
		// 현재 페이지
		currentPage = 1;
		
		// 페이지 요청이 있었다면 해당 페이지를 보여주도록
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		// 하단에 보여질 페이지 수
		pageLimit = 10;
		
		// 한 페이지에 보여질 글 수
		boardLimit = 10;
		
		// 최대 페이지 수
		maxPage = (int)Math.ceil((double)listCount/boardLimit);
		
		// 하단 페이징 바의 첫 번째 페이지랑 끝 페이지
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		endPage = startPage + pageLimit - 1;
		
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		// 위에 구해진 정보들을 바탕으로 dao에서 글을 긁어올 것
		PageInfo pi = new PageInfo(listCount, currentPage, startPage, endPage, maxPage, pageLimit, boardLimit);
		
		ArrayList<Writer> list = new WriterService().selectWriter(pi);
		
		if(list != null) {
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			request.getRequestDispatcher("views/admin/writer/adminWriterList.jsp").forward(request, response);
						
		} else {
			request.setAttribute("msg", "조회 실패");
			request.getRequestDispatcher("views/common/adminErrorPage.jsp").forward(request, response);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
