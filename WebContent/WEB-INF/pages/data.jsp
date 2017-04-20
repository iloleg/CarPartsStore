<%@ taglib prefix="cps" uri="CPS.TLD"%>
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Catalogue</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">Car Parts</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-3">
							<div class="form-group">
								<label for="sel1">Records per page</label> 
								<select class="form-control" id="rpp">
								</select>
							</div>
						</div>
					</div>
					<div class="table-responsive">
						<table id="manage-data" class="table table-bordered table-hover">
							<thead>
								<tr>
									<th>#</th>
									<th>Factory Id</th>
									<th>Car Brand</th>
									<th>Car Model</th>
									<th>Price</th>
								</tr>
							</thead>
						</table>
					</div>
					<!-- /.table-responsive -->

					<div class="row">
						<cps:check-rights>
							<div class="col-lg-2">
								<button id="btn-delete" class="btn btn-danger">
								Delete Chosen Record
								</button>
							</div>
						</cps:check-rights>
						<div class="col-lg-10">
							<nav aria-label="Page navigation">
								<ul class="pagination">
									<li><a href="#">1</a></li>
								</ul>
							</nav>
						</div>
					</div>

				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12s -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->
<script>
$(document).ready(function () {
	var countAll = 0;
	var count = 0;
	var pageCount = 1;
	
	function get_page(lines, page, callback) {
		$.post('get_page', {"lines": lines, "page": page}, function (responce) {
			var data = JSON.parse(responce);
			var table = "<tbody>"
			
			for (var i = 0; i < data.length; ++i) {
				var object = data[i];
				table += "<tr class=\"record\" object-id=\"" + object.id + "\"" + ">"
				+ "<td>" + (i + page*lines + 1) + "</td>"
				+ "<td>" + object.factory_id + "</td>"
				+ "<td>" + object.brand + "</td>"
				+ "<td>" + object.model + "</td>"
				+ "<td>" + object.price + "</td>" 
				+ "</tr>";
			}
			
			table += "</tbody>";
			
			if (callback !== undefined) {
				callback(table);
				
				$(".record" ).click(function () {
					$(".record").css("background-color", "");
					$(this).css("background-color", "lightblue");
					$chosen = $(this);
				});
				
				$("thead").click(function () {
					$(".record").css("background-color", "");
				});
			}
		});
	}

	$.post('get_items_count', function (responce) {
		countAll = count = parseInt(responce);
		console.log(count);
		$("#rpp").html('');
		for (var i = countAll; i > 0; i -= 5) {
			$("#rpp").append("<option value=\"" + i + "\">" + i + "<option>");
		}
	})
	
	$("#rpp").click(function () {
		count = $(this).val();
	
		pageCount = countAll / count;
		
		var paginationHTML = '';
		for (var i = 0; i < pageCount; ++i) {
			paginationHTML += "<li><a href=\"\">" + (i + 1)  + "</a></li>";
		}
		$('.pagination').html(paginationHTML);
		
		/* pagination */
		$(".pagination > li > a").click(function () {
			$(".pagination > li > a").removeClass("active");
			$(this).addClass("active");
			
			console.log(count);
			var page = parseInt($(this).html()) - 1;
			
			console.log(count, page);
			get_page(count, page, function (table) {
				$("#manage-data").html(table);
			});
			return false;
		});
		
	
		get_page($(this).val(), 0, function (table) {
			$("#manage-data").html(table);
		})
	});
	
	var $chosen;

	get_page(100, 0, function (table) {
		$("#manage-data").append(table);
		
		$("#btn-delete").click(function () {
			var id = $chosen.attr("object-id");
			console.log(id);
			if (confirm("Are you sure?")) {
				$.post('delete_record', {"id" : id }, function (responce) {
					console.log(responce);
					if (JSON.parse(responce).status == "success") {
						alert("Successfully deleted!");
						countAll -= 1;
						
						$("#rpp").html('');
						for (var i = countAll; i > 0; i -= 5) {
							$("#rpp").append("<option value=\"" + i + "\">" + i + "<option>");
						}
						
						$('.pagination > li > a.active').click();
					} else {
						alert("Cannot delete this record.");
					};
				});
			}
		});
	});
});
</script>
