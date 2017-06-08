<div id="page-wrapper" style="min-height: 99px;">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Add record</h1>
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
                                            <input class="form-control" id="factory_id" placeholder="Integer" required>
                                        </div>
                                        <div class="form-group col-lg-3">
                                            <label>Car Brand</label>
                                            <input class="form-control" id="brand" placeholder="Text" required>
                                        </div>
                                        <div class="form-group col-lg-3">
                                            <label>Car Model</label>
                                            <input class="form-control" id="model" placeholder="Text" required>
                                        </div>
                                        <div class="form-group col-lg-3">
                                            <label>Price</label>
                                            <input class="form-control" id="price" placeholder="Float" required>
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
        		
    			/*var data = { 
    				"fields[]": ["factory_id", "brand", "model", "price"], 
    				"values[]": [ factory_id ,  brand ,  model ,  price ]
    			}*/
    			
    			var inserted = {
    				"factoryId": factoryId,
    				"brand": brand,
    				"model": model,
    				"price": price
    			}
    			
    			var data = {
    				"inserted": JSON.stringify(inserted)
    			}
        		
        		$.post('add_record', data, function (r) {
        			var result = JSON.parse(r);
        			if (result.status === "success") {
        				alert("Added!");
        			} else {
        				alert("Some error occured!");
        			}
        		});
        	});
        });
        </script>