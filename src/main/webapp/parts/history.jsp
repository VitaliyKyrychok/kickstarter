<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="history" class="box">
    <div class="box-head">
        <h2 class="left">History</h2>
    </div>
    <div class="box-content">
        <c:forEach items="${projectEvents}" var="projectEvent">
            <h2>
                <c:out value="${projectEvent.eventDate}"/><span>: </span>
                <c:out value="${projectEvent.message}"/>
            </h2>
            </br>
        </c:forEach>
    </div>
</div>