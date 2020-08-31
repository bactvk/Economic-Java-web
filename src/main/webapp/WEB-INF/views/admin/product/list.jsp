<%@ page pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<mt:admin_template title="Categories">
	<jsp:attribute name="content">
		<div class="card">
              <div class="card-header">
                <h3 class="card-title">Product</h3>
                <a href ="${pageContext.request.contextPath}/admin/product/add" class="btn btn-success float-right">Adding Product <i class="fa fa-plus-circle"></i></a>
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
	                    <th class="no-sort" >Category</th>
	                    <th class="no-sort" >Number</th>
	                    <th class="no-sort" >Sale</th>
	                    <th class="no-sort" >Image</th>
	                    <th class="no-sort" >Action</th>
	                    
	                  </tr>
                  </thead>
                  <tbody>
                  	<c:forEach var="item" items="${listProduct }">
	                  <tr>
	                    <td>${item.id}</td>
	                    
				        <td class="">${item.name} </td>
				        <td class="">${item.category_name} </td>
				        <td class="">${item.number} </td>
						<td class="">${item.sale} % </td>   
						<td class="">
							<img alt="" style="width:80px" src=" ${pageContext.request.contextPath }/assets/uploads/${item.image}">
						 </td>
	                    <td>
	                    	<a href="${pageContext.request.contextPath}/admin/product/edit?id=${item.id}" class="btn btn-warning">Edit</a>
	                    	
							<a href="${pageContext.request.contextPath}/admin/product/delete?id=${item.id}" onclick="return confirm('Do you want to delete this record?')" class="btn btn-danger">Delete</a>
	                    	
	                    </td>
	                  </tr>
	                 </c:forEach>
              		</tbody>
              	</table>
              	</div>
            </div>
			
			
	</jsp:attribute>
</mt:admin_template>