function lookupUpdate($tr) {
	function get(name) {
		return $tr.find(name);
	}

	var updated = {
		id : parseInt($tr.attr('object-id')),
		factoryId : get('.factoryId').html(),
		brand : {
			id: $tr.attr('brand-id')
		},
		category: {
			id: $tr.attr('category-id')
		},
		model : get('.model').html(),
		price : get('.price').html()
	}
	
	$.post('update_record', {
		"updated" : JSON.stringify(updated)
	}, function(responce) {
		var data = JSON.parse(responce);
		if (data.status !== "success") {
			alert("Could not update record!");
		}
	});
}