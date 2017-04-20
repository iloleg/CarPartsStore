<div id="page-wrapper" style="min-height: 99px;">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Forms</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Add
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <form id="add-form" role="form">
                                        <div class="form-group col-lg-3">
                                            <label>Factory Id</label>
                                            <input class="form-control" name="factory_id" placeholder="Integer" required>
                                        </div>
                                        <div class="form-group col-lg-3">
                                            <label>Car Brand</label>
                                            <input class="form-control" name="brand" placeholder="Text" required>
                                        </div>
                                        <div class="form-group col-lg-3">
                                            <label>Car Model</label>
                                            <input class="form-control" name="model" placeholder="Text" required>
                                        </div>
                                        <div class="form-group col-lg-3">
                                            <label>Price</label>
                                            <input class="form-control" name="price" placeholder="Float" required>
                                        </div>
                                        <div class="col-lg-12">
											<button type="submit" class="btn btn-default">Submit</button>
                                        	<button type="reset" class="btn btn-default">Reset</button>
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
            <!-- /.row -->
        </div>
        <script>
        $(document).ready(function () {
        	$("#add-form").submit(function (e) {
        		e.preventDefault();
    			var data = { "fields": [], "values" : []};
        		$(this).find(':input').each(function() {
        			var $t = $(this);
        			if ($t.attr("name") !== undefined) {
        				console.log($t.attr("name") + " : " + $t.val());
        				data.fields.push($t.attr("name"));
        				data.values.push($t.val());
        				console.log(data);
        			}
        		});
        		
        		$.post('add_record', data, function (r) {
        			var result = JSON.parse(r);
        			if (result.status === "success") {
        				alert("Added!");
        			} else {
        				alert("Some error occured. Check types.");
        			}
        		})
        	});
        });
        </script>