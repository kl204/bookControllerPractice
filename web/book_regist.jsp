<%--
  Created by IntelliJ IDEA.
  User: 금정산2_PC12
  Date: 2023-06-30
  Time: 오전 9:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>도서 등록</title>
    <style>
        table, td, th {
            border : 1px solid black;
            border-collapse: collapse;
            margin: 20px auto;
        }
        td {
            width: 150px;
            height: 50px;
            padding: 5px;
            font-size: 20px;
            /* text-align: center; */
        }

        input , select {
            font-size: 20px;
        }

        button {
            font-size: 15px;
            margin: 5px;
        }


        #sending {
            text-align: center;
        }


        #form {
            font-size: 30px;
        }

    </style>
    <script type="text/javascript" src="https://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#regist').on('click',function(){
                if(checkValidationAll()){
                    alert('저장합니다');
                    $('#frm').attr('action','/bookController?cmd=regist');
                    $('#frm').submit();
                }else{
                    alert('입력 형식을 다시 확인해 주세요!');
                }
            });
        });
    </script>
</head>
<body>
<%-- <% String cmd = request.getParameter("cmd"); %>
<%=cmd.equals("success")?"<script>alert('hello');</script>":""%> --%>
<form action="" method="post" id="frm">
    <table>
        <tr><th colspan="4" id="form">도서등록</th></tr>
        <tr><th>구분</th><th class="data_ui" colspan="2">데이터입력</th><th>비고</th></tr>
        <tr>
            <td>도서번호</td>
            <td colspan="2">
                <input type="text" id="book_seq" name="book_seq" disabled="disabled">
            </td>
            <td id="message">자동생성</td></tr>
        <tr>
            <td>ISBN</td>
            <td colspan="2" id="isbnChk">
                <input type="text" id="book_isbn" name="book_isbn">
            </td>
            <td>
                <input type="hidden" id="flag" value="false">
            </td>
        </tr>
        <tr>
            <td>도서명</td>
            <td colspan="2" id="titleChk">
                <input type="text" id="book_title" name="book_title">
            </td><td></td>
        </tr>
        <tr>
            <td>저자/역자</td>
            <td colspan="2" id="authorChk">
                <input type="text" id="book_author" name="book_author">
            </td><td></td>
        </tr>
        <tr>
            <td>출판사</td>
            <td colspan="2" id="pubChk">
                <input type="text" id="book_publisher" size="35" name="publisher">
            </td><td></td>
        </tr>
        <tr>
            <td>출판일</td>
            <td colspan="2" id="pubDateChk">
                <input type="text" id="book_published_date" size="35" name="book_published_date">
            </td>
            <td></td>
        <tr>
        <tr>
            <td>도서위치</td>
            <td colspan="2">
                <select name="book_position" disabled="disabled">
                    <option value='BS'>--도서 위치--
                    <option value='BS-001' selected>일반서가
                    <option value='BS-002'>예약서가
                    <option value='BS-'>회원
                </select>
            </td>
            <td>기본값삽입</td>
        <tr>
        <tr>
            <td>도서상태</td>
            <td colspan="2">
                <select name="book_status" disabled="disabled">
                    <option value='BM'>--도서 상태--
                    <option value='BM-001' selected>도서대출서비스
                    <option value='BM-002'>도서수선
                    <option value='BM-003'>도서저장고
                </select>
            </td>
            <td>기본값삽입</td>
        <tr>
        <tr>
            <td colspan="4" id="sending">
                <input type="button" id="regist" value="도서등록">
                <input type="reset">
                <a href="/bookListServlet">도서리스트</a>
            </td>
        </tr>
    </table>
</form>

<script src="/validation.js"></script>

</body>
</html>
