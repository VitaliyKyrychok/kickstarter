<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="faqs" class="box">
    <div class="box-head">
        <h2 class="left">Comments</h2>
    </div>
    <div class="box-content">
        <div class="box">
            <form action="faq" method="post">
                <div class="form">
                    <p><span class="req">max 500 symbols</span>
                        <label>Your question <span>(Required Field)</span></label>
                        <textarea name="question" class="field size1" rows="10" cols="10"></textarea>
                    </p>
                </div>
                <div class="buttons">
                    <input type="submit" class="button" value="Add">
                </div>
                <input type="hidden" name="projectId" value="${project.id}"/>
            </form>
        </div>
        <table cols="2" width="100%">
            <c:forEach items="${faqs}" var="faq">
                <tr>
                    <td colspan="2">
                        <h2>
                            <c:out value="${faq.question}"/>
                        </h2>
                    </td>
                </tr>
                <tr>
                    <td width="30px"></td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty faq.answer}">
                                <h2>
                                    <c:out value="${faq.answer}"/>
                                </h2>
                            </c:when>
                            <c:otherwise>
                                <h2>
                                    &laquo;this question has no answer yet&raquo;
                                </h2>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>