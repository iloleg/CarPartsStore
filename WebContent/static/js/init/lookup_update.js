function lookupUpdate($tr) {
	function get(name) {
		return $.trim($tr.find(name).html());
	}

	var updated = {
		id : parseInt($tr.attr('object-id')),
		factoryId : get('.factory_id'),
		brand : get('.brand'),
		model : get('.model'),
		price : get('.price')
	}

	$.post('update_record', {
		"updated" : JSON.stringify(updated)
	}, function(responce) {
		var data = JSON.parse(responce);
		console.log(data);
		if (data.status !== "success") {
			alert("Could not update record!");
		}
	});
}