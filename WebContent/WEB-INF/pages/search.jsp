<%@ taglib prefix="cps" uri="CPS.TLD"%>
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Search</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
	 <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <form id="search-form" role="form">
                                        <div class="form-group col-lg-8">
                                            <label>Query</label>
                                            <input class="form-control" id="search-query" placeholder="" required>
                                        </div>
                                        <div class="form-group col-lg-4">
                                            <label>Search By</label>
                                            <select id="search-field" class="form-control">
                                            	<option value="factory_id">Factory Id</option>
                                            	<option value="brand">Brand</option>
                                            	<option value="model">Model</option>
                                            	<option value="price">Price</option>
                                            </select>
                                        </div>
                                    </form>
                                </div>
                                <!-- /.col-lg-12 (nested) -->
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">Results</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-3">
							<div class="form-group">
								<label for="sel1">Records per page</label> 
								<select class="form-control" id="rpp">
								<option>15</option>
								<option>10</option>
								<option>5</option>
								<option>2</option>
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
	var finalCount = 0;
	
	function get_page(lines, page, search_field, search_query) {
		$.post('get_page', {"lines" : lines, "page" : page, "search_field": search_field, "search_query" : search_query}, function (responce) {
			var data = JSON.parse(responce);
			count = 0;
			for (item in data) {
				++count;
			}
			var table = CPS.$Table(data, 
					["Factory ID", "Brand", "Model", "Price"],
					["factory_id", "brand", "model", "price"],
					["object-id"],
					["id"],
					page*lines);
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
		    var search_field = $("#search-field").val();
			var search_query = $("#search-query").val();	
		    get_page(countOnPage, page, search_field, search_query);
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
		finalCount = countOnPage = count = parseInt(responce);
		pagination(1);
		rpp(count);
		get_page(count, 0);
	});
	
	$("#rpp").click(function () {
		countOnPage = $(this).val();
		pageCount = count / countOnPage;
		pagination(pageCount);
		var search_field = $("#search-field").val();
		var search_query = $("#search-query").val();		
		get_page(countOnPage, 0, search_field, search_query);
	});
	
	$("#btn-delete").click(function () {
		var id = $("#chosen-record").attr('object-id');
		console.log(id);
		if (confirm("Are you sure?")) {
			$.post('delete_record', {"id" : id}, function (responce) {
				var data = JSON.parse(responce);
				if (data.status === "success") {
					alert("Successfully deleted!");
					finalCount -= 1;
					count = finalCount;
					rpp(finalCount);
					pagination(1)
					get_page(finalCount, 0);
				} else {
					alert("Cannot delete this record.");
				}
			});	
		}
	});

	$("#search-query").on('input', function () {
		$("#rpp").val(finalCount);
		countOnPage = count = finalCount;
		pageCount = count / countOnPage;
		pagination(pageCount);
		
		var search_field = $("#search-field").val();
		var search_query = $("#search-query").val();		
		get_page(countOnPage, 0, search_field, search_query);
	});
});
</script>
