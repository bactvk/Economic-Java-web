<%@ page pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<mt:admin_template title="Categories">
	<jsp:attribute name="content">
		<div class="card">
              <div class="card-header">
                <h3 class="card-title">Category</h3>
                <a href ="${pageContext.request.contextPath}/admin/category?action=add" class="btn btn-success float-right">Add category <i class="fa fa-plus-circle"></i></a>
              </div>
              	<c:if test="${not empty sessionScope.success}" >
              		<div class="alert alert-success">${sessionScope.success}</div>
              		<c:remove var="success" scope="session"/>
              	</c:if>
             	
               <div class="card-body">
                <table id="example1" class="table table-bordered table-striped">
                  <thead>
	                  <tr>
	                    <th>Id</th>
	                    <th>Name</th>
	                    <th class="no-sort" >Action</th>
	                    
	                  </tr>
                  </thead>
                  <tbody>
                  	<c:forEach var="item" items="${lists }">
	                  <tr>
	                    <td>${item.id}</td>
	                    <td>${item.name}
	                    </td>
	                    <td>
	                    	<a href="${pageContext.request.contextPath}/admin/category?action=edit&id=${item.id}" class="btn btn-warning">Edit</a>
	                    	<a href="${pageContext.request.contextPath}/admin/category?action=delete&id=${item.id}" onclick="return confirm('Do you want to delete?')" class="btn btn-danger">Delete</a>
	                    </td>
	                   
	                  </tr>
	                 </c:forEach>
              		</tbody>
              	</table>
              	</div>
            </div>
			
			
	</jsp:attribute>
</mt:admin_template>