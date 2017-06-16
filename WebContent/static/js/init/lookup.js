$(document).ready(getLookupReady('get_page'));
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