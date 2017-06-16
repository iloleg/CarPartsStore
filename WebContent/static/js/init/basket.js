$(document).ready(function () {
	var update = window.lookupUpdate;
	var count = 0;
	var countOnPage = 0;

	function get_page(lines, page) {
		$.post('get_basket', {
			"lines" : lines,
			"page" : page
		}, function(responce) {
			var data = JSON.parse(responce);
			var table = CPS.$Table(data, [ "Factory ID", "User"], 
					["data.factoryId", "user.username"],
					[ "object-id"], 
					[ "id" ], page * lines, update);
			if (lines < 0) {
				countOnPage = count = data.length;
				pagination(1);
				rpp(count);
			}
			$('.cps-table').html('').append(table);
		});
	}

	function pagination(count) {
		var ul = $("<ul></ul>");
		for (var i = 0; i < count; ++i) {
			ul.addClass("pagination").append(
					$("<li></li>").append(
							$("<a></a>").attr('href', '#').append(i + 1)));
		}
		ul.find("a").click(function() {
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
			$("#rpp").append(
					$("<option></option>").attr("value", i).append(i));
		}
	}

	get_page(-1, 0);
	
	$("#rpp").click(function() {
		countOnPage = $(this).val();
		pageCount = count / countOnPage;
		pagination(pageCount);
		get_page(countOnPage, 0);
	});

	$("#btn-delete").click(function() {
		var deleted = {
			"id" : $("#chosen-record").attr('object-id')
		}

		if (confirm("Are you sure?")) {
			$.post('delete_from_basket', {
				"deleted" : JSON.stringify(deleted)
			}, function(responce) {
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
	
	$("#btn-order").click(function() {
		if (confirm("Are you sure?")) {
			var email = prompt("Email: ");
			if (email == '' || email.indexOf('@') == -1) {
				alert("Wrong Email!");
				return;
			}
			
			$.post('send_order', {
				"email" : email
			}, function(responce) {
				var data = JSON.parse(responce);
				if (data.status === "success") {
					alert("Success!");
					count -= 1;
					rpp(count);
					pagination(1)
					get_page(count, 0);
				} else {
					alert("Cannot make order!");
				}
			});
		}
	});
});