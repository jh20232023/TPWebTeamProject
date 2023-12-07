package board.port.model;

import java.util.List;

import javax.servlet.http.HttpSession;

public class PortBoardService {

	PortBoardDAO dao;

	public PortBoardService() {
		dao = new PortBoardDAO();
	}

	// 게시물 저장
	public List<PortBoardVO> insertPortBoard(PortBoardVO vo) {
		dao.insertPort(vo);

		return dao.getPortList();
	}

	// 첨부파일들 저장
	public void insertPortImg(List<PortBoardImgVO> imgList) {
		dao.insertPortImg(imgList);
	}

	// 전체 게시물 검색
	public List<PortBoardVO> getPortList() {
		
		return dao.getPortList();
	}

	public PortBoardVO getPort(PortBoardVO vo) {
		PortBoardVO port = dao.getPort(vo);

		// 조회수 증가 쿼리
		dao.view(vo);

		// 첨부파일 검색 쿼리
		port.setPortImgList(dao.getPortImgs(port));

		// 유저의 게시물 목록 검색
		port.setUserPort(dao.getUserPort(port));

		// 현재 게시물의 댓글 불러오기
		port.setPortCmtList(dao.getPortCmtList(port));
		
		// 로그인 중인 유저의 좋아요 여부
		port.setIsLike(dao.isLike(vo));
		
		// 좋아요 개수
		port.setCountFav(dao.countFav(vo));

		return port;
	}

	// 이미지 1개 불러오기
	public PortBoardImgVO getImg(PortBoardImgVO vo) {
		return dao.getImg(vo);
	}

	// 게시물 수정 1 - 글, 이미지
	public void updatePortBoard(PortBoardVO vo, List<PortBoardImgVO> imgList) {
		dao.updatePortBoard(vo);
		dao.deletePortImg(vo);
		dao.insertPortImg(imgList);
	}// 게시물 수정

	// 게시물 수정 2 - 글
	public void updatePortBoard(PortBoardVO vo) {
		dao.updatePortBoard(vo);
	}// 게시물 수정

	// 게시물 삭제 -> TODO:목록으로 돌아가기
	public void deletePortBoard(PortBoardVO vo) {
		dao.deletePortBoard(vo);
	}

	// 댓글 저장 -> 댓글 업데이트된 상세보기 화면
	public void insertPortCmt(PortCmtVO vo) {
		dao.insertPortCmt(vo);
	}

	// 댓글 삭제 -> 댓글 업데이트된 상세보기 화면
	public void deletePortCmt(PortCmtVO vo) {
		dao.deletePortCmt(vo);
	}

	// 댓글 수정 -> 댓글 업데이트된 상세보기 화면
	public void updatePortCmt(PortCmtVO cmtvo) {
		dao.updatePortCmt(cmtvo);
	}
	
	// 게시물 좋아요 기능
	public void isLike(PortBoardVO vo) {
		if(vo.getIsLike()) { // true 일 경우 좋아요 off
			dao.likeOff(vo);
		}else { // false 일 경우 좋아요 on
			dao.likeOn(vo);
		}
	}
}
