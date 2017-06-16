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
		var email = prompt("Enter your email:");
		if (inserted.data.id === undefined ) {
			alert("Choose a record!");
			return;
		}
		
		if (email == "") {
			return;
		}
		
		if (email.indexOf("@") == -1) {
			alert("Wrong email!");
			return;
		}
		
		$.post('add_order', {
			"inserted": JSON.stringify(inserted),
			"email": email
		}, function (r) {
			var result = JSON.parse(r);
			if (result.status === "success") {
				alert("Added! Check your e-mail `" + email + "` for further information!");
			} else {
				alert("Some error occured!");
			}
		});
	});
});