<%--
  Created by IntelliJ IDEA.
  User: 금정산2_PC12
  Date: 2023-06-30
  Time: 오전 9:11
  To change this template use File | Settings | File Templates.
--%>
<%@page import="vo.bookVo"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>도서 상세</title>
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
        .data_ui {
            /* width: 250px; */
            height: 50px;
        }

        button {
            font-size: 15px;
            margin: 5px;
        }


        #sending {
            text-align: center;
        }

        input.poster :disabled {
            background: gray;
        }

        #form {
            font-size: 30px;
        }

        #message {
            color: red;
        }
    </style>
    <script type="text/javascript" src="https://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#update').on('click',function(){
                if(checkValidationAll()){
                    alert("업데이트 되었습니다!");
                    $('#frm').attr('action','/bookController?cmd=update');
                    $('#frm').submit();
                }else{
                    alert('입력 형식을 다시 확인해 주세요!');
                }
            });
        });
    </script>
</head>
<body>

<%
    HashMap<String, Object> copies = (HashMap<String, Object>)request.getAttribute("copy");
    System.out.print("in book_detail : ");
    System.out.println(copies);
%>
<form action="" method="post" id="frm">
    <table>
        <tr><th colspan="4" id="form">도서 상세</th></tr>
        <tr><th>구분</th><th class="data_ui" colspan="2">데이터입력</th><th>비고</th></tr>
        <tr>
            <td>도서번호</td>
            <td colspan="2">
                <input type="text" id="book_seq" name="book_seq" value="<%= copies.get("book_seq")%>">
            </td>
            <td id="message"></td>
        </tr>

        <tr>
            <td>ISBN</td>
            <td colspan="2">
                <input type="text" id="book_isbn" name="book_isbn" value="<%= copies.get("book_isbn")%>">
            </td>
            <td>
                <input type="hidden" id="flag" value="false">
            </td>
        </tr>

        <tr>
            <td>도서명</td>
            <td colspan="2">
                <input type="text" id="book_title" name="book_title" value="<%= copies.get("book_title")%>">
            </td><td></td>
        </tr>

        <tr>
            <td>저자/역자</td>
            <td colspan="2">
                <input type="text" id="book_author" name="book_author" value="<%= copies.get("book_author")%>">
            </td><td></td>
        </tr>

        <tr>
            <td>출판사</td>
            <td colspan="2">
                <input type="text" id="book_publisher" size="35" name="book_publisher" value="<%= copies.get("book_publisher")%>">
            </td><td></td>
        </tr>

        <tr>
            <td>출판일</td>
            <td colspan="2">
                <input type="text" id="book_published_date" size="35" name="book_published_date" value="<%= copies.get("book_published_date")%>">
            </td>
            <td></td>
        </tr>
        <tr>
            <td>도서위치</td>
            <td colspan="2">
                <select name="book_position">

                     <%if (copies.get("book_position").equals("BS")) { %>
                    <option value="BS" selected>--도서 위치--</option>
                    <% } else { %>
                    <option value="BS">--도서 위치--</option>
                    <% } %>

                    <% if (copies.get("book_position").equals("BS-001")) { %>
                    <option value="BS-001" selected>일반서가</option>
                    <% } else { %>
                    <option value="BS-001">일반서가</option>
                    <% } %>

                    <% if (copies.get("book_position").equals("BS-002")) { %>
                    <option value="BS-002" selected>예약서가</option>
                    <% } else { %>
                    <option value="BS-002">예약서가</option>
                    <% } %>

                    <% if (copies.get("book_position").equals("BS-")) { %>
                    <option value="BS-" selected>회원</option>
                    <% } else { %>
                    <option value="BS-">회원</option>
                    <%  }%>
                </select>

            </td>
            <td></td>
        </tr>

        <tr>
            <td>도서상태</td>
            <td colspan="2">
                <select name="book_status">
                    <% if (copies.get("book_status").equals("BM")) { %>
                    <option value="BM" selected>--도서 상태--</option>
                    <% } else { %>
                    <option value="BM">--도서 상태--</option>
                    <% } %>

                    <% if (copies.get("book_status").equals("BS-001")) { %>
                    <option value="BM-001" selected>도서대출서비스</option>
                    <% } else { %>
                    <option value="BM-001">도서대출서비스</option>
                    <% } %>

                    <% if (copies.get("book_status").equals("BS-002")) { %>
                    <option value="BM-002" selected>도서수선</option>
                    <% } else { %>
                    <option value="BM-002">도서수선</option>
                    <% } %>

                    <% if (copies.get("book_status").equals("BS-")) { %>
                    <option value="BM-003" selected>도서저장고</option>
                    <% } else { %>
                    <option value="BM-003">도서저장고</option>
                    <% }%>
                </select>
            </td>
            <td></td>
        </tr>

        <tr>
            <td colspan="4" id="sending">
                <input type="submit" id="update" value="도서수정">
                <input type="reset">
                <a href="./bookController?cmd=list">도서리스트</a>
            </td>
        </tr>

    </table>
</form>

<script src="/validation.js"></script>

</body>
</html>
