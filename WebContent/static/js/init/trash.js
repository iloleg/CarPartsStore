$(document).ready(getLookupReady('get_trash'));
$(document).ready(function() {
	$("#btn-untrash").click(function() {
		var untrashed = {
			"id" : $("#chosen-record").attr('object-id')
		}

		if (confirm("Are you sure?")) {
			$.post('untrash_record', {
				"untrashed" : JSON.stringify(untrashed)
			}, function(responce) {
				var data = JSON.parse(responce);
				if (data.status === "success") {
					alert("Successfully untrashed!");
					location.reload();
				} else {
					alert("Cannot untrash this record.");
				}
			});
		}
	});
});