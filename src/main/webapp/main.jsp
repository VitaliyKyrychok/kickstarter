<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Kickstarter</title>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen">
</head>
<body>
<div id="header">
    <div class="shell">
        <div id="top">
            <h1><a href="kickstarter">Kickstarter</a></h1>

            <div id="top-navigation"> Welcome <a href="#"><strong>Guest</strong></a> <span>|</span> <a href="#">Help</a>
                <span>|</span> <a href="#">Profile Settings</a> <span>|</span> <a href="#">Log in</a></div>
        </div>
        <div id="navigation">
            <ul>
                <li><a href="kickstarter" class="${activeHome}"><span>Home</span></a></li>
                <li><a href="categories" class="${activeCategories}"><span>Categories</span></a></li>
                <li><a href="projects" class="${activeProjects}"><span>Projects</span></a></li>
            </ul>
        </div>
    </div>
</div>
<div id="container">
    <div class="shell">
        <jsp:include page="${container}" flush="true"/>
    </div>
</div>
<div id="footer">
    <div class="shell">
        <span class="left">&copy; 2010 - CompanyName</span>
        <span class="right"> Design by <a href="http://chocotemplates.com">Chocotemplates.com</a> </span>
    </div>
</div>
</body>
</html>
