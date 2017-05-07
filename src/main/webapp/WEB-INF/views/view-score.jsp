<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>查看成绩</title>
<link href="../../../static/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<style>
.pop-listtab th {
    background: #146fc3 none repeat scroll 0 0;
    border-bottom: 1px solid #d5d5d5;
    color: #ffffff;
    font-size: 16px;
    line-height: 40px;
    padding: 3px 5px;
    text-align: left;
    text-indent: 10px;
}
.pop-listtab {
    border-collapse: collapse;
    width: 100%;
}
.pop-listtab td {
    border-bottom: 1px dashed #d5d5d5;
    color: #777777;
    font-size: 15px;
    line-height: 36px;
    padding: 3px 5px;
    text-align: left;
    text-indent: 10px;
}
</style>
</head>
<body>
<div style="text-align:center"><h2>${stuName}的成绩表<span style="margin-left:100px;font-size:18px;">
<a href="exportExcel?studentId=${stuId}">导出成绩表</a></span></h2></div>
<table class="table pop-listtab">
	  <thead>
	      <th>学年</th><th>学期</th><th>学生名称</th><th>课程名称</th><th>课程性质</th><th>课程归属</th><th>学分</th><th>绩点</th>
	      <th>成绩</th><th>辅修标记</th><th>补考成绩</th><th>重修成绩</th><th>重修标记</th>
	   </thead>
<tbody>	
<c:forEach items="${stuScores}" var="stuScore">     
   <tr>
       <td>${stuScore.studyYear}</td><td>${stuScore.studyYearNum}</td><td>${stuScore.name}</td>
       <td>${stuScore.courceName}</td><td>${stuScore.courceType}</td><td>${stuScore.courceAttr}</td>
       <td>${stuScore.credit}</td><td>${stuScore.scorePoint}</td><td>${stuScore.score}</td>
       <td>${stuScore.minorFlag}</td><td>${stuScore.secondScore}</td><td>${stuScore.restudyScore}</td>
       <td>${stuScore.reStudyFlag}</td>
   </tr>
</c:forEach>
</tbody>
</table>
<table  class="table pop-listtab">
   <tr>
   <td>平均分</td><td>${staticScoreDto.avgScore}</td><td>平均学分绩点</td><td>${staticScoreDto.avgCredit}</td>
   <c:forEach items="${staticScoreDto.scoreCourseType}" var="scoreCourseType">
       <td>${scoreCourseType.courceType}</td><td>${scoreCourseType.totalCredit}</td>
   </c:forEach>  
   </tr>   	   
</table>
</body>
</html>
