$(document).ready(function () {
	var update = window.lookupUpdate;
	var count = 0;
	var countOnPage = 0;
	var finalCount = 0;
	
	function get_page(lines, page, search_field, search_query, callback) {
		$.post('get_page', {"lines" : lines, "page" : page, "search_field": search_field, "search_query" : search_query}, function (responce) {
			var data = JSON.parse(responce);
			countOnPage = count = data.length;
			var table = CPS.$Table(data, [ "Factory ID", "Brand", "Category", "Model",
				"Price" ], [ "factoryId", "brand.name", "category.name", "model",
				"price" ], [ "object-id", "brand-id", "category-id"], [ "id",
				"brand.id", "category.id"], page * lines, update);
			$('.cps-table').html('').append(table);
			if (callback !== undefined) {
				callback();
			}
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
			$("#rpp").append(
					$("<option></option>").attr("value", i).append(i));
		}
	}
	
	get_page(-1, 0, "", "", function () {
		rpp(count);
		pagination(1);
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
		var deleted = {
				"id": $("#chosen-record").attr('object-id')
		}
		if (confirm("Are you sure?")) {
			$.post('delete_record', {"deleted" : JSON.stringify(deleted)}, function (responce) {
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
$(document).ready(function () {
	$("#btn-order").click(function () {
		var $chosen = $("#chosen-record");
		var inserted = {
			"data": {
				"id": $chosen.attr('object-id'),
				"factoryId": $chosen.find('.factoryId').html(),
				"brand" : {
					"id": $chosen.attr('brand-id'),
					"name": $chosen.find('.brand-name').html()
				},
				"category": {
					"id": $chosen.attr('category-id'),
					"name": $chosen.find('.category-name').html()
				},
				"model": $chosen.find('.model').html(),
				"price": $chosen.find('.price').html()
			} 
		};		
		
		if (inserted.data.id === undefined ) {
			alert("Choose a record!");
			return;
		}
		
		$.post('add_to_basket', {
			"inserted": JSON.stringify(inserted)
		}, function (r) {
			var result = JSON.parse(r);
			if (result.status === "success") {
				alert("Added!");
			} else {
				alert("Some error occured!");
			}
		});
	});
});