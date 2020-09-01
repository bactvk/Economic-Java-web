<%@ page pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<mt:admin_template title="Add Product">
	<jsp:attribute name="content">
		<div class="content-header">
      		<div class="container-fluid">
	      		<div class="col-sm-8 offset-md-2">
					<div class="card card-primary">
		              <div class="card-header">
		                <h3 class="card-title">Add Product</h3>
		              </div>
		              <!-- /.card-header -->
		              <!-- form start -->
		              <form role="form" method="post" enctype="multipart/form-data">
		                <div class="card-body">
		                	<div class="row form-group">
			                  <div class="col-sm-3">
			                  	<label for="exampleInputEmail1">Category <span style="color:red">*</span></label>
			                  </div>
			                  <div class="col-sm-9">
			                  	<select name="category_id" class="form-control" >
			                  		<option class="form-control" value="">---Choose category---
			                  		<c:forEach var="item" items="${listCategory}">
				                  		<option class="form-control" ${param.category_id==item.id?'selected':''} value="${item.id}">${item.name}
				                  	</c:forEach>
			                  	</select>
			                  	
			                  	
			                  </div>
		                  	</div>
		                  
		                  	<div class="row form-group">
			                  <div class="col-sm-3">
			                  	<label for="exampleInputEmail1">Name <span style="color:red">*</span></label>
			                  </div>
			                  <div class="col-sm-9">
			                  	<input type="text" class="form-control" value="${param.name}" name="name" >
			                  </div>
		                  	</div>
		                  	
		                  	<div class="row form-group">
			                  <div class="col-sm-3">
			                  	<label for="exampleInputEmail1">Price <span style="color:red">*</span></label>
			                  </div>
			                  <div class="col-sm-9">
			                  	<input type="number" step="0.01" min="0" class="form-control" name="price" value="${param.price}" >
			                  </div>
		                  	</div>
							<div class="row form-group">
			                  <div class="col-sm-3">
			                  	<label for="exampleInputEmail1">Number <span style="color:red">*</span></label>
			                  </div>
			                  <div class="col-sm-9">
			                  	<input type="number" min="1" class="form-control" name="number" value="${param.number}" >
			                  </div>
		                  	</div>
		                  	<div class="row form-group">
			                  <div class="col-sm-3">
			                  	<label for="exampleInputEmail1">Sale </label>
			                  </div>
			                  <div class="col-sm-3">
			                  	<input type="number" min="0" class="form-control" name="sale" value="${param.sale}">
			                  </div>
			                  <div class="col-sm-2">
			                  	<label for="exampleInputEmail1">Image </label>
			                  </div>
			                  <div class="col-sm-4">
			                  	<input type="file" value="" name="image" >
			                  </div>
		                  	</div>
		                  	
		                  	<div class="row form-group">
			                  <div class="col-sm-3">
			                  	<label for="exampleInputEmail1">Content</label>
			                  </div>
			                  <div class="col-sm-9">
			                  	<textarea class="form-control" name="content">${param.content} </textarea>
			                  
			                  </div>
		                  	</div>
		                  	
		                </div>
		               
		                <div class="card-footer">
		                  <button type="submit" class="btn btn-primary">Save</button>
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
		           			<c:remove var="errors" scope="session"/>
		           		</ul>
			           	
		           	</c:if>
		       </div>
		     </div>
      	</div>
	
	</jsp:attribute>
</mt:admin_template>