<%@ page pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<mt:admin_template title="Categories">
	<jsp:attribute name="content">
		<div class="card">
              <div class="card-header">
                <h3 class="card-title">Category</h3>
                <a href ="${pageContext.request.contextPath}/admin/category/add" class="btn btn-success float-right">Add category <i class="fa fa-plus-circle"></i></a>
              </div>
              	<c:if test="${not empty sessionScope.success}" >
              		<div class="alert alert-success">${sessionScope.success}</div>
              		<c:remove var="success" scope="session"/>
              	</c:if>
             	
               <div class="card-body">
                <table id="example1" class="table table-bordered table-striped">
                  <thead>
	                  <tr>
	                    <th class="no-sort" >Id</th>
	                    <th class="no-sort" >Name</th>
	                    <th class="no-sort" >Action</th>
	                    
	                  </tr>
                  </thead>
                  <tbody>
                  	<c:forEach var="item" items="${lists }">
	                  <tr>
	                    <td>${item.id}</td>
	                    
				        <td class="${item.parent_id==0? '':'pl-5' }">
	                    	${item.name}
	                    </td>
						   
	                    <td>
	                    	<a href="${pageContext.request.contextPath}/admin/category/edit?id=${item.id}" class="btn btn-warning">Edit</a>
	                    	<c:choose>
							   <c:when test="${item.parent_id==0}">
							   		<a href="${pageContext.request.contextPath}/admin/category/addSub?id=${item.id}" class="btn btn-primary">Add subCategory</a>
							   </c:when> 
							   <c:otherwise>
							   		<a href="${pageContext.request.contextPath}/admin/category/delete?id=${item.id}" onclick="return confirm('Do you want to delete this record?')" class="btn btn-danger">Delete</a>
							   </c:otherwise>    
							</c:choose>
	                    	
	                    </td>
	                  </tr>
	                 </c:forEach>
              		</tbody>
              	</table>
              	</div>
            </div>
			
			
	</jsp:attribute>
</mt:admin_template>