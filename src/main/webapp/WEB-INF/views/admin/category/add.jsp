<%@ page pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<mt:admin_template title="Add category">
	<jsp:attribute name="content">
		<div class="content-header">
      		<div class="container-fluid">
	      		<div class="col-sm-6 offset-md-3">
					<div class="card card-primary">
		              <div class="card-header">
		                <h3 class="card-title">Add category</h3>
		              </div>
		              <!-- /.card-header -->
		              <!-- form start -->
		              <form role="form" method="post">
		                <div class="card-body">
		                  <div class="form-group">
		                    <label for="exampleInputEmail1">Name category</label>
		                    <input type="text" class="form-control" value="${param.name}" placeholder="Enter category name" name="name" >
		                  </div>

		                </div>
		                <!-- /.card-body -->
		
		                <div class="card-footer">
		                  <button type="submit" class="btn btn-primary">Submit</button>
		                </div>
		              </form>
		           	</div>
		           	<c:if test="${not empty success}">
		           		<div class ="alert alert-success">${success}</div>
		           	</c:if>
		           	<c:if test="${not empty error}">
			           	<li class="alert alert-danger">${error}</li>
		           	</c:if>
		       </div>
		     </div>
      	</div>
	
	</jsp:attribute>
</mt:admin_template>