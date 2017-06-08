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
							<nav aria-label="Page navigation" id="pagination-outer">
								<ul id="pagination" class="pagination">
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
	var count = 0;
	var countOnPage = 0;
	
	function update($tr) {
		function get(name) {
			return $tr.find(name).html();
		}
		
		var updated = {
			id         : parseInt($tr.attr('object-id')),
			factoryId : get('.factory_id'),
			brand      : get('.brand'),
			model      : get('.model'),
			price      : get('.price')	
		}

		$.post('update_record', {
			"updated": JSON.stringify(updated)
		}, function (responce) {
			var data = JSON.parse(responce);
			console.log(data);
			if (data.status !== "success") {
				alert("Could not update record!");
			}
		});
	}
	
	function get_page(lines, page) {
		$.post('get_page', {"lines" : lines, "page" : page}, function (responce) {
			var data = JSON.parse(responce);
			var table = CPS.$Table(data, 
					["Factory ID", "Brand", "Model", "Price"],
					["factory_id", "brand", "model", "price"],
					["object-id"],
					["id"],
					page*lines, update);
			$('.table-responsive').html('').append(table);
		});
	}
	
	function pagination(count) {
		var ul = $("<ul></ul>");
		for (var i = 0; i < count; ++i) {
			ul.addClass("pagination").append(
				$("<li></li>").append(
					$("<a></a>").attr('href', '#').append(i + 1)
				)
			);
		}
		ul.find("a").click(function () {
		    var page = parseInt($(this).html()) - 1;
		    console.log(page);
		    get_page(countOnPage, page);
		    return false;
		});
		$("#pagination-outer").html("").append(ul);
	}
	
	function rpp(count) {
		$("#rpp").html('');
		for (var i = count; i > 0; i -= 5) {
			$("#rpp").append($("<option></option>").attr("value", i).append(i));
		}
	}

	$.post('get_items_count', function (responce) {
		countOnPage = count = parseInt(responce);
		pagination(1);
		rpp(count);
		get_page(count, 0);
	});
	
	$("#rpp").click(function () {
		countOnPage = $(this).val();
		pageCount = count / countOnPage;
		pagination(pageCount);
		get_page(countOnPage, 0);
	});
	
	$("#btn-delete").click(function () {
		var deleted = {
			"id": $("#chosen-record").attr('object-id')
		}
		
		if (confirm("Are you sure?")) {
			$.post('delete_record', {"deleted" : JSON.stringify(deleted)}, function (responce) {
				var data = JSON.parse(responce);
				if (data.status === "success") {
					alert("Successfully deleted!");
					count -= 1;
					rpp(count);
					pagination(1)
					get_page(count, 0);
				} else {
					alert("Cannot delete this record.");
				}
			});	
		}
	});
	
});
</script>
