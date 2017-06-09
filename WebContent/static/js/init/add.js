$(document).ready(function() {
	$("#add-form").submit(function(e) {
		e.preventDefault();
		var factoryId = parseInt($("#factory_id").val());
		if (isNaN(factoryId)) {
			alert("Factory Id must be of Integer type!");
			return;
		}

		var price = parseFloat($("#price").val());
		if (isNaN(price)) {
			alert("Price must be of Float type!");
			return;
		}

		var brand = $("#brand").val();
		var model = $("#model").val();

		var inserted = {
			"factoryId" : factoryId,
			"brand" : {
				"id": $("#brand").val(),
			},
			"model" : model,
			"price" : price
		}

		console.log(inserted);
		
		var data = {
			"inserted" : JSON.stringify(inserted)
		}

		$.post('add_record', data, function(r) {
			var result = JSON.parse(r);
			if (result.status === "success") {
				alert("Added!");
			} else {
				alert("Some error occured!");
			}
		});

	});

	$("#add-brand-form").submit(function(e) {
		e.preventDefault();
		console.log("HEY");
		var inserted = {
			"name" : $("#brand-name").val()
		}

		var data = {
			"inserted" : JSON.stringify(inserted)
		}

		$.post('add_brand', data, function(r) {
			var result = JSON.parse(r);
			console.log(result);
			if (result.status === "success") {
				alert("Added!");
				location.reload();
			} else {
				alert("Some error occured!");
			}
		});
	});
});