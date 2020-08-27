<%@ page pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<mt:admin_template title="Edit profile">
	<jsp:attribute name="content">
		<div class="content-header">
      		<div class="container-fluid">
	      		<div class="col-sm-6 offset-md-3">
					<div class="card card-primary">
		              <div class="card-header">
		                <h3 class="card-title">Edit profile</h3>
		              </div>
		              <!-- /.card-header -->
		              <!-- form start -->
		              <form role="form" method="post">
		                <div class="card-body">
		                  <div class="form-group">
		                    <label for="exampleInputEmail1">Username</label>
		                    <input type="text" class="form-control" value="${account.username}" placeholder="Username" name="username" >
		                  </div>
		                  
		                  
		                  <div class="form-check">
		                    <input type="checkbox" class="form-check-input check_change_pass" name="check_change_pass">
		                    <label class="form-check-label" for="exampleCheck1">Change Password</label>
		                  
		                  </div>
		                  
		                  <div class="form-group password_block" style="display:none">
		                    <label for="exampleInputPassword1">Password</label>
		                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="password">
		                  </div>
			                  
		                  
		                  <div class="form-group">
		                    <label for="exampleInputPassword1">Email</label>
		                    <input type="email" class="form-control" value="${account.email}" placeholder="Email" name="email">
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
		           	<c:if test="${not empty errors}">
		           		<ul class="alert alert-danger">
			           		<c:forEach var="err" items="${errors}">
			           			<li>${err}</li>
			           		</c:forEach>
		           		</ul>
		           	</c:if>
		       </div>
		     </div>
      	</div>
       
	</jsp:attribute>
	
</mt:admin_template>
